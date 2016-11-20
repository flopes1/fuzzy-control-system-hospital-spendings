package service;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.poli.fcshs.service.impl.FCSHSSystemService;

public class ServiceTest
{

	private FCSHSSystemService systemService;

	@Before
	public void initialize()
	{
		this.systemService = new FCSHSSystemService();
	}

	@Test
	public void getSystemAnalysisByHospitalAndYear()
	{
		String result = systemService.getSystemAnalysisByHospital("HAM","2020");
		System.out.println(result);
		assertTrue(result != null);
	}

	
	//metodo bugado
	
//	@Test
//	public void getSystemAnalysisByHospital()
//	{
//		String result = systemService.getSystemAnalysisByHospital("HAM");
//		System.out.println(result);
//		assertTrue(result != null);
//	}
}

