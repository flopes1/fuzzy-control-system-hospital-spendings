package com.poli.fcshs.model;

import com.poli.fcshs.generator.util.DataBaseGeneratorUtils;

/**
 * @author Filipe Lopes created on 01/11/2016
 * 
 */

public class SystemInputItem
{

	private String inputName;
	private DataTemplateItem itemTotalAmount;
	private DataTemplateItem itemTotalValue;
	private double valueUnit;

	public SystemInputItem()
	{
	}
	
	public SystemInputItem(String inputName, DataTemplateItem itemTotalAmount, DataTemplateItem itemTotalValue)
	{
		this.inputName = inputName;
		this.itemTotalAmount = itemTotalAmount;
		this.itemTotalValue = itemTotalValue;
		this.setValueUnit(DataBaseGeneratorUtils.calculateValueUnit(itemTotalAmount, itemTotalValue));
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
		return itemTotalValue != null ? itemTotalValue.getIndicatorName() : "";
	}
	
	public double getItemUnitaryValue()
	{
		return itemTotalValue != null ? itemTotalValue.getIndicatorValue() : 0;
	}

	public void setItemUnitaryValue(DataTemplateItem itemUnitValue)
	{
		this.itemTotalValue = itemUnitValue;
	}

	public double getValueUnit()
	{
		return valueUnit;
	}

	public void setValueUnit(double valueUnit)
	{
		this.valueUnit = valueUnit;
	}
	
}