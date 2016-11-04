package com.poli.fcshs.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Lopes created on 01/11/2016
 * 
 */

public class LinguisticVariableItem
{

	private String linguisticVariableName;
	private List<String> linguisticTerms;
	private List<FuzzySet> fuzzySetItens;
	private double maxDomainValue;
	private String domainType;

	public LinguisticVariableItem()
	{
		this.linguisticTerms = new ArrayList<String>();
		this.fuzzySetItens = new ArrayList<FuzzySet>();
	}

	public LinguisticVariableItem(String linguisticValiableName, List<String> terms, List<FuzzySet> fuzzySetItens,
			double maxDomain, String domainType)
	{
		this.linguisticVariableName = linguisticValiableName;
		this.linguisticTerms = terms;
		this.fuzzySetItens = fuzzySetItens;
		this.maxDomainValue = maxDomain;
		this.domainType = domainType;
	}

	public String getLinguisticVariableName()
	{
		return linguisticVariableName;
	}

	public void setLinguisticVariableName(String linguisticVariableName)
	{
		this.linguisticVariableName = linguisticVariableName;
	}

	public List<String> getLinguisticTerms()
	{
		return linguisticTerms;
	}

	public void setLinguisticTerms(List<String> linguisticTerms)
	{
		this.linguisticTerms = linguisticTerms;
	}

	public List<FuzzySet> getFuzzySetItens()
	{
		return fuzzySetItens;
	}

	public void setFuzzySetItens(List<FuzzySet> fuzzySetItens)
	{
		this.fuzzySetItens = fuzzySetItens;
	}

	public double getMaxDomainValue()
	{
		return maxDomainValue * 1.3;
	}

	public void setMaxDomainValue(double maxDomainValue)
	{
		this.maxDomainValue = maxDomainValue;
	}

	public String getDomainType()
	{
		return domainType;
	}

	public void setDomainType(String domainType)
	{
		this.domainType = domainType;
	}

}
