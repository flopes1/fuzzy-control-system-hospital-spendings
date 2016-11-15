package com.poli.fcshs.defuzzification.methods;

import java.util.HashMap;

import com.poli.fcshs.defuzzification.Defuzzifier;
import com.poli.fcshs.model.FuzzySet;
import com.poli.fcshs.model.LinguisticVariableItem;

public abstract class DefuzzifierContinuousSet extends Defuzzifier
{

	private int numberOfPoints;
	private int lowerIntegralLimit;
	private int upperIntegralLimit;
	private HashMap<Double, Double> genericFuncion;

	public DefuzzifierContinuousSet(String deffuziType, LinguisticVariableItem outputInferenceItem)
	{
		super("Deffuzing using the method: ", outputInferenceItem, false);
		this.initialize();
	}

	public int getNumberOfPoints()
	{
		return numberOfPoints;
	}

	public void setNumberOfPoints(int numberOfPoints)
	{
		this.numberOfPoints = numberOfPoints;
	}

	public int getLowerIntegralLimit()
	{
		return lowerIntegralLimit;
	}

	public void setLowerIntegralLimit(int lowerIntegralLimit)
	{
		this.lowerIntegralLimit = lowerIntegralLimit;
	}

	public int getUpperIntegralLimit()
	{
		return upperIntegralLimit;
	}

	public void setUpperIntegralLimit(int upperIntegralLimit)
	{
		this.upperIntegralLimit = upperIntegralLimit;
	}

	public HashMap<Double, Double> getGenericFuncionDomain()
	{
		return genericFuncion;
	}

	public void setGenericFuncionDomain()
	{
		FuzzySet setEntry = this.getOutputInferenceItem().getFuzzySetItens().get(0);
		if (setEntry == null)
		{
			throw new RuntimeException("Invalid system output fuzzy set");
		}

		this.genericFuncion = setEntry.getFuzzySetItens();
	}

	private void initialize()
	{
		this.setGenericFuncionDomain();
		this.setLowerIntegralLimit(0);
		this.setUpperIntegralLimit((int) this.getOutputInferenceItem().getMaxDomainValue());
		this.setNumberOfPoints(this.getGenericFuncionDomain().size());
	}

	public double getFunctionStepSize()
	{
		double stepSize = 0;
		try
		{
			stepSize = (this.getUpperIntegralLimit() - this.getLowerIntegralLimit()) / this.getNumberOfPoints();
		}
		catch (ArithmeticException e)
		{
			throw new RuntimeException("Invalid fuzzy set pertinence function length");
		}
		return stepSize;
	}

	public double functionArea()
	{
		double functionSumElements = 0;

		for (Double key : this.getGenericFuncionDomain().keySet())
		{
			functionSumElements += this.getGenericFuncionDomain().get(key);
		}

		return functionSumElements * this.getFunctionStepSize();
	}

}
