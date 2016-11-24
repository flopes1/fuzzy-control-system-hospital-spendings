package com.poli.fcshs.inference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.poli.fcshs.fuzzification.Fuzzifier;
import com.poli.fcshs.fuzzification.util.FuzzifierUtils;
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
	
	//metodo que retorna um objeto linguisticVariableItem que terá um fuzzyset de cada termo (com um mapeamento de 0 a 100
	//com seu grau de pertinencia).
	public LinguisticVariableItem inference (List<LinguisticVariableItem> linguisticVariableList){
		
		//objeto que contém as regras
		List<SystemRules> rulesList = rules.extractRules();
		//objeto que será retornado
		LinguisticVariableItem variableItem = new LinguisticVariableItem();

		String[] variableLinguisticNameOutput = rulesList.get(0).getOutput().split(",");
		
		//seta as informações basicas do objeto a ser retornado.
		variableItem.setLinguisticVariableName(variableLinguisticNameOutput[0]);
		variableItem.setLinguisticTerms(DataBaseGeneratorUtils.getOutputTerms());
		variableItem.setDomainType("%");
		variableItem.setMaxDomainValue(100);
		
		//cria um fuzzyset para cada termo linguistico para o objeto a ser retornado.
		for (String term : variableItem.getLinguisticTerms())
		{
			FuzzySet fuzzySet =  new FuzzySet();
			fuzzySet.setFuzzySetName(term);
			
			variableItem.getFuzzySetItens().add(fuzzySet);
		
		}
		
		//verifica cada regra
		for (SystemRules systemRules : rulesList)			
		{
			
			//lista que contém os valores normalizados de cada operando, para fazer as operações com conjuntos em cima deles.
			List<Double> listValues = new ArrayList<Double>();
			
			//verifica cada linguisticvariableitem para saber se faz parte da regra.
			for (LinguisticVariableItem linguisticVariables : linguisticVariableList)
			{
				//percorre a lista de operandos das regras
				for (int i = 0; i < systemRules.getOperands().size(); i++)
				{
					//se o operando na posição i(da regra) for igual a da linguisticvariableitem
					if(systemRules.getOperands().get(i).substring(0,systemRules.getOperands().get(i).lastIndexOf(",") ).equals(linguisticVariables.getLinguisticVariableName()))
					{
						List <FuzzySet> fuzzySetList = linguisticVariables.getFuzzySetItens();
						//percorre todos os fuzzyset de linguisticvariableitem para tirar a média dos valores
						for (FuzzySet fuzzySet : fuzzySetList)
						{
							Double average = 0.0;
							int count=0;
							//percorre todo o hashmap para fazer a media dos valores.
							 for (Double key : fuzzySet.getFuzzySetItens().keySet()) {
			                      
								 if (fuzzySet.getFuzzySetItens().get(key)>=0)
								{
									 //soma todos os valores (maior que zero) associados a cada key
									 	average += fuzzySet.getFuzzySetItens().get(key);
									 	count++;
								}
								
							 }
							 //divide a soma de todos os valores pelo numero de itens para obter a média
							 average /= count;
							 
							 
							 //verifica se o termo da regra é igual ao nome termo do fuzzyset, se for o valor será adicionado na listValues
							 //para fazer as operações sobre os conjuntos.
							 String[] variableLinguisticName = systemRules.getOperands().get(i).split(",");
							if (variableLinguisticName[1].equals(fuzzySet.getFuzzySetName()))
							{
								//adiciona em uma lista todos os valores (média) da linguisticvariableitem que batem com a regra
								listValues.add(average);
							}
						}
						
						
					}
				}
			}
			
			
			//faz a operação com conjuntos
			Double before = listValues.get(0) ;
			for (int k = 0; k < systemRules.getOperators().size(); k++)
			{
				//se o operador for igual a E o resultado é o menor valor entre os dois
				if (systemRules.getOperators().get(k).equalsIgnoreCase("E"))
				{
					if (listValues.get(k+1) < before)
					{
						before = listValues.get(k+1);
					}
					//se o operador for igual a OU o resultado é o maior valor entre os dois
				}else{
					if (listValues.get(k+1) > before)
					{
						before = listValues.get(k+1);
					}
					
				}
				
				
			}	
			System.out.println(before);
			
			//percorre todos os fuzzyset da variavel de saída para preencher os valores que foram encontrados nas operações com conjuntos
			for (FuzzySet fuzzySet : variableItem.getFuzzySetItens()){
				String[] variableLinguisticName = systemRules.getOutput().split(",");
				//se o nome (termo) do fuzzyset de saida for igual ao nome (termo) da conclusao da regra
				if (fuzzySet.getFuzzySetName().equals(variableLinguisticName[1]))
				{
					//se o nome (termo) do fuzzyset de saida for igual a baixo, é mapeado com chaves de 0 a 100 e
					//os valores correspondentes a função de pertinencia(de baixo), nao podendo ser maior que o 
					//resultado calculado da regra.
					if (fuzzySet.getFuzzySetName().equals(DataBaseGeneratorUtils.getOutputTerms().get(0)))
					{
						for (Double i = 0.0; i <= 100.0; i+=0.5)
						{
							Double value = FuzzifierUtils.normalizeFuzzySetValues(i, fuzzySet.getFuzzySetName(), 100);
							if (before > value)
							{
								//faz a verificação pra saber se alguma regra já tinha setado esse fuzzyset, se tiver o valor
								//adicionado será a media do novo valor com o valor anterior.
								if (fuzzySet.getFuzzySetItens().containsKey(i))
								{
									fuzzySet.getFuzzySetItens().put(i, ((fuzzySet.getFuzzySetItens().get(i) + value)/2));
								}else
								{
									fuzzySet.getFuzzySetItens().put(i, value);
								}
							}else {
								if (fuzzySet.getFuzzySetItens().containsKey(i))
								{
									fuzzySet.getFuzzySetItens().put(i, ((fuzzySet.getFuzzySetItens().get(i) + before)/2));
								}else
								{
									fuzzySet.getFuzzySetItens().put(i, before);
								}							}
						}
						//mesmo mapeamento, só que pra medio.
					}else if (fuzzySet.getFuzzySetName().equals(DataBaseGeneratorUtils.getOutputTerms().get(1))) {
						
						for (Double i = 0.0; i <= 100.0; i+=0.5)
						{
							Double value = FuzzifierUtils.normalizeFuzzySetValues(i, fuzzySet.getFuzzySetName(), 100);
							if (before > value)
							{
								//faz a verificação pra saber se alguma regra já tinha setado esse fuzzyset, se tiver o valor
								//adicionado será a media do novo valor com o valor anterior.
								if (fuzzySet.getFuzzySetItens().containsKey(i))
								{
									fuzzySet.getFuzzySetItens().put(i, ((fuzzySet.getFuzzySetItens().get(i) + value)/2));
								}else
								{
									fuzzySet.getFuzzySetItens().put(i, value);
								}							}else {
									if (fuzzySet.getFuzzySetItens().containsKey(i))
									{
										fuzzySet.getFuzzySetItens().put(i, ((fuzzySet.getFuzzySetItens().get(i) + before)/2));
									}else
									{
										fuzzySet.getFuzzySetItens().put(i, before);
									}							}
						}
						//mesmo mapeamento só que pra alto.
					}else
					{
						for (Double i = 0.0; i <= 100.0; i+=0.5)
						{
							Double value = FuzzifierUtils.normalizeFuzzySetValues(i, fuzzySet.getFuzzySetName(), 100);
							if (before > value)
							{
								//faz a verificação pra saber se alguma regra já tinha setado esse fuzzyset, se tiver o valor
								//adicionado será a media do novo valor com o valor anterior.
								if (fuzzySet.getFuzzySetItens().containsKey(i))
								{
									fuzzySet.getFuzzySetItens().put(i, ((fuzzySet.getFuzzySetItens().get(i) + value)/2));
								}else
								{
									fuzzySet.getFuzzySetItens().put(i, value);
								}
								
								
							}else {

								if (fuzzySet.getFuzzySetItens().containsKey(i))
								{
									fuzzySet.getFuzzySetItens().put(i, ((fuzzySet.getFuzzySetItens().get(i) + before)/2));
								}else
								{
									fuzzySet.getFuzzySetItens().put(i, before);
								}
								
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
		
		// É calculado um hashMap que tenha a combinação dos hashMaps baixo,medio e alto 
		//(pegando o maior valor para determinada key entre os três hashMaps)
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
		finalFuzzySet.setFuzzySetName("indice de qualidade");
		List<FuzzySet> fuzzySetList = new ArrayList<FuzzySet>();
		fuzzySetList.add(finalFuzzySet);
		
		variableItem.setFuzzySetItens(fuzzySetList);
		
		return variableItem;
		
	} 
	

	
}
