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

	//Método responsável por listar os dados de entrada, fornecidos pela planilha no sistema que serão usados na fuzzificação.
	public List<SystemInputItem> generateSystemInputItensByYear(String hospitalName, String year)
	{
		int count;
		this.allDataTemplateFile.add(this.dataTemplateExtractor.extractDataByName(year));
		//Lista com as informações da planilha

		//Loop que percorre os dados da lista acima e monta a lista de objetos usados pela fuzzificação.
		for (DataTemplateFile dataTemplateFile : allDataTemplateFile)
		{
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
							//Montagem dos objetos (SystemInputItem) que estarão na lista.
							//O objeto conterá um nome, um par de informações das colunas sob mesmo dominio (ex: Paciente tem sob seu domínio Paciente_QTD e Pacient_VAL_TOT)
							//e o mês do qual a informação corresponde na planilha.
							
							SystemInputItem systemInputItem = new SystemInputItem();
							systemInputItem.setInputName(itens.get(count).getIndicatorName().substring(0,
									itens.get(count).getIndicatorName().lastIndexOf("_")));
							systemInputItem.setItemTotalAmount(itens.get(count));
							systemInputItem.setItemUnitaryValue(itens.get(count + 1));
							systemInputItem.setMonth(month.getMonth());
							this.inputSystemItens.add(systemInputItem);
							count += 2;
						}
					}
				}
			}
		}

		return this.inputSystemItens;
	}

	//Funcionamento idêntico ao método acima, com exceção que as informações da lista será referente a todas as planilhas do diretório.
	public List<SystemInputItem> generateSystemInputItens(String hospitalName)
	{
		int count;
		this.allDataTemplateFile = this.dataTemplateExtractor.extractAllData();
		//Extrai a lista de informações de todas as planilhas.

		for (DataTemplateFile dataTemplateFile : allDataTemplateFile)
		{
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
							//Montagem dos objetos (SystemInputItem) que estarão na lista.
							//O objeto conterá um nome, um par de informações das colunas sob mesmo dominio (ex: Paciente tem sob seu domínio Paciente_QTD e Pacient_VAL_TOT)
							//e o mês do qual a informação corresponde na planilha.
							SystemInputItem systemInputItem = new SystemInputItem();
							systemInputItem.setInputName(itens.get(count).getIndicatorName().substring(0,
									itens.get(count).getIndicatorName().lastIndexOf("_")));
							systemInputItem.setItemTotalAmount(itens.get(count));
							systemInputItem.setItemUnitaryValue(itens.get(count + 1));
							systemInputItem.setMonth(month.getMonth());
							this.inputSystemItens.add(systemInputItem);
							count += 2;
						}
					}
				}
			}
		}

		return this.inputSystemItens;
	}

	//Este método cria a lista de variáveis linguisticas. Essa lista será preenchida com os valores na Fuzzicação.
	//Este método usa as informações da lista de SystemInputItem.
	public List<LinguisticVariableItem> generateSystemLinguisticVariables(){
		for (SystemInputItem systemInputItem : this.inputSystemItens) {
			LinguisticVariableItem linguisticVariable = containsLinguisticVariable(systemInputItem.getItemTotalAmountName());
			//Verifica se ja existe uma variável linguistica com mesmo nome do primeiro DataTemplateItem dentro systemInputItem.
			if (linguisticVariable == null) {
				//Caso não exista essa variavel linguistica, cria uma nova.
				//A variavel linguistica é criada com: nome, valor maximo que assume, lista de termos e a lista de fuzzySet vazia. 
				linguisticVariable = new LinguisticVariableItem();
				linguisticVariable.setLinguisticVariableName(systemInputItem.getItemTotalAmountName());
				linguisticVariable.setMaxDomainValue(systemInputItem.getItemTotalAmountValue());
				linguisticVariable.setDomainType(systemInputItem.getItemTotalAmountName().substring(systemInputItem.getItemTotalAmountName().indexOf("_"), systemInputItem.getItemTotalAmountName().length()));
				//O Domaintype a princpio esta sendo atribuido os tipos "QTE" ou "VAL_TOT". 
				
				linguisticVariable.setLinguisticTerms(DataBaseGeneratorUtils.getInputTerms());
				
				List<FuzzySet> fuzzySetItens = new ArrayList<FuzzySet>();
				
				linguisticVariable.setFuzzySetItens(fuzzySetItens);
				
				this.linguisticVariableItens.add(linguisticVariable);
			}else{
				//Atualiza o valor maximo da variável linguistica.
				if (systemInputItem.getItemTotalAmountValue() > (linguisticVariable.getMaxDomainValue() / DOMAIN_VALUE_MULTIPLIFIER)) {
					linguisticVariable.setMaxDomainValue(systemInputItem.getItemTotalAmountValue());
				}
			}
			//Verifica se ja existe uma variável linguistica com mesmo nome do segundo DataTemplateItem dentro systemInputItem.
			linguisticVariable = containsLinguisticVariable(systemInputItem.getItemUnitaryName());
			if (linguisticVariable == null) {
				//Caso não exista essa variavel linguistica, cria uma nova.
				//A variavel linguistica é criada com: nome, valor maximo que assume, lista de termos e a lista de fuzzySet vazia.
				linguisticVariable = new LinguisticVariableItem();
				linguisticVariable.setLinguisticVariableName(systemInputItem.getItemUnitaryName());
				linguisticVariable.setMaxDomainValue(systemInputItem.getItemUnitaryValue());
				linguisticVariable.setDomainType(systemInputItem.getItemUnitaryName().substring(systemInputItem.getItemUnitaryName().indexOf("_"), systemInputItem.getItemUnitaryName().length()));
				//O Domaintype a princpio esta sendo atribuido os tipos "QTE" ou "VAL_TOT". 
				
				linguisticVariable.setLinguisticTerms(DataBaseGeneratorUtils.getInputTerms());
				
				List<FuzzySet> fuzzySetItens = new ArrayList<FuzzySet>();
				
				linguisticVariable.setFuzzySetItens(fuzzySetItens);
				
				this.linguisticVariableItens.add(linguisticVariable);
			}else{
				//Atualiza o valor maximo da variável linguistica.
				if (systemInputItem.getItemUnitaryValue() > (linguisticVariable.getMaxDomainValue() / DOMAIN_VALUE_MULTIPLIFIER)) {
					linguisticVariable.setMaxDomainValue(systemInputItem.getItemUnitaryValue());
				}
			}
		}
		
		return this.linguisticVariableItens;
	}

	//Método que verifica se existe um a varíavel linguistica com o mesmo nome passado como parametro.
	//Caso exista retorna a variável linguística.
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

	public List<SystemInputItem> getInputSystemItens()
	{
		return inputSystemItens;
	}

	public void setInputSystemItens(List<SystemInputItem> inputSystemItens)
	{
		this.inputSystemItens = inputSystemItens;
	}

}
