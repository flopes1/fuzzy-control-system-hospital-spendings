package com.poli.fcshs.fuzzification;

import java.util.ArrayList;
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
		//Construtor de fuzzifica��o para as informa��es de uma planilha.
		this.fuzzificationLinguisticVariable = new KnowledgeBaseGenerator(templateYear);
		this.fuzzificationLinguisticVariable.generateSystemInputItensByYear(hospitalName, templateYear);
		this.linguisticVariableItens = this.fuzzificationLinguisticVariable.generateSystemLinguisticVariables();
	}
	
	public Fuzzifier(String hospitalName)
	{
		//Construtor de fuzzifica��o para as informa��es de todas planilhas.
		this.fuzzificationLinguisticVariable = new KnowledgeBaseGenerator();
		this.fuzzificationLinguisticVariable.generateSystemInputItens(hospitalName);
		this.linguisticVariableItens = this.fuzzificationLinguisticVariable.generateSystemLinguisticVariables();
	}

	//M�todo que preenche os valores na lista de var�aveis linguisticas, geradas em KnowledgeBaseGenerator.
	public List<LinguisticVariableItem> getListlinguisticVariablewWithValues(){
		LinguisticVariableItem linguisticVariableItem;
		//Loop que percorre lista com os valores da(s) planilha(s). (Essa lista � preenchida na chamada do m�todo feita no construtor).
		for (SystemInputItem systemInputItem : this.fuzzificationLinguisticVariable.getInputSystemItens()) {
				//Retorna a vari�vel linguistica com mesmo nome do primeiro DataTemplateItem dentro systemInputItem.
				linguisticVariableItem = getlinguisticVariableByName(systemInputItem.getItemTotalAmountName());
				if (linguisticVariableItem != null) {
					for (String term : linguisticVariableItem.getLinguisticTerms()) {
						//Para cada termo da vari�vel linguistica � criado um fuzzySet com  mapeamento do valor da planilha para um valor normalizado.
						//Esse mapeamento � adicionada na vari�vel linguistica.
						boolean isUpdateFuzzySet = false;
						for (FuzzySet fuzzySetItem : linguisticVariableItem.getFuzzySetItens()) {
							if (fuzzySetItem.getFuzzySetName().equals(term)) {
								fuzzySetItem.getFuzzySetItens().put((double)fuzzySetItem.getFuzzySetItens().size(), FuzzifierUtils.normalizeFuzzySetValues(systemInputItem.getItemTotalAmountValue(),term, linguisticVariableItem.getMinDomainValue(), linguisticVariableItem.getMaxDomainValue()));
								isUpdateFuzzySet = true;
							}
							
						}
						if (!isUpdateFuzzySet) {
							//Caso ja exista um fuzzySet criado para o termo,ent�o apenas atualiza com um novo mapeamento o fuzzySet existente.
							linguisticVariableItem.getFuzzySetItens().add(generateFuzzySet(systemInputItem.getItemTotalAmountValue(), term, linguisticVariableItem.getMinDomainValue(),linguisticVariableItem.getMaxDomainValue()));
						}
					}
				}
				
				//Retorna a vari�vel linguistica com mesmo nome do segundo DataTemplateItem dentro systemInputItem.
				linguisticVariableItem = getlinguisticVariableByName(systemInputItem.getItemUnitaryName());
				if (linguisticVariableItem != null) {
					for (String term : linguisticVariableItem.getLinguisticTerms()) {
						//Para cada termo da vari�vel linguistica � criado um fuzzySet com  mapeamento do valor da planilha para um valor normalizado.
						//Esse mapeamento � adicionada na vari�vel linguistica.
						boolean isUpdateFuzzySet = false;
						for (FuzzySet fuzzySetItem : linguisticVariableItem.getFuzzySetItens()) {
							if (fuzzySetItem.getFuzzySetName().equals(term)) {
								fuzzySetItem.getFuzzySetItens().put((double)fuzzySetItem.getFuzzySetItens().size(), FuzzifierUtils.normalizeFuzzySetValues(systemInputItem.getItemUnitaryValue(),term, linguisticVariableItem.getMinDomainValue(), linguisticVariableItem.getMaxDomainValue()));
								isUpdateFuzzySet = true;
							}
						}
						if (!isUpdateFuzzySet) {
							//Caso ja exista um fuzzySet criado para o termo,ent�o apenas atualiza com um novo mapeamento o fuzzySet existente.
							linguisticVariableItem.getFuzzySetItens().add(generateFuzzySet(systemInputItem.getItemUnitaryValue(), term, linguisticVariableItem.getMinDomainValue(),linguisticVariableItem.getMaxDomainValue()));
						}
						
					}
				}
				
		}

		return this.linguisticVariableItens;
	}
	
	//M�todo que verifica se existe um a var�avel linguistica com o mesmo nome passado como parametro.
	//Caso exista retorna a vari�vel lingu�stica.
	public LinguisticVariableItem getlinguisticVariableByName(String name){
		for (LinguisticVariableItem linguisticVariableItem : this.linguisticVariableItens) {
			if (linguisticVariableItem.getLinguisticVariableName().equals(name)) {
				return linguisticVariableItem;
			}
		}
		return null;
	}
	
	

	//Cria um novo fuzzySet com o nome passado e um mapeamento entre o valor passado e valor normalizado.
	public FuzzySet generateFuzzySet(double indicatorValue, String term, double minValue ,double maxValue){
		FuzzySet fuzzySet = new FuzzySet();
		fuzzySet.setFuzzySetName(term);
		//Nome do fuzzySet.
		
		HashMap<Double, Double> fuzzySetItem = new HashMap<Double, Double>();
		
		fuzzySetItem.put((double)fuzzySet.getFuzzySetItens().size(), FuzzifierUtils.normalizeFuzzySetValues(indicatorValue,term, minValue,maxValue));
		//Mapeamento com o valor e valorNormalizado.
		
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
