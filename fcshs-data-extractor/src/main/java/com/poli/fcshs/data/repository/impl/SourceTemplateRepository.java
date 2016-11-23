package com.poli.fcshs.data.repository.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import com.poli.fcshs.config.FcshsConstants;
import com.poli.fcshs.config.FcshsPropertiesLoader;
import com.poli.fcshs.config.FcshsSetupConstants;
import com.poli.fcshs.data.repository.IDataTemplateRepository;

public class SourceTemplateRepository implements IDataTemplateRepository
{

	private final String XLS_DIR;
	private File sourceDirectory;

	public SourceTemplateRepository()
	{
		this.XLS_DIR = FcshsPropertiesLoader.getInstance()
				.getPropertyByName(FcshsSetupConstants.DATA_TEMPLATE_DIRECTORY_XLS);
		this.initializeDirectory();
	}

	public File getDefaultDirectory()
	{
		return this.sourceDirectory;
	}

	/**
	 * Find and return xls file with name passed, returns null if the file is
	 * not found
	 * 
	 * @param xls
	 *            file name
	 * @return File archive from the xls directory
	 */

	public File getDataTemplateByYear(String year)
	{
		File dataTemplateFile = null;

		List<File> allFile = (List<File>) FileUtils.listFiles(this.sourceDirectory,
				new WildcardFileFilter("*" + FcshsConstants.INPUT_TEMPLATE_FORMAT), null);

		if (allFile == null || allFile.size() == 0)
		{
			allFile = (List<File>) FileUtils.listFiles(this.sourceDirectory,
					new WildcardFileFilter("*" + FcshsConstants.INPUT_TEMPLATE_FORMAT_SECOND), null);
		}
		else
		{
			List<File> xlsxFiles = (List<File>) FileUtils.listFiles(this.sourceDirectory,
					new WildcardFileFilter("*" + FcshsConstants.INPUT_TEMPLATE_FORMAT_SECOND), null);
			if (xlsxFiles != null && xlsxFiles.size() > 0)
			{
				allFile.addAll(xlsxFiles);
			}
		}

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
	 * List all xls files in the directory set in the properties
	 * 
	 * @return List<File> list of file of all xls itens
	 */

	public List<File> getAllData()
	{
		List<File> allFiles = null;

		allFiles = (List<File>) FileUtils.listFiles(this.sourceDirectory,
				new WildcardFileFilter("*" + FcshsConstants.INPUT_TEMPLATE_FORMAT), null);

		if (allFiles == null || allFiles.size() == 0)
		{
			allFiles = (List<File>) FileUtils.listFiles(this.sourceDirectory,
					new WildcardFileFilter("*" + FcshsConstants.INPUT_TEMPLATE_FORMAT_SECOND), null);
		}
		else
		{
			List<File> xlsxFiles = (List<File>) FileUtils.listFiles(this.sourceDirectory,
					new WildcardFileFilter("*" + FcshsConstants.INPUT_TEMPLATE_FORMAT_SECOND), null);
			if (xlsxFiles != null && xlsxFiles.size() > 0)
			{
				allFiles.addAll(xlsxFiles);
			}
		}

		return allFiles;
	}

	public boolean insertDataTemplate(String directory)
	{
		// TODO: Esse metodo será implementado apenas quando o sistema possuir
		// interface gráfica
		return false;
	}

	public boolean removeDataTemplateByYear(String year)
	{
		// TODO: Esse metodo será implementado apenas quando o sistema possuir
		// interface gráfica
		return false;
	}

	private void initializeDirectory()
	{

		this.sourceDirectory = new File(this.XLS_DIR);

		boolean hasInitialized = sourceDirectory.exists();

		if (!hasInitialized)
		{
			hasInitialized = sourceDirectory.mkdirs();
		}

	}

}
