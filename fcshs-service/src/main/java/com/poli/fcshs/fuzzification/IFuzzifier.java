package com.poli.fcshs.fuzzification;

import com.poli.fcshs.model.FuzzySet;

public interface IFuzzifier {

	double normalizeFuzzySetValues(double value,String term, double maxValue);
	FuzzySet generateFuzzySet(double indicatorValue, String term, double maxValue);
}
