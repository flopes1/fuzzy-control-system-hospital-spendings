package loader;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.poli.fcshs.config.constants.FcshsSetupConstants;
import com.poli.fcshs.config.loader.FcshsPropertiesLoader;

public class FcshsPropertiesLoaderTest
{

	private FcshsPropertiesLoader instance;

	@Before
	public void Initialize()
	{
		this.instance = FcshsPropertiesLoader.getInstance();
	}

	@Test
	public void getPropertyByName()
	{
		String property = this.instance.getPropertyByName(FcshsSetupConstants.DATA_TEMPLATE_DIRECTORY);
		assertEquals(true, property != null && !property.isEmpty());
	}

}