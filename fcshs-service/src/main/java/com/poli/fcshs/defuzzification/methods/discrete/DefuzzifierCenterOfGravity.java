package com.poli.fcshs.defuzzification.methods.discrete;

import java.util.HashMap;

import com.poli.fcshs.defuzzification.methods.DefuzzifierDiscreteSet;
import com.poli.fcshs.model.FuzzySet;
import com.poli.fcshs.model.LinguisticVariableItem;
import com.poli.fcshs.model.SystemOutputItem;

/**
 * @author Filipe Lopes created on 13/11/2016
 * 
 */

public class DefuzzifierCenterOfGravity extends DefuzzifierDiscreteSet
{

	public DefuzzifierCenterOfGravity(LinguisticVariableItem outputInferenceItem)
	{
		super("Center of Gravity - COG", outputInferenceItem);
	}

	/**
	 * Defuzzify the discrete function set in the class with CoG method
	 * 
	 * @return SystemOutputItem the information of the process and results
	 */
	public SystemOutputItem defuzzify()
	{
		SystemOutputItem outputItem = new SystemOutputItem();

		double x, y, sum = 0, sumWight = 0;

		for (FuzzySet fuzzySet : this.getOutputInferenceItem().getFuzzySetItens())
		{
			HashMap<Double, Double> set = fuzzySet.getFuzzySetItens();
			for (Double key : set.keySet())
			{
				y = set.get(key);
				x = key;
				sumWight += x * y;
				sum += y;
			}
		}

		double value = sum != 0 ? (sumWight / sum) : 0;

		outputItem.setOutputName(this.getOutputInferenceItem().getLinguisticVariableName());
		outputItem.setOutputValue(value);
		outputItem.setOutputType(this.getOutputInferenceItem().getDomainType());

		return outputItem;
	}

	public String getInformation()
	{
		return super.getInformtion();
	}

}
