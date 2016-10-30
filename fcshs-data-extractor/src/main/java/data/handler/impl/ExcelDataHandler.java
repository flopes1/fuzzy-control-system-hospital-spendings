package data.handler.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.poli.fcshs.data.repository.impl.DataTemplateRepository;

import data.handler.IExcelDataHandler;

public class ExcelDataHandler implements IExcelDataHandler{

//	public File getCsvFile(String name) {
//		return null;
//	}
	DataTemplateRepository dataTemplateRepository = new DataTemplateRepository();
	public File xlsxConversorToCsv(File xlsxFile ){
		
		File outputFile = new File(dataTemplateRepository.getDefaultDirectory().getAbsolutePath());
		
		String name =  xlsxFile.getName().replace(".", "#");
		String [] nameFile = name.split("#");
		
		File csv = new File( outputFile.getAbsolutePath()+ File.separator + nameFile[0] + ".csv");
		//System.out.println(outputFile.getAbsolutePath()+ File.separator + nameFile[0] + ".csv");
		StringBuffer cellValue = new StringBuffer();
		
		try {
			FileOutputStream fos = new FileOutputStream(csv);
			
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(xlsxFile));
			
			XSSFSheet sheet = workbook.getSheetAt(0); 

			Row row;
			Cell cell;

			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()){
				row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				cellValue.append("#");
				
				while (cellIterator.hasNext()) {
					cell = cellIterator.next();

					switch (cell.getCellType()) {
					
					case Cell.CELL_TYPE_BOOLEAN:
						cellValue.append(cell.getBooleanCellValue() + ",");
						break;

					case Cell.CELL_TYPE_NUMERIC:
						cellValue.append(cell.getNumericCellValue() + ",");
						break;

					case Cell.CELL_TYPE_STRING:
						cellValue.append(cell.getStringCellValue() + ",");
						break;

					case Cell.CELL_TYPE_BLANK:
						cellValue.append("" + ",");
						break;

					default:
						cellValue.append(cell + ",");

					}

				}
			}

			fos.write(cellValue.toString().getBytes());
			fos.close();
			
			System.out.println("testeT");
		
			return csv;
			
			
			
		} catch (IOException e) {
			System.out.println(e.getMessage()); 
		}

		return csv;
	}
	
	

	
}
