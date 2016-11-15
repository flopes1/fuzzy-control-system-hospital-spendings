package com.poli.fcshs.defuzzification;

import com.poli.fcshs.model.SystemOutputItem;

/**
 * @author Filipe Lopes created on 13/11/2016
 * 
 */

public interface IDefuzzifier
{

	SystemOutputItem defuzzify();

	String getInformation();

}
