package com.poli.fcshs.model;

import java.util.ArrayList;
import java.util.List;

import com.poli.fcshs.config.FcshsPropertiesLoader;
import com.poli.fcshs.config.FcshsSetupConstants;

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
	private double minDomainValue;

	private String domainType;
	private static String MAX_DOMAIN_VALUE_MULTIPLIFIER;
	private static String MIN_DOMAIN_VALUE_MULTIPLIFIER;

	public LinguisticVariableItem()
	{
		this.linguisticTerms = new ArrayList<String>();
		this.fuzzySetItens = new ArrayList<FuzzySet>();
		LinguisticVariableItem.MAX_DOMAIN_VALUE_MULTIPLIFIER = FcshsPropertiesLoader.getInstance()
				.getPropertyByName(FcshsSetupConstants.ITEM_MAX_VALUE_MULTIPLIFIER);
		LinguisticVariableItem.MIN_DOMAIN_VALUE_MULTIPLIFIER = FcshsPropertiesLoader.getInstance()
				.getPropertyByName(FcshsSetupConstants.ITEM_MIN_VALUE_MULTIPLIFIER);
	}

	public LinguisticVariableItem(String linguisticValiableName, List<String> terms, List<FuzzySet> fuzzySetItens,
			double minDomainValue, double maxDomain, String domainType)
	{
		this.linguisticVariableName = linguisticValiableName;
		this.linguisticTerms = terms;
		this.fuzzySetItens = fuzzySetItens;
		this.minDomainValue = minDomainValue;
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
		return maxDomainValue * Double.parseDouble(MAX_DOMAIN_VALUE_MULTIPLIFIER);
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
	
	public double getMinDomainValue()
	{
		return minDomainValue * Double.parseDouble(MIN_DOMAIN_VALUE_MULTIPLIFIER);
	}
	
	public void setMinDomainValue(double minDomainValue)
	{
		this.minDomainValue = minDomainValue;
	}

}
