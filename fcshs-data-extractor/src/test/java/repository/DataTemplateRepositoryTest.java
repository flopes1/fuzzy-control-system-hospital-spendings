package repository;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import com.poli.fcshs.data.repository.impl.DataTemplateRepository;

public class DataTemplateRepositoryTest
{

	private DataTemplateRepository dataTemplateRepository;

	@Before
	public void initialize()
	{
		this.dataTemplateRepository = new DataTemplateRepository();
		this.initializeDataBase();
	}

	@Test
	public void getDataTemplateByYear()
	{
		File file = this.dataTemplateRepository.getDataTemplateByYear("2015");
		assertEquals(true, file != null);
		assertEquals("2015.csv", file.getName());
	}

	@Test
	public void getAllData()
	{
		List<File> files = this.dataTemplateRepository.getAllData();
		assertEquals(true, files != null);
		assertEquals(7, files.size());
	}

	@Test
	public void insertDataTemplate()
	{
		String dir = this.getClass().getClassLoader().getResource("2016.csv").getFile();
		this.dataTemplateRepository.insertDataTemplate(dir);

		File file = this.dataTemplateRepository.getDataTemplateByYear("2016");
		assertEquals(true, file != null);
		assertEquals("2016.csv", file.getName());
	}

	@Test
	public void removeDataTemplateByYear()
	{
		String dir = this.getClass().getClassLoader().getResource("2017.csv").getFile();
		this.dataTemplateRepository.insertDataTemplate(dir);
		
		this.dataTemplateRepository.removeDataTemplateByYear("2017");
		File file = this.dataTemplateRepository.getDataTemplateByYear("2017");
		assertEquals(true, file == null);
	}

	private void initializeDataBase()
	{
		try
		{
			for (int i = 2010; i < 2016; i++)
			{
				File file = new File(this.getClass().getClassLoader().getResource(i + ".csv").getFile());
				FileUtils.copyFileToDirectory(file, this.dataTemplateRepository.getDefaultDirectory());
			}
		}
		catch (IOException e)
		{

		}
	}

}
