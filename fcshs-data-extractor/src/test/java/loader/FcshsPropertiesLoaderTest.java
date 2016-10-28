package loader;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.poli.fcshs.config.FcshsPropertiesLoader;
import com.poli.fcshs.config.FcshsSetupConstants;

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
		String property = this.instance.getPropertyByName(FcshsSetupConstants.DATA_TEMPLATE_DIRECTORY_CSV);
		assertEquals(true, property != null && !property.isEmpty());
		property = this.instance.getPropertyByName(FcshsSetupConstants.DATA_TEMPLATE_DIRECTORY_XLS);
		assertEquals(true, property != null && !property.isEmpty());
	}

}
