package com.poli.fcshs.model;
import java.io.File;

public interface IExcelDataHandler {

	File getCsvFile(String name);
	
	File xlsxConversorToCsv(File name);
}
