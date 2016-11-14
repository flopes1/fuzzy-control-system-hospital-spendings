package com.poli.fcshs.model;

/**
 * @author Filipe Lopes created on 01/11/2016
 * 
 */

public class SystemOutputItem
{

	private String outputName;
	private double outputValue;

	public SystemOutputItem()
	{
	}

	public SystemOutputItem(String outputName, double outputValue)
	{
		this.outputName = outputName;
		this.outputValue = outputValue;
	}

	public String getOutputName()
	{
		return outputName;
	}

	public void setOutputName(String outputName)
	{
		this.outputName = outputName;
	}

	public double getOutputValue()
	{
		return outputValue;
	}

	public void setOutputValue(double outputValue)
	{
		this.outputValue = outputValue;
	}

}
