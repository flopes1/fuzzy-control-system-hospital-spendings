package com.poli.fcshs.data.repository;

import java.io.File;
import java.util.List;

public interface IDataTemplateRepository
{
	
	File getDataTemplateByYear(String year);
	
	List<File> getAllData();
	
	boolean insertDataTemplate(String directory);
	
	boolean removeDataTemplateByYear(String year);

}
