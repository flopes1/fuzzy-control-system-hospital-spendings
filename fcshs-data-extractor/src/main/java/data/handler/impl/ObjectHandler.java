package data.handler.impl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVReader;
import com.poli.fcshs.config.FcshsConstants;
import com.poli.fcshs.model.impl.DataFile;
import com.poli.fcshs.model.impl.DataObject;
import com.poli.fcshs.model.impl.Hospital;
import com.poli.fcshs.model.impl.Month;

public class ObjectHandler
{

	public DataFile listInitialize(File csvFile) throws IOException{
		DataFile dataFile = new DataFile();
		
		
		if (csvFile.getName().contains(FcshsConstants.OUTPUT_TEMPLATE_FORMAT)) {
			
			 CSVReader csvReader = new CSVReader(new FileReader(csvFile.getPath()));
			 String [] nextLine;
			 
			 dataFile.setYear(csvFile.getName().substring(0, csvFile.getName().lastIndexOf(".")));

			 nextLine = csvReader.readNext();
			 
			 List<Hospital> hospitals = new ArrayList<Hospital>();
			 
			 String[] indicatorsNames = getLine(nextLine, 0);
			 for (int i = 1; i <= numberLines(nextLine); i++){
				 String [] line = getLine(nextLine, i);

				 if (getHospitalByName(line[0].replace("#", ""), hospitals) != null) {
					 Hospital hospital = getHospitalByName(line[0].replace("#", ""), hospitals);
					 if (hospital.getMonths() == null){
						hospital.setMonths(new ArrayList<Month>());
					 }
					 
					 hospital.getMonths().add(createNewMonth(line, indicatorsNames));
					 
				 }else{
					 Hospital newHospital = new Hospital();
					 newHospital.setMonths(new ArrayList<Month>());
					 newHospital.setName(line[0].replace("#", ""));
					 newHospital.getMonths().add(createNewMonth(line, indicatorsNames));
					 hospitals.add(newHospital);
				 }

			 }
			 dataFile.setHospitais(hospitals);
			 csvReader.close();
		}
		return dataFile;
	}
	
	private Month createNewMonth(String[] line,  String[] indicators){
		Month newMonth = new Month();
		newMonth.setMonth(Double.parseDouble(line[1]));
		newMonth.setDataObjectList(createNewListDataObject(line, indicators));
		
		return newMonth;
	}
	
	private List<DataObject> createNewListDataObject(String[] line, String[] indicators) {
		List<DataObject> listData = new ArrayList<DataObject>();
		for(int i=2; i < line.length; i++){
			DataObject dataObject = new DataObject();
			dataObject.setIndicador(indicators[i]);
			dataObject.setValorIndicador(Double.parseDouble(line[i]));
			listData.add(dataObject);
		}
		
		return listData;
	}
	
	
	private int numberLines(String[] nextLine){
		int startLine=0,endLine=0,counter=0;
		
		for (int i = 0; i < nextLine.length-1; i++){
			if(nextLine[i].contains("#")){
				startLine=i;
			}
			if(nextLine[i+1].contains("#") || i+1 == nextLine.length){
				if (i+1 == nextLine.length) {
					endLine = i+1;
				}
				else{
					endLine = i;
				}
				counter++;
			}
		}
		
		return counter;
	}
	
	private String[] getLine(String[] nextLine, int line){

		int startLine=0,endLine=0,counter=-1;
		String [] result = null;

		for (int i = 0; i < nextLine.length-1; i++){

			if(nextLine[i].contains("#")){
				startLine=i;
			}

			if(nextLine[i+1].contains("#") || i+1 == nextLine.length-1){
				if (i+1 == nextLine.length-1) {
					endLine = i+1;
				}
				else{
					endLine = i;
				}
				counter++;
			}

			if(counter == line){

				result = Arrays.copyOfRange(nextLine, startLine, endLine);
				break;
			}

		}
		
		return result;
	}
	
	private Hospital getHospitalByName(String name, List<Hospital> hospitalsList){
		Hospital hospitalObject = null;
		if (hospitalsList != null) {
			for (Hospital hospital : hospitalsList) {
				if (hospital.getName().equals(name)) {
					hospitalObject = hospital;
					break;
				}
			}
		}
		return hospitalObject;
	}
	
	
	
}
