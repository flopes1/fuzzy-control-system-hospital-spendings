package com.poli.fcshs;

import com.poli.fcshs.controller.impl.FcshsSystemController;

/**
 * This class represents the facade of the controller of the system
 * 
 * @author Filipe Lopes created on 20/11/2016
 *
 */

public class FcshsSystemFacade
{

	private FcshsSystemController systemController;
	private static FcshsSystemFacade instance;

	private FcshsSystemFacade()
	{
		this.systemController = new FcshsSystemController();
	}

	public static FcshsSystemFacade getInstance()
	{
		if (instance == null)
		{
			instance = new FcshsSystemFacade();
		}
		return instance;
	}

	public FcshsSystemController getSystemController()
	{
		return this.systemController;
	}

}
