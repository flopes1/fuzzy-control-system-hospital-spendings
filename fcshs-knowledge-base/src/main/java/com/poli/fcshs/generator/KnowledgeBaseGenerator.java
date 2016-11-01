package com.poli.fcshs.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.poli.fcshs.data.extractor.DataTemplateExtractor;
import com.poli.fcshs.model.LinguisticVariableItem;
import com.poli.fcshs.model.SystemInputItem;

/**
 * @author Filipe Lopes created on 01/11/2016
 * 
 */

public class KnowledgeBaseGenerator implements IKnowledgeBaseGenerator
{

	private String[] templatesItensNames;
	private DataTemplateExtractor dataTemplateExtractor;
	private List<LinguisticVariableItem> linguisticVariableItens;
	private List<SystemInputItem> inputSystemItens;

	// o argumento do construtor é o/os nomes (anos) das planilhas que vou
	// utilizar
	// se quiser, pode remover o argumento se for mais inteligente utilizar
	// todas as
	// planilhas csv que estao cadastradas.
	public KnowledgeBaseGenerator(String... templateItensName)
	{
		this.templatesItensNames = templateItensName;
		this.dataTemplateExtractor = new DataTemplateExtractor();
		this.linguisticVariableItens = new ArrayList<LinguisticVariableItem>();
		this.inputSystemItens = new ArrayList<SystemInputItem>();
	}

	public List<SystemInputItem> generateSystemInputItens()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public List<LinguisticVariableItem> generateSystemLinguisticVariables()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public HashMap<Integer, Double> generateFuzzySet()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void normalizeFuzzySetValues()
	{
		// TODO Auto-generated method stub

	}

}
