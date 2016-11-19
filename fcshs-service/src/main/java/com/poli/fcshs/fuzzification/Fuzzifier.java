package com.poli.fcshs.fuzzification;

import java.util.HashMap;
import java.util.List;

import com.poli.fcshs.fuzzification.util.FuzzifierUtils;
import com.poli.fcshs.generator.KnowledgeBaseGenerator;
import com.poli.fcshs.model.FuzzySet;
import com.poli.fcshs.model.LinguisticVariableItem;
import com.poli.fcshs.model.SystemInputItem;

public class Fuzzifier implements IFuzzifier{

	private KnowledgeBaseGenerator fuzzificationLinguisticVariable;
	private List<LinguisticVariableItem> linguisticVariableItens; 
	
	public Fuzzifier(String hospitalName, String templateYear)
	{
		this.fuzzificationLinguisticVariable = new KnowledgeBaseGenerator(templateYear);
		this.fuzzificationLinguisticVariable.generateSystemInputItensByYear(hospitalName, templateYear);
		this.linguisticVariableItens = this.fuzzificationLinguisticVariable.generateSystemLinguisticVariables(hospitalName);
	}
	
	public Fuzzifier(String hospitalName)
	{
		this.fuzzificationLinguisticVariable = new KnowledgeBaseGenerator();
		this.fuzzificationLinguisticVariable.generateSystemInputItens(hospitalName);
		this.linguisticVariableItens = this.fuzzificationLinguisticVariable.generateSystemLinguisticVariables(hospitalName);
	}

	public List<LinguisticVariableItem> getListlinguisticVariablewWithValues(){
		LinguisticVariableItem linguisticVariableItem;
		for (SystemInputItem systemInputItem : this.fuzzificationLinguisticVariable.getInputSystemItens()) {
				linguisticVariableItem = getlinguisticVariableByName(systemInputItem.getItemTotalAmountName());
				if (linguisticVariableItem != null) {
					for (String term : linguisticVariableItem.getLinguisticTerms()) {
						boolean isUpdateFuzzySet = false;
						for (FuzzySet fuzzySetItem : linguisticVariableItem.getFuzzySetItens()) {
							if (fuzzySetItem.getFuzzySetName().equals(term)) {
								fuzzySetItem.getFuzzySetItens().put(systemInputItem.getItemTotalAmountValue(), FuzzifierUtils.normalizeFuzzySetValues(systemInputItem.getItemTotalAmountValue(),term, linguisticVariableItem.getMaxDomainValue()));
								isUpdateFuzzySet = true;
							}
							
						}
						if (!isUpdateFuzzySet) {
							linguisticVariableItem.getFuzzySetItens().add(generateFuzzySet(systemInputItem.getItemTotalAmountValue(), term, linguisticVariableItem.getMaxDomainValue()));
						}
					}
				}
				
				linguisticVariableItem = getlinguisticVariableByName(systemInputItem.getItemUnitaryName());
				if (linguisticVariableItem != null) {
					for (String term : linguisticVariableItem.getLinguisticTerms()) {
						boolean isUpdateFuzzySet = false;
						for (FuzzySet fuzzySetItem : linguisticVariableItem.getFuzzySetItens()) {
							if (fuzzySetItem.getFuzzySetName().equals(term)) {
								fuzzySetItem.getFuzzySetItens().put(systemInputItem.getItemUnitaryValue(), FuzzifierUtils.normalizeFuzzySetValues(systemInputItem.getItemTotalAmountValue(),term, linguisticVariableItem.getMaxDomainValue()));
								isUpdateFuzzySet = true;
							}
						}
						if (!isUpdateFuzzySet) {
							linguisticVariableItem.getFuzzySetItens().add(generateFuzzySet(systemInputItem.getItemUnitaryValue(), term, linguisticVariableItem.getMaxDomainValue()));
						}
						
					}
				}
				
		}

		return this.linguisticVariableItens;
	}
	
	public LinguisticVariableItem getlinguisticVariableByName(String name){
		for (LinguisticVariableItem linguisticVariableItem : this.linguisticVariableItens) {
			if (linguisticVariableItem.getLinguisticVariableName().equals(name)) {
				return linguisticVariableItem;
			}
		}
		return null;
	}
	
	


	public FuzzySet generateFuzzySet(double indicatorValue, String term, double maxValue){
		FuzzySet fuzzySet = new FuzzySet();
		fuzzySet.setFuzzySetName(term);
		
		HashMap<Double, Double> fuzzySetItem = new HashMap<Double, Double>();
		fuzzySetItem.put(indicatorValue, FuzzifierUtils.normalizeFuzzySetValues(indicatorValue,term,maxValue));
		
		fuzzySet.setFuzzySetItens(fuzzySetItem);
		
		return fuzzySet;
	}


	
	
	public KnowledgeBaseGenerator getFuzzificationLinguisticVariable() {
		return fuzzificationLinguisticVariable;
	}


	public void setFuzzificationLinguisticVariable(KnowledgeBaseGenerator fuzzificationLinguisticVariable) {
		this.fuzzificationLinguisticVariable = fuzzificationLinguisticVariable;
	}
	
}
