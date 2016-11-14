package com.poli.fcshs.generator.util;

import java.util.ArrayList;
import java.util.List;

import com.poli.fcshs.config.FcshsPropertiesLoader;
import com.poli.fcshs.config.FcshsSetupConstants;
import com.poli.fcshs.model.DataTemplateItem;

/**
 * @author Filipe Lopes created on 01/11/2016
 * 
 */

public class DataBaseGeneratorUtils
{

	public static double calculateValueUnit(DataTemplateItem itemTotalAmount, DataTemplateItem itemTotalValue)
	{
		double result = 0;
		try
		{
			result = (itemTotalValue.getIndicatorValue() / itemTotalAmount.getIndicatorValue());
		}
		catch (ArithmeticException e)
		{
			throw new RuntimeException(e.getMessage());
		}

		return result;

	}

	public static List<String> getInputTerms()
	{
		List<String> inputTerms = new ArrayList<String>();
		String[] terms = FcshsPropertiesLoader.getInstance().getPropertyByName(FcshsSetupConstants.INPUT_TERMS)
				.split(";");
		for (String term : terms)
		{
			inputTerms.add(term);
		}

		return inputTerms;

	}

	public static List<String> getOutputTerms()
	{
		List<String> outputTerms = new ArrayList<String>();
		String[] terms = FcshsPropertiesLoader.getInstance().getPropertyByName(FcshsSetupConstants.OUTPUT_TERMS)
				.split(";");
		for (String term : terms)
		{
			outputTerms.add(term);
		}

		return outputTerms;
	}

}
