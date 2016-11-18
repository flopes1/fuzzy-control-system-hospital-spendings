package defuzzification;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.poli.fcshs.fuzzification.Fuzzifier;
import com.poli.fcshs.model.LinguisticVariableItem;

public class FuzzifierTest {

	private Fuzzifier fuzzification = null;

	@Before
	public void initialize()
	{
		this.fuzzification = new Fuzzifier("HAM", "2020");
	}

	@Test
	public void defuzziffy()
	{
		List<LinguisticVariableItem> linguisticVariableItens = this.fuzzification.getListlinguisticVariablewWithValues();
		assertTrue(linguisticVariableItens != null);
		System.out.println(linguisticVariableItens);
	}

}
