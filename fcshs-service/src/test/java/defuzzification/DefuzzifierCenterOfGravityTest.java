package defuzzification;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.poli.fcshs.defuzzification.methods.discrete.DefuzzifierCenterOfGravity;
import com.poli.fcshs.model.LinguisticVariableItem;
import com.poli.fcshs.model.SystemOutputItem;

public class DefuzzifierCenterOfGravityTest
{

	private DefuzzifierCenterOfGravity defuzziCoG = null;
	private LinguisticVariableItem inferenceOutput = null;

	@Before
	public void initialize()
	{
		this.inferenceOutput = DefuzzifierTestUtils.getLinguisticVariableMock();
		this.defuzziCoG = new DefuzzifierCenterOfGravity(this.inferenceOutput);
	}

	@Test
	public void defuzziffy()
	{
		SystemOutputItem defuzziResult = this.defuzziCoG.defuzzify();
		assertTrue(defuzziResult != null);
		System.out.println(defuzziResult);
	}

}
