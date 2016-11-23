package com.poli.fcshs.data.repository.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import com.poli.fcshs.config.FcshsConstants;
import com.poli.fcshs.config.FcshsPropertiesLoader;
import com.poli.fcshs.config.FcshsSetupConstants;
import com.poli.fcshs.data.repository.IDataTemplateRepository;

public class DataTemplateRepository implements IDataTemplateRepository
{

	private final String SOURCE_DIR;
	private File rootDirectory;

	public DataTemplateRepository()
	{
		this.SOURCE_DIR = FcshsPropertiesLoader.getInstance()
				.getPropertyByName(FcshsSetupConstants.DATA_TEMPLATE_DIRECTORY_CSV);

		this.initializeDirectory();
	}

	public File getDefaultDirectory()
	{
		return this.rootDirectory;
	}

	/**
	 * Find and returns a csv file with the name passed in param, returns null
	 * if the file not found
	 * 
	 * @return File from the csv archive
	 */
	public File getDataTemplateByYear(String year)
	{
		File dataTemplateFile = null;

		List<File> allFile = (List<File>) FileUtils.listFiles(this.rootDirectory,
				new WildcardFileFilter("*" + FcshsConstants.OUTPUT_TEMPLATE_FORMAT), null);

		if (allFile == null || allFile.isEmpty())
		{
			throw new RuntimeException("The root directory is empty");
		}

		for (File file : allFile)
		{
			if (year.equals(file.getName().substring(0, file.getName().lastIndexOf("."))))
			{
				dataTemplateFile = file;
				break;
			}
		}

		return dataTemplateFile;
	}

	/**
	 * List all csv file in the root directory set in the properties archive,
	 * returns a empty list if there is no file
	 * 
	 * @return List<File> from the all csv file
	 */
	public List<File> getAllData()
	{
		List<File> allFiles = null;

		allFiles = (List<File>) FileUtils.listFiles(this.rootDirectory,
				new WildcardFileFilter("*" + FcshsConstants.OUTPUT_TEMPLATE_FORMAT), null);

		return allFiles;
	}

	/**
	 * Insert a csv file with name passed in parameter, in the directory set in
	 * the properties
	 * 
	 * @param String
	 *            with the name of the csv file
	 * @return boolean represents if the operation was successful
	 */
	public boolean insertDataTemplate(String newDataDirectory)
	{
		boolean hasInserted = false;

		if (newDataDirectory == null)
		{
			throw new IllegalArgumentException("The new data directory is Invalid");
		}

		try
		{
			File toInsert = FileUtils.getFile(newDataDirectory);
			FileUtils.copyFileToDirectory(toInsert, this.rootDirectory);
			hasInserted = true;
		}
		catch (IOException e)
		{
			throw new RuntimeException("The new data cannot be inserted");
		}

		return hasInserted;
	}

	/**
	 * Remove a csv file with name passed in parameter, in the directory set in
	 * the properties
	 * 
	 * @param String
	 *            with the name of the csv file
	 * @return boolean represents if the operation was successful
	 */
	public boolean removeDataTemplateByYear(String year)
	{
		boolean hasRemoved = false;

		File toRemove = this.getDataTemplateByYear(year);

		if (toRemove == null)
		{
			throw new RuntimeException("The file: " + year + FcshsConstants.OUTPUT_TEMPLATE_FORMAT + " do not exist");
		}

		FileUtils.deleteQuietly(toRemove);

		return hasRemoved;
	}

	private void initializeDirectory()
	{

		this.rootDirectory = new File(this.SOURCE_DIR);

		boolean hasInitialized = rootDirectory.exists();

		if (!hasInitialized)
		{
			hasInitialized = rootDirectory.mkdirs();
		}

	}

}
