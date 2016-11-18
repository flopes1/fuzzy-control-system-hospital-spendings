package defuzzification;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.poli.fcshs.model.FuzzySet;
import com.poli.fcshs.model.LinguisticVariableItem;

public class DefuzzifierTestUtils
{

	public static LinguisticVariableItem getLinguisticVariableMock()
	{
		LinguisticVariableItem linguisticVariableItem = new LinguisticVariableItem();

		linguisticVariableItem.setLinguisticVariableName("Good management Indication");
		linguisticVariableItem.setDomainType("%");
		linguisticVariableItem.setMaxDomainValue(100);
		linguisticVariableItem.setLinguisticTerms(getLinguisticTermsMock());
		linguisticVariableItem.setFuzzySetItens(getFuzzySetOutputMock());

		return linguisticVariableItem;
	}

	private static List<String> getLinguisticTermsMock()
	{
		return Arrays.asList(new String[] { "Low", "Medium", "High" });
	}

	private static List<FuzzySet> getFuzzySetOutputMock()
	{
		FuzzySet outputFuzzySet = new FuzzySet();

		outputFuzzySet.setFuzzySetName("Joined Inputs Fuzzy Set");

		HashMap<Double, Double> pertinenceFunction = new HashMap<Double, Double>();

		for (int i = 0; i <= 100; i += 1)
		{
			pertinenceFunction.put((double) i, getNormalizedValue(i));
		}

		outputFuzzySet.setFuzzySetItens(pertinenceFunction);

		return Arrays.asList(new FuzzySet[] { outputFuzzySet });
	}

	private static Double getNormalizedValue(int i)
	{

		if (i <= 10 || i > 56 && i <= 65 || i > 95)
		{
			return ThreadLocalRandom.current().nextDouble(0.93, 1);
		}
		else if (i > 10 && i <= 17 || i > 44 && i <= 56 || i > 65 && i <= 72 || i > 93 && i <= 95)
		{
			return ThreadLocalRandom.current().nextDouble(0.63, 0.9);
		}
		else if (i > 17 && i <= 25 || i > 36 && i <= 44 || i > 72 && i <= 80 || i > 87 && i <= 93)
		{
			return ThreadLocalRandom.current().nextDouble(0.5, 0.6);
		}
		else if (i > 25 && i <= 36 || i > 80 && i <= 87)
		{
			return ThreadLocalRandom.current().nextDouble(0.2, 0.4);
		}
		return 0d;
	}

}
