package com.poli.fcshs.model;

import java.io.File;
import java.io.IOException;

import com.poli.fcshs.data.repository.impl.DataTemplateRepository;
import com.poli.fcshs.data.repository.impl.SourceTemplateRepository;
import com.poli.fcshs.model.impl.DataFile;

import data.handler.impl.ExcelDataHandler;

public class Test {

	public static void main(String[] args) {
		
		
		SourceTemplateRepository sourceTemplateRepository = new SourceTemplateRepository();
		DataTemplateRepository dataTemplateRepository = new DataTemplateRepository();
		
		System.out.println(sourceTemplateRepository.getDefaultDirectory().getAbsolutePath());
		//C:\_FCSHS\_Data\XLS_Directory
		System.out.println(dataTemplateRepository.getDefaultDirectory().getAbsolutePath());
		//C:\_FCSHS\_Data\CSV_Directory
		
//		File inputFile = new File(sourceTemplateRepository.getDefaultDirectory().getAbsolutePath() + "/PlanilhaNormalizada.xlsx");
//		File outputFile = new File(dataTemplateRepository.getDefaultDirectory().getAbsolutePath() +"/planilhaCSV.csv");
		
		ExcelDataHandler obj = new  ExcelDataHandler();
		
		
		DataFile dataTemplate = new DataFile();
		
		File inputDirectory = new File(sourceTemplateRepository.getDefaultDirectory().getAbsolutePath());
		File[] listInputFiles = inputDirectory.listFiles();
		
		File outputDirectory = new File(dataTemplateRepository.getDefaultDirectory().getAbsolutePath());
		File[] listOutputFiles = outputDirectory.listFiles();
		
		for (File fileInput : listInputFiles)
		{
			obj.xlsxConversorToCsv(fileInput);
		}
		
		try {
			
			
			
			for (File fileOutput : listOutputFiles)
			{
				dataTemplate.listInitialize(fileOutput);
				
				System.out.println(dataTemplate);
				
				System.out.println("inicializou");
		
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
