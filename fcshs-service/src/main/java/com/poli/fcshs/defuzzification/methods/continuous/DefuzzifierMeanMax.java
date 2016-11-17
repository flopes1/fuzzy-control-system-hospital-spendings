package com.poli.fcshs.defuzzification.methods.continuous;

import java.util.Set;

import com.poli.fcshs.defuzzification.methods.DefuzzifierContinuousSet;
import com.poli.fcshs.model.LinguisticVariableItem;
import com.poli.fcshs.model.SystemOutputItem;

public class DefuzzifierMeanMax extends DefuzzifierContinuousSet
{

	public DefuzzifierMeanMax(LinguisticVariableItem outputInferenceItem)
	{
		super("Mean Maxima - MeOM", outputInferenceItem);
	}

	public SystemOutputItem defuzzify()
	{
		SystemOutputItem outputDefuzzyItem = new SystemOutputItem();

		double maxValue = this.getMaxValueOfFunctionEntry();

		if (maxValue == 0)
		{
			throw new RuntimeException("The fuzzy set pertinence function non contains a maximum value");
		}

		int maxValueMatchsCounter = 0, index = 0;
		double sumOfMaxValuesMatchs = 0;

		Set<Double> functionXItens = this.getGenericFuncionDomain().keySet();

		for (Double key : functionXItens)
		{
			double value = this.getGenericFuncionDomain().get(key);
			if (value == maxValue)
			{
				maxValueMatchsCounter++;
				sumOfMaxValuesMatchs += this.getLowerIntegralLimit() + this.getFunctionStepSize() * index;
			}
			index++;
		}

		double outputDefuzzyValue = 0;

		try
		{
			outputDefuzzyValue = sumOfMaxValuesMatchs / maxValueMatchsCounter;
		}
		catch (ArithmeticException e)
		{
			throw new RuntimeException(e.getMessage());
		}

		outputDefuzzyItem.setOutputName(this.getOutputInferenceItem().getLinguisticVariableName());
		outputDefuzzyItem.setOutputValue(outputDefuzzyValue);
		outputDefuzzyItem.setOutputType(this.getOutputInferenceItem().getDomainType());

		return outputDefuzzyItem;
	}

	private double getMaxValueOfFunctionEntry()
	{
		double maxValue = 0;

		for (Double keySet : this.getGenericFuncionDomain().keySet())
		{
			double currentValue = this.getGenericFuncionDomain().get(keySet);
			if (currentValue > maxValue)
			{
				maxValue = currentValue;
			}
		}

		return maxValue;
	}

	public String getInformation()
	{
		return super.getInformtion();
	}

}
