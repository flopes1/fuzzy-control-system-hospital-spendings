package com.poli.fcshs.model;

/**
 * @author Filipe Lopes created on 01/11/2016
 * 
 */

public class SystemInputItem
{

	private String inputName;
	private DataTemplateItem itemTotalAmount;
	private DataTemplateItem itemUnitaryValue;

	public SystemInputItem()
	{
	}
	
	public SystemInputItem(String inputName, DataTemplateItem itemTotalAmount, DataTemplateItem itemUnitaryValue)
	{
		this.inputName = inputName;
		this.itemTotalAmount = itemTotalAmount;
		this.itemUnitaryValue = itemUnitaryValue;
	}

	public String getInputName()
	{
		return inputName;
	}

	public void setInputName(String inputName)
	{
		this.inputName = inputName;
	}

	public String getItemTotalAmountName()
	{
		return itemTotalAmount != null ? itemTotalAmount.getIndicatorName() : "";
	}
	
	public double getItemTotalAmountValue()
	{
		return itemTotalAmount != null ? itemTotalAmount.getIndicatorValue() : 0;
	}

	public void setItemTotalAmount(DataTemplateItem itemTotalAmount)
	{
		this.itemTotalAmount = itemTotalAmount;
	}

	public String getItemUnitaryName()
	{
		return itemUnitaryValue != null ? itemUnitaryValue.getIndicatorName() : "";
	}
	
	public double getItemUnitaryValue()
	{
		return itemUnitaryValue != null ? itemUnitaryValue.getIndicatorValue() : 0;
	}

	public void setItemUnitaryValue(DataTemplateItem itemUnitValue)
	{
		this.itemUnitaryValue = itemUnitValue;
	}
	
}
