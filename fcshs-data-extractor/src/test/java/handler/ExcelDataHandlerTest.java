package handler;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.poli.fcshs.config.FcshsPropertiesLoader;
import com.poli.fcshs.config.FcshsSetupConstants;
import com.poli.fcshs.data.repository.impl.DataTemplateRepository;
import com.poli.fcshs.data.repository.impl.SourceTemplateRepository;

import data.handler.impl.ExcelDataHandler;

public class ExcelDataHandlerTest
{
	private ExcelDataHandler instance;
	private SourceTemplateRepository sourceTemplateRepository ;
	private DataTemplateRepository dataTemplateRepository ;
	private File inputDirectory;

	@Before
	public void Initialize()
	{
		this.instance =  new  ExcelDataHandler();
		sourceTemplateRepository = new SourceTemplateRepository();
		dataTemplateRepository = new DataTemplateRepository();
		inputDirectory = new File(sourceTemplateRepository.getDefaultDirectory().getAbsolutePath());
	}

	@Test
	public void xlsxConversorToCsv()
	{
		
		
		File[] listInputFiles = inputDirectory.listFiles();
		
		for (File fileInputXlxsx : listInputFiles)
		{
			File testFileCsv = this.instance.xlsxConversorToCsv(fileInputXlxsx);
			
			String name =  testFileCsv.getName().replace(".", "#");
			String [] nameFile = name.split("#");
			
			File inputFileTest = new File(dataTemplateRepository.getDefaultDirectory().getAbsolutePath() + File.separator+ nameFile[0]+ ".csv");
//			System.out.println(testFileCsv.getAbsolutePath());
//			System.out.println(inputFileTest.getAbsolutePath());
			assertEquals(true, testFileCsv != null && testFileCsv.getAbsolutePath().equals(inputFileTest.getAbsolutePath()));
		}

	}

}
