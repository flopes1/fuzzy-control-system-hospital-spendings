package inference;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.poli.fcshs.fuzzification.Fuzzifier;
import com.poli.fcshs.inference.Inference;
import com.poli.fcshs.model.LinguisticVariableItem;

public class InferenceTest {

	private Fuzzifier fuzzification = null;
	private Inference inference = null;

	@Before
	public void initialize()
	{
		this.fuzzification = new Fuzzifier("HAM", "2020");
		this.inference =  new Inference();
	}

	@Test
	public void defuzziffy()
	{
		List<LinguisticVariableItem> linguisticVariableItens = this.fuzzification.getListlinguisticVariablewWithValues();
		LinguisticVariableItem lisguisticVariableItem = inference.inference(linguisticVariableItens);
		assertTrue(lisguisticVariableItem != null);
		System.out.println(lisguisticVariableItem);
	}

}
