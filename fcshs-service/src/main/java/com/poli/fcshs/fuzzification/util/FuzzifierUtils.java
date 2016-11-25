package com.poli.fcshs.fuzzification.util;

public class FuzzifierUtils
{

	public static double normalizeFuzzySetValues(double value,String term, double minValue, double maxValue){
		double valueNormalized = 0;
		double leftLimit= 0;
		double rightLimit= 0;
		
//		maxValue/= 1.3;
		
		
		// foram definidas as seguintes regras para o sistema:  
		// faixa de valores baixos entre 0% ~ 60% (em relação ao valor maximo)
		// faixa de valores medios entre 40% ~ 60% (em relação ao valor maximo)
		// faixa de valores altos entre 40% ~ 100% (em relação ao valor maximo)

		
		if (term.equalsIgnoreCase("baixo")) {
			leftLimit = minValue;
			rightLimit = (0.6 * (maxValue - minValue) + minValue);
			if (value < leftLimit || value >  rightLimit) {
				valueNormalized = 0;
			}
			else {
				valueNormalized = 1 - ( (value - leftLimit) / (rightLimit - leftLimit) );
				
			}
			
		}
		if (term.equalsIgnoreCase("medio")) {
			leftLimit = (0.4 * (maxValue - minValue) + minValue);
			rightLimit = (0.6 * (maxValue - minValue) + minValue);
			
			if (value < leftLimit || value >  rightLimit) {
				valueNormalized = 0;
			}else{
				if(value/maxValue > 0.5){
					valueNormalized = 1 - ( (value - leftLimit) / (rightLimit - leftLimit) );
				}else {
					valueNormalized = ( (value - leftLimit) / (rightLimit - leftLimit) );
				}
			}
		}
		if (term.equalsIgnoreCase("alto")) {
			leftLimit = (0.4 * (maxValue - minValue) + minValue);
			rightLimit = maxValue;
			
			if (value < leftLimit || value >  rightLimit) {
				valueNormalized = 0;
			}
			else {
				valueNormalized = ( (value - leftLimit) / (rightLimit - leftLimit) );
			}
		}
		
		return valueNormalized;

	}
	
}
