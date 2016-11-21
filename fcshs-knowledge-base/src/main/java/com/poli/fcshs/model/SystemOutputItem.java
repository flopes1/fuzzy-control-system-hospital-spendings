package com.poli.fcshs.model;

/**
 * @author Filipe Lopes created on 01/11/2016
 * 
 */

public class SystemOutputItem
{

	private String outputName;
	private double outputValue;
	private String outputType;

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

	public String getOutputType()
	{
		return outputType;
	}

	public void setOutputType(String outputType)
	{
		this.outputType = outputType;
	}
	
	@Override
	public String toString()
	{
		return "System output variable name: " + this.outputName + "\n" +
				" Result: " + this.outputValue + " " + this.outputType;
	}

}
