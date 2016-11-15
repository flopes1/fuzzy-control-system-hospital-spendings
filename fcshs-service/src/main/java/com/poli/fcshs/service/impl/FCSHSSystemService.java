package com.poli.fcshs.service.impl;

import com.poli.fcshs.generator.KnowledgeBaseGenerator;
import com.poli.fcshs.service.IFCSHSSystemService;

/**
 * @author Filipe Lopes created on 13/11/2016
 * 
 */

public class FCSHSSystemService implements IFCSHSSystemService
{

	private KnowledgeBaseGenerator systemKnowledgeBase;

	public FCSHSSystemService()
	{
		this.systemKnowledgeBase = new KnowledgeBaseGenerator();
	}

	public String getSystemAnalysisByHospital(String hospitalName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String getSystemAnalysisByYear(String year)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String getSystemAnalysisAllYears()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String getSystemAnalysisAllHospitalYears()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
