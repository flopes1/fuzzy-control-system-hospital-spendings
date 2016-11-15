package com.poli.fcshs.defuzzification.methods;

import com.poli.fcshs.defuzzification.Defuzzifier;
import com.poli.fcshs.model.LinguisticVariableItem;

/**
 * @author Filipe Lopes created on 13/11/2016
 * 
 */

public abstract class DefuzzifierDiscreteSet extends Defuzzifier
{

	public DefuzzifierDiscreteSet(String deffuziType, LinguisticVariableItem outputInferenceItem)
	{
		super("Deffuzing using the method: " + deffuziType, outputInferenceItem, true);
	}

}
