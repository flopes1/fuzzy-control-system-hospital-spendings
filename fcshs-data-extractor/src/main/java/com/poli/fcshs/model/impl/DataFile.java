package com.poli.fcshs.model.impl;

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

public class DataFile
{
	/**
	 * @author Filipe Lopes create on 22/10/2016
	 * 
	 */
	
	//TODO Esse será nosso objeto que será formado a partir do .csv (arquivo excel) montado quando vocês extrairem os dados
	
	// Unico atributo que consegui pensar que é o nome da planilha, outros atributos podem ser
	// a lista de hospitais, com seus respectivos dados (colunas), organizados pelos meses, e outros atributos.
	
	private String year;
	
	private List<Hospital> hospitals;
	
	public DataFile()
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

	public List<Hospital> getHospitals() {
		return hospitals;
	}

	public void setHospitais(List<Hospital> hospitals) {
		this.hospitals = hospitals;
	}
	

	


	

	
	
	
	
}
