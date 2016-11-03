package com.poli.fcshs.generator.util;

import com.poli.fcshs.config.FcshsPropertiesLoader;
import com.poli.fcshs.config.FcshsRulesConstants;
import com.poli.fcshs.model.DataTemplateItem;

/**
 * @author Filipe Lopes created on 01/11/2016
 * 
 */

public class DataBaseGeneratorUtils
{

	/*
	 * Essa classe deve conter todos os metodos uteis para auxiliar na classe
	 * principal desse pacote todos os metodos devem ser estaticos
	 */
	public static double calculateValueUnit(DataTemplateItem itemTotalAmount, DataTemplateItem itemTotalValue){
		double result=0;
		
		result = (itemTotalValue.getIndicatorValue() / itemTotalAmount.getIndicatorValue());
		
		return result;
		
	}
	public static String[] getInputTerms(){
		
		String[] inputTerms = FcshsPropertiesLoader.getInstance().getPropertyByName(FcshsRulesConstants.INPUT_TERMS).split(";");
		
		
		return inputTerms;
		
	}

	public static String[] getOutputTerms(){
		
		String[] outputTerms = FcshsPropertiesLoader.getInstance().getPropertyByName(FcshsRulesConstants.OUTPUT_TERMS).split(";");
		
		
		return outputTerms;
		
	}
	
}
