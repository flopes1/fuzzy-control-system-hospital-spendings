package com.poli.fcshs.model;

public class DataTemplate
{
	/**
	 * @author Filipe Lopes create on 22/10/2016
	 * 
	 */
	
	//TODO Esse ser� nosso objeto que ser� formado a partir do .csv (arquivo excel) montado quando voc�s extrairem os dados
	
	// Unico atributo que consegui pensar que � o nome da planilha, outros atributos podem ser
	// a lista de hospitais, com seus respectivos dados (colunas), organizados pelos meses, e outros atributos.
	
	private String year;
	
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
	
	
}
