package repository;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import com.poli.fcshs.data.repository.impl.SourceTemplateRepository;

public class SourceTemplateRepositoryTest
{

	private SourceTemplateRepository dataTemplateRepository;

	@Before
	public void initialize()
	{
		this.dataTemplateRepository = new SourceTemplateRepository();
		this.initializeDataBase();
	}

	@Test
	public void getDataTemplateByYear()
	{
		File file = this.dataTemplateRepository.getDataTemplateByYear("2005");
		assertEquals(true, file != null);
		assertEquals("2005.xls", file.getName());
		file = this.dataTemplateRepository.getDataTemplateByYear("2006");
		assertEquals(true, file != null);
		assertEquals("2006.xlsx", file.getName());
	}

	@Test
	public void getAllData()
	{
		List<File> files = this.dataTemplateRepository.getAllData();
		assertEquals(true, files != null);
		assertEquals(2, files.size());
	}

	private void initializeDataBase()
	{
		try
		{
			for (int i = 2005; i < 2007; i++)
			{
				URL directory = this.getClass().getClassLoader().getResource(i + ".xls");
				File file = null;

				if (directory != null)
				{
					file = new File(directory.getFile());
				}
				else
				{
					directory = this.getClass().getClassLoader().getResource(i + ".xlsx");
					file = new File(directory.getFile());
				}
				FileUtils.copyFileToDirectory(file, this.dataTemplateRepository.getDefaultDirectory());
			}
		}
		catch (IOException e)
		{

		}
	}

}
