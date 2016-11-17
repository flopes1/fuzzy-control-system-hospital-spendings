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
		
		//objeto que cont�m as regras
		List<SystemRules> rulesList = rules.extractRules();
		//objeto que ser� retornado
		LinguisticVariableItem variableItem = new LinguisticVariableItem();

		String[] variableLinguisticNameOutput = rulesList.get(0).getOutput().split(",");
		
		
		variableItem.setLinguisticVariableName(variableLinguisticNameOutput[0]);
		variableItem.setLinguisticTerms(DataBaseGeneratorUtils.getOutputTerms());
		
		//cria um fuzzyset para cada termo linguistico
		for (String term : variableItem.getLinguisticTerms())
		{
			FuzzySet fuzzySet =  new FuzzySet();
			fuzzySet.setFuzzySetName(term);
			
			variableItem.getFuzzySetItens().add(fuzzySet);
		
		}
		
		//verifica cada regra
		for (SystemRules systemRules : rulesList)			
		{
			
			//lista que cont�m os valores normalizados de cada operando
			List<Double> listValues = new ArrayList<Double>();
			
			//verifica cada linguisticvariableitem para saber se faz parte da regra
			for (LinguisticVariableItem linguisticVariables : linguisticVariableList)
			{
				//percorre a lista de operandos
				for (int i = 0; i < systemRules.getOperands().size(); i++)
				{
					//se o perando na posi��o i(da regra) for igual a da linguisticvariableitem
					if(systemRules.getOperands().get(i).substring(0,systemRules.getOperands().get(i).lastIndexOf(",") ).equals(linguisticVariables.getLinguisticVariableName()))
					{
						List <FuzzySet> fuzzySetList = linguisticVariables.getFuzzySetItens();
						//percorre todos os fuzzyset de linguisticvariableitem para tirar a m�dia dos valores
						for (FuzzySet fuzzySet : fuzzySetList)
						{
							Double average = 0.0;
							//pega todas as keys
							 for (Double key : fuzzySet.getFuzzySetItens().keySet()) {
			                      //soma todos os valores associados a cada key
								 	average += fuzzySet.getFuzzySetItens().get(key);
			                      
							 }
							 //divide a soma de todos os valores pelo numero de itens para obter a m�dia
							 average /= fuzzySet.getFuzzySetItens().size();
							 
							 //verifica se o termo da regra � igual ao nome termo do fuzzyset
							 String[] variableLinguisticName = systemRules.getOutput().split(",");
							if (variableLinguisticName[1].equals(fuzzySet.getFuzzySetName()))
							{
								//adiciona em uma lista todos os valores (m�dia) da linguisticvariableitem que batem com a regra
								listValues.add(average);
							}
						}
						
						
					}
				}
			}
			
			
			//faz a opera��o com conjuntos
			Double before = listValues.get(0) ;
			for (int k = 0; k < systemRules.getOperators().size(); k++)
			{
				//se o operador for igual a E o resultado � o menor valor entre os dois
				if (systemRules.getOperators().get(k).equalsIgnoreCase("E"))
				{
					if (listValues.get(k+1) < before)
					{
						before = listValues.get(k+1);
					}
					//se o operador for igual a OU o resultado � o maior valor entre os dois
				}else{
					if (listValues.get(k+1) > before)
					{
						before = listValues.get(k+1);
					}
					
				}
				
				
			}	
			
			//percorre todos os fuzzyset da variavel de sa�da para preencher os valores que foram encontrados nas opera��es com conjuntos
			for (FuzzySet fuzzySet : variableItem.getFuzzySetItens()){
				String[] variableLinguisticName = systemRules.getOutput().split(",");
				if (fuzzySet.getFuzzySetName().equals(variableLinguisticName[1]))
				{
					//no hashmap pode substituir um valor que j� tinha, n�o criando um novo.
					fuzzySet.getFuzzySetItens().put(before, before);
					
				}
			}
			
		}
		
		return variableItem;
		
	} 
	
}
