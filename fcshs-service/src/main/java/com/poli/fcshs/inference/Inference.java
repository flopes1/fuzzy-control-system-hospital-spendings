package com.poli.fcshs.inference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.poli.fcshs.fuzzification.Fuzzifier;
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
				//se o nome (termo) do fuzzyset de saida for igual ao nome (termo) da conclusao da regra
				if (fuzzySet.getFuzzySetName().equals(variableLinguisticName[1]))
				{
					//se o nome (termo) do fuzzyset de saida for igual a baixo, � mapeado com chaves de 0 a 100
					//os valores correspondentes a fun��o de pertinencia(de baixo), nao podendo ser maior que o 
					//resultado calculado da regra.
					if (fuzzySet.getFuzzySetName().equals(DataBaseGeneratorUtils.getOutputTerms().get(0)))
					{
						for (Double i = 0.0; i <= 100.0; i+=0.5)
						{
							Double value = normalizeFuzzySetValues(i, fuzzySet.getFuzzySetName(), 100);
							if (before > value)
							{
								fuzzySet.getFuzzySetItens().put(i, value);
							}else {
								fuzzySet.getFuzzySetItens().put(i, before);
							}
						}
						//mesmo mapeamento, s� que pra medio.
					}else if (fuzzySet.getFuzzySetName().equals(DataBaseGeneratorUtils.getOutputTerms().get(1))) {
						
						for (Double i = 0.0; i <= 100.0; i+=0.5)
						{
							Double value = normalizeFuzzySetValues(i, fuzzySet.getFuzzySetName(), 100);
							if (before > value)
							{
								fuzzySet.getFuzzySetItens().put(i, value);
							}else {
								fuzzySet.getFuzzySetItens().put(i, before);
							}
						}
						//mesmo mapeamento s� que pra alto.
					}else
					{
						for (Double i = 0.0; i <= 100.0; i+=0.5)
						{
							Double value = normalizeFuzzySetValues(i, fuzzySet.getFuzzySetName(), 100);
							if (before > value)
							{
								fuzzySet.getFuzzySetItens().put(i, value);
							}else {
								fuzzySet.getFuzzySetItens().put(i, before);
							}
						}
						
					}
					
				}
			}
			
		}
		
		
		HashMap<Double, Double> baixo = variableItem.getFuzzySetItens().get(0).getFuzzySetItens();
		HashMap<Double, Double> medio = variableItem.getFuzzySetItens().get(1).getFuzzySetItens();
		HashMap<Double, Double> alto = variableItem.getFuzzySetItens().get(2).getFuzzySetItens();
		HashMap<Double, Double> resultHashMap = new HashMap<Double, Double>();
		
		Double resultvalue;
		
		// � calculado um hashMap que tenha a combina��o dos hashMaps baixo,medio e alto 
		//(pegando o maior valor para determinada key entre os tr�s hashMaps)
		for (Double i = 0.0; i <= 100.0; i+=0.5)
		{
			resultvalue = 0.0;
			
			if (baixo.get(i) > medio.get(i) && baixo.get(i) > alto.get(i))
			{
				resultvalue = baixo.get(i);			                      
			}else if (medio.get(i) > alto.get(i))
			{		               
				resultvalue = medio.get(i);
			}else
			{
				resultvalue = alto.get(i);
			}
			
			resultHashMap.put(i, resultvalue);
		}
		
		//cria uma nova lista de fuzzyset para substituir na variavel variableItem.
		FuzzySet finalFuzzySet = new FuzzySet();
		finalFuzzySet.setFuzzySetItens(resultHashMap);
		//mudar nome depois.
		finalFuzzySet.setFuzzySetName("indice");
		List<FuzzySet> fuzzySetList = new ArrayList<FuzzySet>();
		fuzzySetList.add(finalFuzzySet);
		
		variableItem.setFuzzySetItens(fuzzySetList);
		
		return variableItem;
		
	} 
	
	public double normalizeFuzzySetValues(double value,String term, double maxValue){
		double valueNormalized = 0;
		double leftLimit= 0;
		double rightLimit= 0;
		
		maxValue/= 1.3;
		
		
		// foram definidas as seguintes regras para o sistema:  
		// faixa de valores baixos entre 0% ~ 40% (em rela��o ao valor maximo)
		// faixa de valores medios entre 30% ~ 70% (em rela��o ao valor maximo)
		// faixa de valores altos entre 60% ~ 100% (em rela��o ao valor maximo)

		
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
