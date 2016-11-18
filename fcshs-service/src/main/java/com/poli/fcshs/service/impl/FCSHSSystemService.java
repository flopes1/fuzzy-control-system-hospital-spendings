package com.poli.fcshs.service.impl;

import com.poli.fcshs.config.FcshsConstants;
import com.poli.fcshs.config.FcshsPropertiesLoader;
import com.poli.fcshs.config.FcshsSetupConstants;
import com.poli.fcshs.defuzzification.Defuzzifier;
import com.poli.fcshs.defuzzification.methods.continuous.DefuzzifierMeanMax;
import com.poli.fcshs.defuzzification.methods.discrete.DefuzzifierCenterOfGravity;
import com.poli.fcshs.model.LinguisticVariableItem;
import com.poli.fcshs.model.SystemOutputItem;
import com.poli.fcshs.service.IFCSHSSystemService;

/**
 * @author Filipe Lopes created on 13/11/2016
 * 
 */

public class FCSHSSystemService implements IFCSHSSystemService
{

	/*
	 * System functional variables
	 */

	// fuzzi attribute
	// inference attribute
	private Defuzzifier defuzziMethod;

	/*
	 * System configurable variables
	 */
	private static final String DEFUZZI_METHOD = FcshsPropertiesLoader.getInstance()
			.getPropertyByName(FcshsSetupConstants.DEFUZZIFICATION_METHOD);

	public FCSHSSystemService()
	{
	}

	public String getSystemAnalysisByHospital(String hospitalName, String year)
	{
		// chama a fuzzificação para argumentos hospital e ano
		// pega o retorno e passa para a inferencia que trata um hospital e um
		// ano
		// seta o retorno da inferencia na variavel abaixo
		LinguisticVariableItem inferenceOutput = null;

		this.initializeDefuzzification(inferenceOutput);
		SystemOutputItem defuzziOutput = this.defuzziMethod.defuzzify();

		return defuzziOutput.toString();
	}

	public String getSystemAnalysisByHospital(String hospitalName)
	{
		// chama a fuzzificação para argumento só hospital (todos os anos i.e
		// planilhas)
		// pega o retorno e passa para a inferencia que trata um só hospital em
		// todos os anos
		// seta o retorno da inferencia na variavel abaixo
		LinguisticVariableItem inferenceOutput = null;

		this.initializeDefuzzification(inferenceOutput);
		SystemOutputItem defuzziOutput = this.defuzziMethod.defuzzify();

		return defuzziOutput.toString();
	}

	private void initializeDefuzzification(LinguisticVariableItem outputInferenceItem)
	{
		if (DEFUZZI_METHOD.equals(FcshsConstants.DEFUZZI_METHOD_CONTINUOUS))
		{
			this.defuzziMethod = new DefuzzifierMeanMax(outputInferenceItem);
		}
		else
		{
			this.defuzziMethod = new DefuzzifierCenterOfGravity(outputInferenceItem);
		}

	}

}
