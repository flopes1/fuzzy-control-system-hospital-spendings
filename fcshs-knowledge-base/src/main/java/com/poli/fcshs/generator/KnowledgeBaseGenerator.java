package com.poli.fcshs.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.poli.fcshs.config.FcshsPropertiesLoader;
import com.poli.fcshs.config.FcshsSetupConstants;
import com.poli.fcshs.data.extractor.DataTemplateExtractor;
import com.poli.fcshs.generator.util.DataBaseGeneratorUtils;
import com.poli.fcshs.model.DataTemplateFile;
import com.poli.fcshs.model.DataTemplateItem;
import com.poli.fcshs.model.FuzzySet;
import com.poli.fcshs.model.Hospital;
import com.poli.fcshs.model.LinguisticVariableItem;
import com.poli.fcshs.model.Month;
import com.poli.fcshs.model.SystemInputItem;

/**
 * @author Filipe Lopes created on 01/11/2016
 * 
 */

public class KnowledgeBaseGenerator implements IKnowledgeBaseGenerator
{

	private String templatesItemName;
	private DataTemplateExtractor dataTemplateExtractor;
	private List<LinguisticVariableItem> linguisticVariableItens;
	private List<SystemInputItem> inputSystemItens;
	private static double DOMAIN_VALUE_MULTIPLIFIER = Double.valueOf(
			FcshsPropertiesLoader.getInstance().getPropertyByName(FcshsSetupConstants.ITEM_MAX_VALUE_MULTIPLIFIER));

	public KnowledgeBaseGenerator(String templatesItemName)
	{
		this.templatesItemName = templatesItemName;
		this.dataTemplateExtractor = new DataTemplateExtractor();
		this.linguisticVariableItens = new ArrayList<LinguisticVariableItem>();
		this.inputSystemItens = new ArrayList<SystemInputItem>();
	}

	public List<SystemInputItem> generateSystemInputItens(String hospitalName)
	{
		int count;
		DataTemplateFile dataTemplateFile = this.dataTemplateExtractor.extractDataByName(this.templatesItemName);
		for (Hospital hospital : dataTemplateFile.getHospitals())
		{
			if (hospital.getName().equals(hospitalName))
			{
				for (Month month : hospital.getMonths())
				{
					count = 0;
					List<DataTemplateItem> itens = month.getDataTemplateItens();
					while (count < itens.size())
					{
						SystemInputItem systemInputItem = new SystemInputItem();
						systemInputItem.setInputName(itens.get(count).getIndicatorName().substring(0,
								itens.get(count).getIndicatorName().lastIndexOf("_")));
						systemInputItem.setItemTotalAmount(itens.get(count));
						systemInputItem.setItemUnitaryValue(itens.get(count + 1));
						this.inputSystemItens.add(systemInputItem);
						count += 2;
					}
				}
			}
		}

		return this.inputSystemItens;
	}

	public List<LinguisticVariableItem> generateSystemLinguisticVariables(String hospitalName)
	{
		DataTemplateFile dataTemplateFile = this.dataTemplateExtractor.extractDataByName(this.templatesItemName);

		LinguisticVariableItem linguisticVariableItem = new LinguisticVariableItem();
		for (Hospital hospital : dataTemplateFile.getHospitals())
		{
			if (hospital.getName().equals(hospitalName))
			{
				for (Month month : hospital.getMonths())
				{
					List<DataTemplateItem> itens = month.getDataTemplateItens();
					for (DataTemplateItem dataTemplateItem : itens)
					{
						LinguisticVariableItem linguisticVariable = containsLinguisticVariable(
								dataTemplateItem.getIndicatorName());
						if (linguisticVariable != null)
						{
							FuzzySet fuzzySet = new FuzzySet();
							fuzzySet.setFuzzySetItens(generateFuzzySet(dataTemplateItem.getIndicatorValue()));
							linguisticVariable.getFuzzySetItens().add(fuzzySet);
							if (linguisticVariable.getMaxDomainValue() < (dataTemplateItem.getIndicatorValue()
									* DOMAIN_VALUE_MULTIPLIFIER))
							{
								linguisticVariable.setMaxDomainValue(dataTemplateItem.getIndicatorValue());
							}

						}
						else
						{
							linguisticVariable = new LinguisticVariableItem();
							linguisticVariable.setLinguisticVariableName(dataTemplateItem.getIndicatorName());
							linguisticVariable.setLinguisticTerms(DataBaseGeneratorUtils.getInputTerms());
							FuzzySet fuzzySet = new FuzzySet();
							fuzzySet.setFuzzySetItens(generateFuzzySet(dataTemplateItem.getIndicatorValue()));
							linguisticVariable.getFuzzySetItens().add(fuzzySet);
							if (linguisticVariable.getMaxDomainValue() < (dataTemplateItem.getIndicatorValue()
									* DOMAIN_VALUE_MULTIPLIFIER))
							{
								linguisticVariable.setMaxDomainValue(dataTemplateItem.getIndicatorValue());
							}
							linguisticVariable.setDomainType(dataTemplateItem.getIndicatorName().substring(
									dataTemplateItem.getIndicatorName().indexOf("_"),
									dataTemplateItem.getIndicatorName().length()));

							this.linguisticVariableItens.add(linguisticVariableItem);

						}

					}
				}
			}
		}
		return this.linguisticVariableItens;
	}

	public LinguisticVariableItem containsLinguisticVariable(String linguisticVariableItem)
	{
		for (LinguisticVariableItem linguisticVariable : this.linguisticVariableItens)
		{
			if (linguisticVariable.getLinguisticVariableName().equals(linguisticVariableItem))
			{
				return linguisticVariable;
			}
		}
		return null;
	}

	public HashMap<Double, Double> generateFuzzySet(double indicatorValue)
	{
		HashMap<Double, Double> fuzzySet = new HashMap<Double, Double>();

		fuzzySet.put(indicatorValue, normalizeFuzzySetValues());

		return fuzzySet;
	}

	public double normalizeFuzzySetValues()
	{
		return 0;
	}

}
