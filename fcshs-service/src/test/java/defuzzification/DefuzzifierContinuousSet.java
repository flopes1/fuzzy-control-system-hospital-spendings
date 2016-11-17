package defuzzification;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.poli.fcshs.defuzzification.methods.continuous.DefuzzifierMeanMax;
import com.poli.fcshs.model.LinguisticVariableItem;
import com.poli.fcshs.model.SystemOutputItem;

public class DefuzzifierContinuousSet
{
	
	private DefuzzifierMeanMax defuzziMeOM = null;
	private LinguisticVariableItem inferenceOutput = null;

	@Before
	public void initialize()
	{
		this.inferenceOutput = DefuzzifierTestUtils.getLinguisticVariableMock();
		this.defuzziMeOM = new DefuzzifierMeanMax(this.inferenceOutput);
	}

	@Test
	public void defuzziffy()
	{
		SystemOutputItem defuzziResult = this.defuzziMeOM.defuzzify();
		assertTrue(defuzziResult != null);
		System.out.println(defuzziResult);
	}

}
