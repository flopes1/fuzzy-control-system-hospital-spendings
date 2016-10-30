package model;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.poli.fcshs.config.FcshsPropertiesLoader;
import com.poli.fcshs.config.FcshsSetupConstants;
import com.poli.fcshs.data.repository.impl.DataTemplateRepository;
import com.poli.fcshs.model.impl.DataFile;

public class DataFileTest
{

	private DataFile instance;
	private DataTemplateRepository dataTemplateRepository ;
	private File outputDirectory;

	@Before
	public void Initialize()
	{
		this.instance = new DataFile();
		dataTemplateRepository = new DataTemplateRepository();
		outputDirectory = new File(dataTemplateRepository.getDefaultDirectory().getAbsolutePath());
	}

	@Test
	public void listInitialize()
	{
	try {
			
		
		File[] listOutputFiles = outputDirectory.listFiles();
			
			for (File fileOutput : listOutputFiles)
			{
				instance.listInitialize(fileOutput);
				
//				System.out.println(instance);
//				System.out.println("inicializou");
				
				assertEquals(true, instance != null);
		
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		String property = this.instance.getPropertyByName(FcshsSetupConstants.DATA_TEMPLATE_DIRECTORY_CSV);
//		assertEquals(true, property != null && !property.isEmpty());
//		property = this.instance.getPropertyByName(FcshsSetupConstants.DATA_TEMPLATE_DIRECTORY_XLS);
//		assertEquals(true, property != null && !property.isEmpty());
	}

}