package com.poli.fcshs.data.extractor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVReader;
import com.poli.fcshs.data.repository.impl.DataTemplateRepository;
import com.poli.fcshs.data.repository.impl.SourceTemplateRepository;
import com.poli.fcshs.model.DataTemplateFile;
import com.poli.fcshs.model.DataTemplateItem;
import com.poli.fcshs.model.Hospital;
import com.poli.fcshs.model.Month;
import com.poli.fcshs.util.DataTemplateUtils;

public class DataTemplateExtractor implements IDataTempalteExtractor
{

	private DataTemplateRepository dataTemplateRepository;
	private SourceTemplateRepository sourceTemplateRepository;

	public DataTemplateExtractor()
	{
		this.dataTemplateRepository = new DataTemplateRepository();
		this.sourceTemplateRepository = new SourceTemplateRepository();
	}

	/**
	 * Extract all information from a csv file with name equals to the parameter
	 * passed
	 * 
	 * @return DataTemplateFile the extrated information in the csv file
	 */
	public DataTemplateFile extractDataByName(String fileName)
	{
		DataTemplateFile dataFile = new DataTemplateFile();

		try
		{

			File xlsxFile = this.sourceTemplateRepository.getDataTemplateByYear(fileName);
			File csvFile = DataTemplateUtils.xlsxConversorToCsv(xlsxFile);
			this.dataTemplateRepository.insertDataTemplate(csvFile.getAbsolutePath());

			CSVReader csvReader = new CSVReader(new FileReader(csvFile.getPath()));

			dataFile.setYear(DataTemplateUtils.getFileName(csvFile));

			String[] nextLine = csvReader.readNext();
			List<Hospital> hospitals = new ArrayList<Hospital>();
			String[] indicatorsNames = this.getLine(nextLine, 0);

			for (int i = 1; i <= this.numberLines(nextLine); i++)
			{
				String[] line = this.getLine(nextLine, i);

				if (this.getHospitalByName(line[0].replace("#", ""), hospitals) != null)
				{
					Hospital hospital = this.getHospitalByName(line[0].replace("#", ""), hospitals);
					if (hospital.getMonths() == null)
					{
						hospital.setMonths(new ArrayList<Month>());
					}

					hospital.getMonths().add(createNewMonth(line, indicatorsNames));

				}
				else
				{
					Hospital newHospital = new Hospital();
					newHospital.setMonths(new ArrayList<Month>());
					newHospital.setName(line[0].replace("#", ""));
					newHospital.getMonths().add(this.createNewMonth(line, indicatorsNames));
					hospitals.add(newHospital);
				}

			}
			dataFile.setHospitais(hospitals);
			csvReader.close();

		}
		catch (FileNotFoundException e)
		{
			throw new RuntimeException(e.getMessage());
		}
		catch (IOException e)
		{
			throw new RuntimeException(e.getMessage());
		}

		return dataFile;
	}

	/**
	 * Extract all information from all csv file in the direcory set in the 
	 * properties archive
	 * 
	 * @return List<DataTemplateFile> the extrated information in all csv file
	 */
	public List<DataTemplateFile> extractAllData()
	{
		List<File> allXlsxFile = this.sourceTemplateRepository.getAllData();

		List<DataTemplateFile> dataTemplateItens = new ArrayList<DataTemplateFile>();

		for (File file : allXlsxFile)
		{
			String fileName = DataTemplateUtils.getFileName(file);
			DataTemplateFile dataFile = this.extractDataByName(fileName);
			dataTemplateItens.add(dataFile);
		}

		return dataTemplateItens;
	}

	
	//cria o mês.
	private Month createNewMonth(String[] line, String[] indicators)
	{
		Month newMonth = new Month();
		newMonth.setMonth((int) Double.parseDouble(line[1]));
		newMonth.setDataTempalteItens(this.createNewListDataObject(line, indicators));

		return newMonth;
	}

	//cria a lista de dataTemplateItem.
	private List<DataTemplateItem> createNewListDataObject(String[] line, String[] indicators)
	{
		List<DataTemplateItem> listData = new ArrayList<DataTemplateItem>();

		for (int i = 2; i < line.length; i++)
		{
			DataTemplateItem dataObject = new DataTemplateItem();
			dataObject.setIndicatorName(indicators[i]);
			dataObject.setIndicatorValue(Double.parseDouble(line[i]));
			listData.add(dataObject);
		}

		return listData;
	}

	//Conta quantas linhas tem no arquivo xlsx.
	private int numberLines(String[] nextLine)
	{
		int startLine = 0, endLine = 0, counter = 0;

		for (int i = 0; i < nextLine.length - 1; i++)
		{
			if (nextLine[i].contains("#"))
			{
				startLine = i;
			}
			if (nextLine[i + 1].contains("#") || i + 1 == nextLine.length)
			{
				if (i + 1 == nextLine.length)
				{
					endLine = i + 1;
				}
				else
				{
					endLine = i;
				}
				counter++;
			}
		}

		return counter;
	}
	
	//retorna a linha indicada.
	private String[] getLine(String[] nextLine, int line)
	{

		int startLine = 0, endLine = 0, counter = -1;
		String[] result = null;

		for (int i = 0; i < nextLine.length - 1; i++)
		{

			if (nextLine[i].contains("#"))
			{
				startLine = i;
			}

			if (nextLine[i + 1].contains("#") || i + 1 == nextLine.length - 1)
			{
				if (i + 1 == nextLine.length - 1)
				{
					endLine = i + 1;
				}
				else
				{
					endLine = i + 1;
				}
				counter++;
			}

			if (counter == line)
			{

				result = Arrays.copyOfRange(nextLine, startLine, endLine);
				break;
			}

		}

		return result;
	}
	
	//retorna um objeto hospital da lista de hospitais.
	private Hospital getHospitalByName(String name, List<Hospital> hospitalsList)
	{
		Hospital hospitalObject = null;
		if (hospitalsList != null)
		{
			for (Hospital hospital : hospitalsList)
			{
				if (hospital.getName().equals(name))
				{
					hospitalObject = hospital;
					break;
				}
			}
		}
		return hospitalObject;
	}

}
