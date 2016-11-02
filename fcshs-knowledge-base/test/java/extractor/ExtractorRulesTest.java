package extractor;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.poli.fcshs.model.RuleModel;
import com.poli.fcshs.rules.ExtractorRules;

public class ExtractorRulesTest
{

	ExtractorRules extractorRules;
	List<RuleModel> rules;
	

	@Before
	public void Initialize()
	{
		extractorRules =  new ExtractorRules();
		rules = new ArrayList<RuleModel>();
	}


	@Test
	public void extractRulesTest()
	{
		rules = extractorRules.extractRules();
		assertEquals(true, extractorRules != null);
	}
	
	

}