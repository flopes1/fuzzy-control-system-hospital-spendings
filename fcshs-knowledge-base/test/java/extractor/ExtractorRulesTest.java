package extractor;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.poli.fcshs.model.SystemRules;
import com.poli.fcshs.rules.ExtractorRules;

public class ExtractorRulesTest
{

	ExtractorRules extractorRules;
	List<SystemRules> rules;

	@Before
	public void Initialize()
	{
		extractorRules = new ExtractorRules();
		rules = new ArrayList<SystemRules>();
	}

	@Test
	public void extractRulesTest()
	{
		rules = extractorRules.extractRules();
		assertEquals(true, extractorRules != null);
	}

}