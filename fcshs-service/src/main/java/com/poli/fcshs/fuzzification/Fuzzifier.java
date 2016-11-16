package com.poli.fcshs.fuzzification;

import java.util.HashMap;
import java.util.List;

import com.poli.fcshs.generator.KnowledgeBaseGenerator;
import com.poli.fcshs.model.FuzzySet;
import com.poli.fcshs.model.LinguisticVariableItem;
import com.poli.fcshs.model.SystemInputItem;

public class Fuzzifier implements IFuzzifier{

	private KnowledgeBaseGenerator fuzzificationLinguisticVariable;
	private List<LinguisticVariableItem> linguisticVariableItens; 
	
	public Fuzzifier(String hospitalName, String templateYear)
	{
		this.fuzzificationLinguisticVariable.generateSystemInputItensByYear(hospitalName, templateYear);
//		this.linguisticVariableItens = this.fuzzificationLinguisticVariable.generateSystemLinguisticVariables();
		
	}
	
	public Fuzzifier(String hospitalName)
	{
		this.fuzzificationLinguisticVariable.generateSystemInputItens(hospitalName);
//		this.linguisticVariableItens = this.fuzzificationLinguisticVariable.generateSystemLinguisticVariables();
		
	}
	
	public List<LinguisticVariableItem> getListLinguisticVariable(){
		
		for (SystemInputItem systemInputItem : fuzzificationLinguisticVariable.getInputSystemItens()) {
			LinguisticVariableItem linguisticVariableItem;
			
			linguisticVariableItem = getLinguisticVariableByName(systemInputItem.getItemTotalAmountName());
			if (linguisticVariableItem != null) {
				for (String term : linguisticVariableItem.getLinguisticTerms()) {
					linguisticVariableItem.getFuzzySetItens().add(generateFuzzySet(systemInputItem.getItemTotalAmountValue(), term, linguisticVariableItem.getMaxDomainValue()));
				}
			}
			
			linguisticVariableItem = getLinguisticVariableByName(systemInputItem.getItemUnitaryName());
			if (linguisticVariableItem != null) {
				for (String term : linguisticVariableItem.getLinguisticTerms()) {
					linguisticVariableItem.getFuzzySetItens().add(generateFuzzySet(systemInputItem.getItemUnitaryValue(), term, linguisticVariableItem.getMaxDomainValue()));
				}
			}
			
		}
		
		return this.linguisticVariableItens;
	}
	
	public LinguisticVariableItem getLinguisticVariableByName(String name){
		for (LinguisticVariableItem linguisticVariableItem : linguisticVariableItens) {
			if (linguisticVariableItem.getLinguisticVariableName().equals(name)) {
				return linguisticVariableItem;
			}
		}
		return null;
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


	public FuzzySet generateFuzzySet(double indicatorValue, String term, double maxValue){
		FuzzySet fuzzySet = new FuzzySet();
		fuzzySet.setFuzzySetName(term);
		
		HashMap<Double, Double> fuzzySetItem = new HashMap<Double, Double>();
		fuzzySetItem.put(indicatorValue, normalizeFuzzySetValues(indicatorValue,term,maxValue));
		
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
