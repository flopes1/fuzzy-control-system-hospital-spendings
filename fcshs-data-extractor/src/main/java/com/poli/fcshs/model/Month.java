package com.poli.fcshs.model;

import java.util.List;

public class Month
{

	private int month;
	private List<DataTemplateItem> dataTemplateItens;

	public double getMonth()
	{
		return month;
	}

	public void setMonth(int month)
	{
		this.month = month;
	}

	public List<DataTemplateItem> getDataTemplateItens()
	{
		return dataTemplateItens;
	}

	public void setDataTempalteItens(List<DataTemplateItem> itens)
	{
		this.dataTemplateItens = itens;
	}

}
