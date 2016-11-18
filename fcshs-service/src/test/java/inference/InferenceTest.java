package inference;

import org.junit.Before;
import org.junit.Test;

import com.poli.fcshs.fuzzification.Fuzzifier;
import com.poli.fcshs.generator.KnowledgeBaseGenerator;
import com.poli.fcshs.inference.Inference;
import com.poli.fcshs.model.LinguisticVariableItem;

public class InferenceTest
{

	private Fuzzifier fuzzifier;
	private Inference inference;
	private KnowledgeBaseGenerator knowledgeBaseGenerator;
	@Before
	public void initialize()
	{
//		this.fuzzifier = new Fuzzifier("HAM");
		this.inference = new Inference();
		this.knowledgeBaseGenerator = new KnowledgeBaseGenerator("2005");
	}

	@Test
	public void inference()
	{
		System.out.println("oi");
//		LinguisticVariableItem ola = inference.inference(knowledgeBaseGenerator.generateSystemLinguisticVariables("2005"));
//		System.out.println(ola);
		
		//		assertTrue(defuzziResult != null);
		
	}

}
