package handler;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.poli.fcshs.data.repository.impl.DataTemplateRepository;
import com.poli.fcshs.model.impl.DataFile;

import data.handler.impl.ObjectHandler;

public class ObjectHandlerTest
{

	private ObjectHandler objectHandler;
	private DataTemplateRepository dataTemplateRepository ;

	@Before
	public void Initialize()
	{
		this.objectHandler = new ObjectHandler();
		dataTemplateRepository = new DataTemplateRepository();
	}

	@Test
	public void listInitialize()
	{
	try {
			
		
		List<File> listOutputFiles = dataTemplateRepository.getAllData();
			
			for (File fileOutput : listOutputFiles)
			{
				DataFile dataFile = this.objectHandler.listInitialize(fileOutput);
				
//				System.out.println(dataFile);
//				System.out.println("inicializou");
				
				assertEquals(true, dataFile != null);
		
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

}