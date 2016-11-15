package com.poli.fcshs.service;

/**
 * @author Filipe Lopes created on 13/11/2016
 * 
 */

public interface IFCSHSSystemService
{

	String getSystemAnalysisByHospital(String hospitalName);

	String getSystemAnalysisByYear(String year);

	String getSystemAnalysisAllYears();

	String getSystemAnalysisAllHospitalYears();

}
