package com.poli.fcshs.controller.impl;

import com.poli.fcshs.controller.IFcshsSystemController;
import com.poli.fcshs.service.IFCSHSSystemService;
import com.poli.fcshs.service.impl.FCSHSSystemService;

public class FcshsSystemController implements IFcshsSystemController
{

	private IFCSHSSystemService systemService;

	public FcshsSystemController()
	{
		this.systemService = new FCSHSSystemService();
	}

	public String getSystemAnalysisByHospital(String hospitalName, String year)
	{
		return this.systemService.getSystemAnalysisByHospital(hospitalName, year);
	}

	public String getSystemAnalysisByHospital(String hospitalName)
	{
		return this.systemService.getSystemAnalysisByHospital(hospitalName);
	}

}
