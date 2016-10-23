package com.poli.fcshs.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @author Filipe Lopes create on 22/10/2016
 * 
 */

public class FcshsPropertiesLoader
{

	private Properties fcshsProperties;
	private static FcshsPropertiesLoader instance;

	private FcshsPropertiesLoader()
	{
		this.loadFcshsProperties();
	}

	public static FcshsPropertiesLoader getInstance()
	{
		if (instance == null)
		{
			instance = new FcshsPropertiesLoader();
		}
		return instance;
	}

	public Properties loadFcshsProperties()
	{
		try
		{
			InputStream globalPropertiesStream = this.getClass().getClassLoader()
					.getResourceAsStream("fcshs.properties");
			fcshsProperties = new Properties();
			fcshsProperties.load(globalPropertiesStream);

			return fcshsProperties;
		}
		catch (IOException e)
		{
			throw new RuntimeException("Failed to load fcshs.properties file", e);
		}
	}

	public String getPropertyByName(String name)
	{
		return this.fcshsProperties.getProperty(name);
	}

}
