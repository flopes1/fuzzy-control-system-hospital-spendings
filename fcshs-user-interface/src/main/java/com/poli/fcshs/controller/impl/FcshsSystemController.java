package com.poli.fcshs.controller.impl;

import com.poli.fcshs.controller.IFcshsSystemController;
import com.poli.fcshs.service.IFCSHSSystemService;
import com.poli.fcshs.service.impl.FCSHSSystemService;

/**
 * This class is the controller of system and it connects with the service of
 * system
 * 
 * @author Filipe created on 20/11/2016
 *
 */

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
