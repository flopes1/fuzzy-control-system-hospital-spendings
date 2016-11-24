package com.poli.fcshs.model;

import java.util.HashMap;

/**
 * @author Filipe Lopes created on 01/11/2016
 * 
 */

public class FuzzySet
{

	private String fuzzySetName;
	private HashMap<Double, Double> fuzzySetItens;

	

	public FuzzySet()
	{
		this.fuzzySetItens = new HashMap<Double, Double>();
	}

	public FuzzySet(String fuzzyName)
	{
		this();
		this.fuzzySetName = fuzzyName;
	}

	public String getFuzzySetName()
	{
		return fuzzySetName;
	}

	public void setFuzzySetName(String fuzzySetName)
	{
		this.fuzzySetName = fuzzySetName;
	}

	public HashMap<Double, Double> getFuzzySetItens()
	{
		return fuzzySetItens;
	}

	public void setFuzzySetItens(HashMap<Double, Double> fuzzySetItens)
	{
		this.fuzzySetItens = fuzzySetItens;
	}

}
