package com.poli.fcshs.model;

import java.io.File;
import java.io.IOException;

import com.poli.fcshs.model.DataTemplate;

public class Test {

	public static void main(String[] args) {
		
//		File inputFile = new File("C:/Users/Erik/Desktop/PlanilhaNormalizada.xlsx");
		
		File inputFile = new File("C:/Users/vitop/Desktop/planilhaCSV.csv");
		
//		ExcelDataHandler obj = new  ExcelDataHandler();
//		obj.xlsxConversorToCsv(inputFile);
		
		DataTemplate teste = new DataTemplate();
		
		try {
			teste.listInitialize(inputFile);
			System.out.println( teste );
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
