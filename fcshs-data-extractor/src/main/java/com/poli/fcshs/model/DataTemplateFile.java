package com.poli.fcshs.model;

import java.util.List;

/**
 * @author Filipe Lopes create on 22/10/2016
 * 
 */

public class DataTemplateFile
{

	private String year;
	private List<Hospital> hospitals;

	public DataTemplateFile()
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

	public List<Hospital> getHospitals()
	{
		return hospitals;
	}

	public void setHospitais(List<Hospital> hospitals)
	{
		this.hospitals = hospitals;
	}

}
