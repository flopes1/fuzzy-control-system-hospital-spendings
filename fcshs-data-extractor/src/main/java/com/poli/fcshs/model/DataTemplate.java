package com.poli.fcshs.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opencsv.CSVReader;
import com.poli.fcshs.config.FcshsConstants;

public class DataTemplate
{
	/**
	 * @author Filipe Lopes create on 22/10/2016
	 * 
	 */
	
	//TODO Esse será nosso objeto que será formado a partir do .csv (arquivo excel) montado quando vocês extrairem os dados
	
	// Unico atributo que consegui pensar que é o nome da planilha, outros atributos podem ser
	// a lista de hospitais, com seus respectivos dados (colunas), organizados pelos meses, e outros atributos.
	
	private String year;
	
	private List<Hospital> hospitais;
	
	
	
	
	public DataTemplate()
	{
	}

	public String getYear()
	{
		return year;
	}

	public void setYear(String year)
	{
		this.year = year;
	}

	public List<Hospital> getHospitais() {
		return hospitais;
	}

	public void setHospitais(List<Hospital> hospitais) {
		this.hospitais = hospitais;
	}
	
	public void listInitialize(File csvFile) throws IOException{
		if (csvFile.getName().contains(FcshsConstants.OUTPUT_TEMPLATE_FORMAT)) {
			
			 CSVReader reader = new CSVReader(new FileReader(csvFile.getPath()));
			 String [] nextLine;
			 String[] stringList = null;
			 
			 String name =  csvFile.getName().replace(".", "#");
			 String [] nameFile = name.split("#");
			 setYear(nameFile[0]);

			 nextLine = reader.readNext();
			 for (int i = 1; i < numberLines(nextLine); i++){
				 String [] line = getLine(nextLine, i);

				 if (getHospitalByName(line[0].replace("#", "")) != null) {
					 Hospital newHospital = getHospitalByName(line[0].replace("#", ""));
					 newHospital.createNewMes(line, getLine(nextLine, 0));

				 }else{
					 if (this.hospitais == null) {
						 this.hospitais = new ArrayList<Hospital>();
					 }

					 Hospital newHospital = new Hospital();

					 newHospital.setName(line[0].replace("#", ""));
					 newHospital.createNewMes(line, getLine(nextLine, 0));

					 this.hospitais.add(newHospital);

				 }

			 }
			 reader.close();
		}
	}
	
	private int numberLines(String[] nextLine){
		int comeco=0,fim=0,contador=0;
		
		for (int i = 0; i < nextLine.length-1; i++){
			if(nextLine[i].contains("#")){
				comeco=i;
			}
			if(nextLine[i+1].contains("#") || i+1 == nextLine.length){
				if (i+1 == nextLine.length) {
					fim = i+1;
				}
				else{
					fim = i;
				}
				contador++;
			}
		}
		
		return contador;
	}
	
	private String[] getLine(String[] nextLine, int linha){

		int comeco=0,fim=0,contador=-1;
		String [] resultado = null;

		for (int i = 0; i < nextLine.length-1; i++){

			if(nextLine[i].contains("#")){
				comeco=i;
			}

			if(nextLine[i+1].contains("#") || i+1 == nextLine.length-1){
				if (i+1 == nextLine.length-1) {
					fim = i+1;
				}
				else{
					fim = i;
				}
				contador++;
			}

			if(contador == linha){

				resultado = Arrays.copyOfRange(nextLine, comeco, fim);
				break;
			}

		}
		
		return resultado;
	}

	
	public Hospital getHospitalByName(String name){
		Hospital hospitalObject = null;
		if (this.hospitais != null) {
			for (Hospital hospital : this.hospitais) {
				if (hospital.getName().equals(name)) {
					hospitalObject = hospital;
					break;
				}
			}
		}
		return hospitalObject;
	}
	
	
	
	
}
