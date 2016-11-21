package com.poli.fcshs.generator;

import java.util.ArrayList;
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
	private List<DataTemplateFile> allDataTemplateFile;
	private DataTemplateExtractor dataTemplateExtractor;
	private List<LinguisticVariableItem> linguisticVariableItens;
	private List<SystemInputItem> inputSystemItens;
	private static double DOMAIN_VALUE_MULTIPLIFIER = Double.valueOf(
			FcshsPropertiesLoader.getInstance().getPropertyByName(FcshsSetupConstants.ITEM_MAX_VALUE_MULTIPLIFIER));
	
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
		this.allDataTemplateFile = new ArrayList<DataTemplateFile>();
		
	}
	
	public KnowledgeBaseGenerator()
	{
		this("");
	}

	//De acordo com o que foi proposto este método deve retornar a lista de itens de um hospital
	//e portanto deve ter acesso ao hospital em questão, neste caso foi considerado o nome do hospital.
	
	public List<SystemInputItem> generateSystemInputItensByYear(String hospitalName, String year){
		int count;
		this.allDataTemplateFile.add(this.dataTemplateExtractor.extractDataByName(year));  
		
		for (DataTemplateFile dataTemplateFile : allDataTemplateFile) {
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
		}

		return this.inputSystemItens;
	}

	
	public List<SystemInputItem> generateSystemInputItens(String hospitalName){
		int count;
		this.allDataTemplateFile = this.dataTemplateExtractor.extractAllData();
		
		for (DataTemplateFile dataTemplateFile : allDataTemplateFile) {
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
		}

		return this.inputSystemItens;
	}

	//OBS: Alterar para utilizar lista de inputSystemItens (melhora de codigo)
	public List<LinguisticVariableItem> generateSystemLinguisticVariables(String hospitalName){
		
		for (DataTemplateFile dataTemplateFile : this.allDataTemplateFile) {
			for (Hospital hospital : dataTemplateFile.getHospitals()) {
				if (hospital.getName().equals(hospitalName)) {
					for (Month month : hospital.getMonths()) {
						List<DataTemplateItem> itens = month.getDataTemplateItens();
						for (DataTemplateItem dataTemplateItem : itens) {
							LinguisticVariableItem linguisticVariable = containsLinguisticVariable(dataTemplateItem.getIndicatorName());
							if (linguisticVariable == null) {
								linguisticVariable = new LinguisticVariableItem();
								linguisticVariable.setLinguisticVariableName(dataTemplateItem.getIndicatorName());
								linguisticVariable.setMaxDomainValue(getMaxValueOfHospital(hospital.getMonths(), linguisticVariable.getLinguisticVariableName()));
								linguisticVariable.setDomainType(dataTemplateItem.getIndicatorName().substring(dataTemplateItem.getIndicatorName().indexOf("_"), dataTemplateItem.getIndicatorName().length()));
								//O Domaintype a princpio esta sendo atribuido os tipos "QTE" ou "VAL_TOT". 
								
								linguisticVariable.setLinguisticTerms(DataBaseGeneratorUtils.getInputTerms());
								
								List<FuzzySet> fuzzySetItens = new ArrayList<FuzzySet>();
								
								linguisticVariable.setFuzzySetItens(fuzzySetItens);
								
								this.linguisticVariableItens.add(linguisticVariable);
							}
						}
					}
				}
			}
		}
		return this.linguisticVariableItens;
	}
	
	public double getMaxValueOfHospital(List<Month> months, String linguisticVariableName){
		double maxValue = 0;
		for (Month month : months) {
			for (DataTemplateItem dataTemplateItem : month.getDataTemplateItens()) {
				if (dataTemplateItem.getIndicatorName().equals(linguisticVariableName)) {
					if (maxValue < dataTemplateItem.getIndicatorValue()) {
						maxValue = dataTemplateItem.getIndicatorValue();
					}
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
	
	public List<SystemInputItem> getInputSystemItens() {
		return inputSystemItens;
	}

	public void setInputSystemItens(List<SystemInputItem> inputSystemItens) {
		this.inputSystemItens = inputSystemItens;
	}

}
