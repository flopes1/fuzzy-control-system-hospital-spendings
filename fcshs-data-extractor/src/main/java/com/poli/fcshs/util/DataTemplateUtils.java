package com.poli.fcshs.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataTemplateUtils
{

	public static File xlsxConversorToCsv(File xlsxFile)
	{

		File csvFile = new File(FileUtils.getTempDirectory().getAbsolutePath() + File.separator
				+ DataTemplateUtils.getFileName(xlsxFile) + ".csv");

		StringBuffer cellValue = new StringBuffer();

		try
		{
			FileOutputStream fileOutputStream = new FileOutputStream(csvFile);

			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(xlsxFile));

			XSSFSheet sheet = workbook.getSheetAt(0);

			Row row;
			Cell cell;

			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext())
			{
				row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				cellValue.append("#");

				while (cellIterator.hasNext())
				{
					cell = cellIterator.next();

					switch (cell.getCellType())
					{

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

			fileOutputStream.write(cellValue.toString().getBytes());
			fileOutputStream.close();

		}
		catch (IOException e)
		{
			throw new RuntimeException(e.getMessage());
		}

		return csvFile;
	}

	public static String getFileName(File file)
	{
		if (file == null)
		{
			throw new IllegalArgumentException("The file cannot be null");
		}

		return file.getName().substring(0, file.getName().lastIndexOf("."));
	}

}
