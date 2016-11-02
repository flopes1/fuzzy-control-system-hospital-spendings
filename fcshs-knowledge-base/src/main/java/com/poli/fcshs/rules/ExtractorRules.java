package com.poli.fcshs.rules;

import java.util.ArrayList;
import java.util.List;

import com.poli.fcshs.config.FcshsConstants;
import com.poli.fcshs.config.FcshsPropertiesLoader;
import com.poli.fcshs.config.FcshsRulesConstants;
import com.poli.fcshs.model.RuleModel;

public class ExtractorRules
{
	
	
	public ExtractorRules()
	{
	}

	
	public List<RuleModel> extractRules()
	{
		String[] ruleLines = FcshsPropertiesLoader.getInstance().getPropertyByName(FcshsRulesConstants.DATA_RULES).split(";");
		List<RuleModel> rules = new ArrayList<RuleModel>();
		
		
		for (String ruleLine : ruleLines)
		{
			String[] parts = ruleLine.split(" ");
			RuleModel rule = new RuleModel();;
			int count=1;
		
			
			while(parts[count] != "ENTAO")
			{
				
				rule.getOperands().add(parts[count]+ "," + parts[count+1]);
				count+=2;
				
				if (parts[count].equalsIgnoreCase("e") || parts[count].equalsIgnoreCase("ou"))
				{
					rule.getOperators().add(parts[count]);
					count++;
					
				}else if(parts[count].equalsIgnoreCase("entao")){
					count++;
					break;
				}
				
			}
			
			rule.setOutput(parts[count] + "," + parts[count+1]);
			
			rules.add(rule);
			
		}
		
		return rules;
		
	}
	
	
}
