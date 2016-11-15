package com.poli.fcshs.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

	// o argumento do construtor é o/os nomes (anos) das planilhas que vou
	// utilizar
	// se quiser, pode remover o argumento se for mais inteligente utilizar
	// todas as
	// planilhas csv que estao cadastradas.
	public KnowledgeBaseGenerator(String templatesItemName)
	{
		this.templatesItemName = templatesItemName;
		this.dataTemplateExtractor = new DataTemplateExtractor();
		this.linguisticVariableItens = new ArrayList<LinguisticVariableItem>();
		this.inputSystemItens = new ArrayList<SystemInputItem>();
	}

	//De acordo com o que foi proposto este método deve retornar a lista de itens de um hospital
	//e portanto deve ter acesso ao hospital em questão, neste caso foi considerado o nome do hospital.

	public List<SystemInputItem> generateSystemInputItens(String hospitalName){
		int count;
		DataTemplateFile dataTemplateFile = this.dataTemplateExtractor.extractDataByName(this.templatesItemName);
		for (Hospital hospital : dataTemplateFile.getHospitals()) {
			if (hospital.getName().equals(hospitalName)) {
				for (Month month : hospital.getMonths()) {
					count = 0;
					List<DataTemplateItem> itens = month.getDataTemplateItens();
					while(count < itens.size()){
						SystemInputItem systemInputItem = new SystemInputItem();
						systemInputItem.setInputName(itens.get(count).getIndicatorName().substring(0, itens.get(count).getIndicatorName().lastIndexOf("_")));
						systemInputItem.setItemTotalAmount(itens.get(count));
						systemInputItem.setItemUnitaryValue(itens.get(count+1));
						systemInputItem.setMonth(month.getMonth());
						this.inputSystemItens.add(systemInputItem);
						count+=2;
					}
				}
			}
		}

		return this.inputSystemItens;
	}

	public List<LinguisticVariableItem> generateSystemLinguisticVariables(String hospitalName){
		DataTemplateFile dataTemplateFile = this.dataTemplateExtractor.extractDataByName(this.templatesItemName);
		
		for (Hospital hospital : dataTemplateFile.getHospitals()) {
			if (hospital.getName().equals(hospitalName)) {
				for (Month month : hospital.getMonths()) {
					List<DataTemplateItem> itens = month.getDataTemplateItens();
					for (DataTemplateItem dataTemplateItem : itens) {
						LinguisticVariableItem linguisticVariable = containsLinguisticVariable(dataTemplateItem.getIndicatorName());
						if ( linguisticVariable != null) {
							
							for (String term : linguisticVariable.getLinguisticTerms()) {
								for (FuzzySet fuzzySet : linguisticVariable.getFuzzySetItens()) {
									if (fuzzySet.getFuzzySetName().equals(term)) {
										fuzzySet.getFuzzySetItens().put(dataTemplateItem.getIndicatorValue(), normalizeFuzzySetValues(dataTemplateItem.getIndicatorValue(),term,linguisticVariable.getMaxDomainValue()));
									}
								}
							}
							
						} else {
							linguisticVariable = new LinguisticVariableItem();
							linguisticVariable.setLinguisticVariableName(dataTemplateItem.getIndicatorName());
							linguisticVariable.setMaxDomainValue(getMaxValueOfHospital(itens, linguisticVariable.getLinguisticVariableName()));
							linguisticVariable.setDomainType(dataTemplateItem.getIndicatorName().substring(dataTemplateItem.getIndicatorName().indexOf("_"), dataTemplateItem.getIndicatorName().length()));
							//O Domaintype a princpio esta sendo atribuido os tipos "QTE" ou "VAL_TOT". 
							
							linguisticVariable.setLinguisticTerms(DataBaseGeneratorUtils.getInputTerms());
							
							List<FuzzySet> fuzzySetItens = new ArrayList<FuzzySet>();
							for (String  term: linguisticVariable.getLinguisticTerms()) {
								fuzzySetItens.add(generateFuzzySet(dataTemplateItem.getIndicatorValue(), term, linguisticVariable.getMaxDomainValue()));
							}
							linguisticVariable.setFuzzySetItens(fuzzySetItens);
							
							this.linguisticVariableItens.add(linguisticVariable);

						}
					}
				}
			}
		}
		return this.linguisticVariableItens;
	}
	
	public double getMaxValueOfHospital(List<DataTemplateItem> itens, String linguisticVariableName){
		double maxValue = 0;
		
		for (DataTemplateItem dataTemplateItem : itens) {
			if (dataTemplateItem.getIndicatorName().equals(linguisticVariableName)) {
				if (maxValue < (dataTemplateItem.getIndicatorValue() * 1.3)) {
					maxValue = dataTemplateItem.getIndicatorValue();
				}
			}
		}
		return maxValue;
	}
	
	public LinguisticVariableItem containsLinguisticVariable (String linguisticVariableItem){
		for (LinguisticVariableItem linguisticVariable : this.linguisticVariableItens) {
			if (linguisticVariable.getLinguisticVariableName().equals(linguisticVariableItem)) {
				return linguisticVariable;
			}
		}
		return null;
	}

	public FuzzySet generateFuzzySet(double indicatorValue, String term, double maxValue){
		FuzzySet fuzzySet = new FuzzySet();
		fuzzySet.setFuzzySetName(term);
		
		HashMap<Double, Double> fuzzySetItem = new HashMap<Double, Double>();
		fuzzySetItem.put(indicatorValue, normalizeFuzzySetValues(indicatorValue,term,maxValue));
		
		fuzzySet.setFuzzySetItens(fuzzySetItem);
		
		return fuzzySet;
	}
	
	public double normalizeFuzzySetValues(double value,String term, double maxValue){
		double valueNormalized = 0;
		double leftLimit= 0;
		double rightLimit= 0;
		
		
		// foram definidas as seguintes regras para o sistema:  
		// faixa de valores baixos entre 0% ~ 40% (em relação ao valor maximo)
		// faixa de valores medios entre 30% ~ 70% (em relação ao valor maximo)
		// faixa de valores altos entre 60% ~ 100% (em relação ao valor maximo)

		
		if (term.equalsIgnoreCase("baixo")) {
			leftLimit = 0;
			rightLimit = 0.4 * maxValue;
			if (value < leftLimit || value >  rightLimit) {
				valueNormalized = 0;
			}
			else {
				valueNormalized = 1 - ( (value - leftLimit) / (rightLimit - leftLimit) );
				
			}
			
		}
		if (term.equalsIgnoreCase("medio")) {
			leftLimit = 0.3 * maxValue;
			rightLimit = 0.7 * maxValue;
			
			if (value < leftLimit || value >  rightLimit) {
				valueNormalized = 0;
			}else{
				if(value/maxValue > 0.5){
					valueNormalized = 1 - ( (value - leftLimit) / (rightLimit - leftLimit) );
				}else {
					valueNormalized = ( (value - leftLimit) / (rightLimit - leftLimit) );
				}
			}
		}
		if (term.equalsIgnoreCase("alto")) {
			leftLimit = 0.6 * maxValue;
			rightLimit = maxValue;
			
			if (value < leftLimit || value >  rightLimit) {
				valueNormalized = 0;
			}
			else {
				valueNormalized = ( (value - leftLimit) / (rightLimit - leftLimit) );
			}
		}
		
		return valueNormalized;

	}

}
