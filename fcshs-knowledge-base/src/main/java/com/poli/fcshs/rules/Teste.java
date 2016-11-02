package com.poli.fcshs.rules;

import java.util.List;

import com.poli.fcshs.model.RuleModel;

public class Teste
{

	public static void main(String[] args)
	{

		ExtractorRules handler = new ExtractorRules();
		
		List<RuleModel> rules =  handler.extractRules();
		
		System.out.println(rules);

	}

}
