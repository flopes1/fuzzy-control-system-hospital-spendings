package com.poli.fcshs.data.extractor;

import java.util.List;

import com.poli.fcshs.model.DataTemplateFile;

public interface IDataTempalteExtractor
{
	
	public DataTemplateFile extractDataByName(String fileName);
	
	public List<DataTemplateFile> extractAllData();

}
