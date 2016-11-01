package extractor;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.poli.fcshs.data.extractor.DataTemplateExtractor;
import com.poli.fcshs.data.repository.impl.DataTemplateRepository;
import com.poli.fcshs.data.repository.impl.SourceTemplateRepository;
import com.poli.fcshs.model.DataTemplateFile;

public class DataTemplateExtractorTest
{

	private DataTemplateExtractor dataExtractor;
	private SourceTemplateRepository sourceRepository;

	@Before
	public void Initialize()
	{
		this.dataExtractor = new DataTemplateExtractor();
		this.sourceRepository = new SourceTemplateRepository();
	}

	// public DataTemplateFile extractDataByName(String fileName);

	// public List<DataTemplateFile> extractAllData();

	@Test
	public void extractDataByNameTest()
	{
		DataTemplateFile extractedFile = this.dataExtractor.extractDataByName("PlanilhaNormalizada");
		assertEquals(true, extractedFile != null);
	}
	
	@Test
	public void extractAllData()
	{
		
	}

}