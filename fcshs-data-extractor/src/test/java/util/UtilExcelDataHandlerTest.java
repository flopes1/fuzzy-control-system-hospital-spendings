package util;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.poli.fcshs.data.repository.impl.DataTemplateRepository;
import com.poli.fcshs.data.repository.impl.SourceTemplateRepository;
import com.poli.fcshs.util.DataTemplateUtils;

public class UtilExcelDataHandlerTest
{
	private DataTemplateUtils utilExcelDataHandler;
	private SourceTemplateRepository sourceTemplateRepository ;
	private DataTemplateRepository dataTemplateRepository ;

	@Before
	public void Initialize()
	{
		this.utilExcelDataHandler =  new  DataTemplateUtils();
		sourceTemplateRepository = new SourceTemplateRepository();
		dataTemplateRepository = new DataTemplateRepository();
	}

	@Test
	public void xlsxConversorToCsv()
	{
		
		
		List<File> listInputFiles = sourceTemplateRepository.getAllData();
		
		for (File fileInputXlxsx : listInputFiles)
		{
			File testFileCsv = this.utilExcelDataHandler.xlsxConversorToCsv(fileInputXlxsx);
			File inputFileTest = new File(dataTemplateRepository.getDefaultDirectory().getAbsolutePath() + File.separator+ testFileCsv.getName().substring(0, testFileCsv.getName().lastIndexOf("."))+ ".csv");
//			System.out.println(testFileCsv.getAbsolutePath());
//			System.out.println(inputFileTest.getAbsolutePath());
			assertEquals(true, testFileCsv != null && testFileCsv.getAbsolutePath().equals(inputFileTest.getAbsolutePath()));
		}

	}

}
