package generator;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.poli.fcshs.generator.util.DataBaseGeneratorUtils;
import com.poli.fcshs.model.DataTemplateItem;

public class DataBaseGeneratorUtilsTest
{

	DataTemplateItem itemTotalAmount;
	DataTemplateItem itemTotalValue;

	@Before
	public void Initialize()
	{
		itemTotalAmount = new DataTemplateItem();
		itemTotalValue = new DataTemplateItem();
	}

	@Test
	public void calculateValueUnit()
	{
		// rules = extractorRules.extractRules();
		// assertEquals(true, extractorRules != null);
		itemTotalAmount.setIndicatorName("Amount: ");
		itemTotalAmount.setIndicatorValue(10);
		itemTotalValue.setIndicatorName("Value: ");
		itemTotalValue.setIndicatorValue(200);
		System.out.println(DataBaseGeneratorUtils.calculateValueUnit(itemTotalAmount, itemTotalValue));
		assertEquals(true, DataBaseGeneratorUtils.calculateValueUnit(itemTotalAmount, itemTotalValue) == 20);
	}

	@Test
	public void getInputTerms()
	{
		// rules = extractorRules.extractRules();
		// assertEquals(true, extractorRules != null);
		System.out.println("InputTerms:");
		List<String> inputTerms = DataBaseGeneratorUtils.getInputTerms();
		for (String string : inputTerms)
		{
			System.out.println(string);

		}
		assertEquals(true, inputTerms.size() == 3);
	}

	@Test
	public void getOutputTerms()
	{
		// rules = extractorRules.extractRules();
		// assertEquals(true, extractorRules != null);
		System.out.println("OutputTerms:");
		List<String> outputTerms = DataBaseGeneratorUtils.getOutputTerms();
		for (String string : outputTerms)
		{
			System.out.println(string);
		}
		assertEquals(true, outputTerms.size() == 5);
	}

}