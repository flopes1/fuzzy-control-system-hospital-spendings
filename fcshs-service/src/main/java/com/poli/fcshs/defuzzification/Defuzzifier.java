package com.poli.fcshs.defuzzification;

import com.poli.fcshs.model.LinguisticVariableItem;

/**
 * @author Filipe Lopes created on 13/11/2016
 * 
 */

public abstract class Defuzzifier implements IDefuzzifier
{

	private LinguisticVariableItem outputInferenceItem;
	private String defuzzifierTypeName;
	private boolean defuzzifierDiscrete;

	public Defuzzifier(String deffuziType, LinguisticVariableItem outputInferenceItem, boolean isDiscret)
	{
		this.defuzzifierTypeName = deffuziType;
		this.outputInferenceItem = outputInferenceItem;
		this.defuzzifierDiscrete = isDiscret;
	}

	public LinguisticVariableItem getOutputInferenceItem()
	{
		return outputInferenceItem;
	}

	public void setOutputInferenceItem(LinguisticVariableItem outputInferenceItem)
	{
		this.outputInferenceItem = outputInferenceItem;
	}

	public String getDefuzzifierTypeName()
	{
		return defuzzifierTypeName;
	}

	public void setDefuzzifierTypeName(String defuzzifierTypeName)
	{
		this.defuzzifierTypeName = defuzzifierTypeName;
	}

	public boolean isDefuzzifierDiscrete()
	{
		return defuzzifierDiscrete;
	}

	public void setDefuzzifierDiscrete(boolean defuzzifierDiscrete)
	{
		this.defuzzifierDiscrete = defuzzifierDiscrete;
	}

	public String getInformtion()
	{
		return this.getDefuzzifierTypeName() + "\n" + "Type: "
				+ (this.isDefuzzifierDiscrete() ? "Discrete" : "Continuous");
	}

}
