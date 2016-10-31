package com.poli.fcshs.model;

import java.io.File;
import java.io.IOException;

import com.poli.fcshs.data.repository.impl.DataTemplateRepository;
import com.poli.fcshs.data.repository.impl.SourceTemplateRepository;
import com.poli.fcshs.model.impl.DataFile;

import data.handler.impl.ObjectHandler;
import data.util.UtilExcelDataHandler;

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
		
		UtilExcelDataHandler obj = new  UtilExcelDataHandler();
		
		
		DataFile dataTemplate ;
		
		ObjectHandler objectHandler= new ObjectHandler();
		
		File inputDirectory = new File(sourceTemplateRepository.getDefaultDirectory().getAbsolutePath());
		File[] listInputFiles = inputDirectory.listFiles();
		
		File outputDirectory = new File(dataTemplateRepository.getDefaultDirectory().getAbsolutePath());
		File[] listOutputFiles = outputDirectory.listFiles();
		
		// O que deveria fazer no teste
		// scan entrada de texto do usuario;
		// String input = read.nextLine();		
		//dataTemplateRepository.getDataTemplateByYear(input);
		//converterxls
		//listinitialize
		
		for (File fileInput : listInputFiles)
		{
			obj.xlsxConversorToCsv(fileInput);
		}
		
		try {
			
			
			
			for (File fileOutput : listOutputFiles)
			{
				dataTemplate = objectHandler.listInitialize(fileOutput);
				
				System.out.println(dataTemplate);
				
				System.out.println("inicializou");
		
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
