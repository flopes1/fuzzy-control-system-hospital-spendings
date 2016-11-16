package com.poli.fcshs.inference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.poli.fcshs.generator.util.DataBaseGeneratorUtils;
import com.poli.fcshs.model.FuzzySet;
import com.poli.fcshs.model.LinguisticVariableItem;
import com.poli.fcshs.model.SystemRules;
import com.poli.fcshs.rules.ExtractorRules;

public class Inference implements IInference
{
	ExtractorRules rules;
	
	
	public Inference()
	{
		this.rules = new ExtractorRules();
	}
	
	
	public LinguisticVariableItem inference (List<LinguisticVariableItem> linguisticVariableList){
		
		List<SystemRules> rulesList = rules.extractRules();
		
		LinguisticVariableItem variableItem = new LinguisticVariableItem();
		
		variableItem.setLinguisticVariableName(rulesList.get(0).getOperands().get(rulesList.get(0).getOperands().size() - 1));
		variableItem.setLinguisticTerms(DataBaseGeneratorUtils.getOutputTerms());
		
		for (String term : variableItem.getLinguisticTerms())
		{
			//terminar de construir  variableitem...
			FuzzySet fuzzySet =  new FuzzySet();
			fuzzySet.setFuzzySetName(term);
			
			variableItem.getFuzzySetItens().add(fuzzySet);
		
		}
		
		
		for (SystemRules systemRules : rulesList)			
		{
			
			
			List<Double> listValues = new ArrayList<Double>();
			
			for (LinguisticVariableItem linguisticVariables : linguisticVariableList)
			{
				for (int i = 0; i < systemRules.getOperands().size(); i+=0)
				{
					if(systemRules.getOperands().get(i).equals(linguisticVariables.getLinguisticVariableName()))
					{
						List <FuzzySet> fuzzySetList = linguisticVariables.getFuzzySetItens();
						
						for (FuzzySet fuzzySet : fuzzySetList)
						{
							Double average = 0.0;
							
							 for (Double key : fuzzySet.getFuzzySetItens().keySet()) {
			                      
								 	average += fuzzySet.getFuzzySetItens().get(key);
			                      
							 }
							 average /= fuzzySet.getFuzzySetItens().size();
							 
//							 fuzzySet.getFuzzySetItens().clear();
//							 fuzzySet.getFuzzySetItens().put(0.0, average);
							 
							 String[] variableLinguisticName = systemRules.getOutput().split(",");
							if (variableLinguisticName[1].equals(fuzzySet.getFuzzySetName()))
							{
								//fazer a media dos fuzzysetitens
								listValues.add(average);
							}
						}
						
						
					}
				}
			}
			
			Double before = listValues.get(0) ;
			
			for (int k = 0; k < systemRules.getOperators().size(); k++)
			{
				
				if (systemRules.getOperators().get(k).equalsIgnoreCase("E"))
				{
					if (listValues.get(k+1) < before)
					{
						before = listValues.get(k+1);
					}
				}else{
					if (listValues.get(k+1) > before)
					{
						before = listValues.get(k+1);
					}
					
				}
				
				
			}	
			
			//
//			fuzzySetItem.put(indicatorValue, normalizeFuzzySetValues(indicatorValue,term,maxValue));
			
//			fuzzySet.setFuzzySetItens(fuzzySetItem);
			
			for (FuzzySet fuzzySet : variableItem.getFuzzySetItens()){
				String[] variableLinguisticName = systemRules.getOutput().split(",");
				if (fuzzySet.getFuzzySetName().equals(variableLinguisticName[0]))
				{
					//no hashmap pode substituir um valor que já tinha, não criando um novo.
					fuzzySet.getFuzzySetItens().put(before, before);
					
				}
			}
			
		}
		
		return variableItem;
		
	} 
	
}
