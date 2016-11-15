package com.poli.fcshs.model;

import java.util.ArrayList;
import java.util.List;

public class SystemRules
{

	private List<String> operands;
	private List<String> operators;
	private String output;

	public SystemRules()
	{
		operands = new ArrayList<String>();
		operators = new ArrayList<String>();

	}

	public List<String> getOperands()
	{
		return operands;
	}

	public void setOperands(List<String> operands)
	{
		this.operands = operands;
	}

	public List<String> getOperators()
	{
		return operators;
	}

	public void setOperators(List<String> operators)
	{
		this.operators = operators;
	}

	public String getOutput()
	{
		return output;
	}

	public void setOutput(String output)
	{
		this.output = output;
	}

}
