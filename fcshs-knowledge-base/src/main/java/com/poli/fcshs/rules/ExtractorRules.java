package com.poli.fcshs.rules;

import java.util.ArrayList;
import java.util.List;

import com.poli.fcshs.config.FcshsPropertiesLoader;
import com.poli.fcshs.config.FcshsSetupConstants;
import com.poli.fcshs.model.SystemRules;

public class ExtractorRules
{

	public ExtractorRules()
	{
	}

	/**
	 * Extract all the rules in the properties archive
	 * 
	 * @return List<SystemRules> a list of all rules represented with operators
	 *         and operands
	 */
	public List<SystemRules> extractRules()
	{
		String[] ruleLines = FcshsPropertiesLoader.getInstance().getPropertyByName(FcshsSetupConstants.DATA_RULES)
				.split(";");
		List<SystemRules> rules = new ArrayList<SystemRules>();

		for (String ruleLine : ruleLines)
		{

			ruleLine = ruleLine.replaceAll("\\s+", " ");

			String[] parts = ruleLine.split(" ");
			SystemRules rule = new SystemRules();
			;
			int count = 1;

			while (parts[count] != "ENTAO")
			{

				rule.getOperands().add(parts[count] + "," + parts[count + 1]);
				count += 2;

				if (parts[count].equalsIgnoreCase("e") || parts[count].equalsIgnoreCase("ou"))
				{
					rule.getOperators().add(parts[count]);
					count++;

				}
				else if (parts[count].equalsIgnoreCase("entao"))
				{
					count++;
					break;
				}

			}

			rule.setOutput(parts[count] + "," + parts[count + 1]);

			rules.add(rule);

		}

		return rules;

	}

}
