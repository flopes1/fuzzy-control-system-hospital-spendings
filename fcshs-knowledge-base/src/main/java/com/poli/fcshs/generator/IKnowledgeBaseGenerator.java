package com.poli.fcshs.generator;

import java.util.List;

import com.poli.fcshs.model.LinguisticVariableItem;
import com.poli.fcshs.model.SystemInputItem;

/**
 * @author Filipe Lopes created on 01/11/2016
 * 
 */

public interface IKnowledgeBaseGenerator
{
	
	List<SystemInputItem> generateSystemInputItens(String hospitalName);
	
	List<SystemInputItem> generateSystemInputItensByYear(String hospitalName, String year);
	
	List<LinguisticVariableItem> generateSystemLinguisticVariables();
	
}
