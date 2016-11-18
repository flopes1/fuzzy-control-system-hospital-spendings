package com.poli.fcshs.service;

/**
 * @author Filipe Lopes created on 13/11/2016
 * 
 */

public interface IFCSHSSystemService
{

	String getSystemAnalysisByHospital(String hospitalName, String year);

	String getSystemAnalysisByHospital(String hospitalName);

}
