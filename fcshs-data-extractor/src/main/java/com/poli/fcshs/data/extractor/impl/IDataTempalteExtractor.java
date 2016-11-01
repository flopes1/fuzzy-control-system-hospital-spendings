package com.poli.fcshs.data.extractor.impl;

import java.util.List;

import com.poli.fcshs.model.DataTemplateFile;

public interface IDataTempalteExtractor
{
	
	public DataTemplateFile extractDataByName(String fileName);
	
	public List<DataTemplateFile> extractAllData();

}
