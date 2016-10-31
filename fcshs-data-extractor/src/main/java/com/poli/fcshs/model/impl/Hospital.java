package com.poli.fcshs.model.impl;

import java.util.List;

public class Hospital {

	private String name;
	
	private List<Month> months;

	
	public  Hospital(){
		
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public List<Month> getMonths() {
		return months;
	}

	public void setMonths(List<Month> meses) {
		this.months = meses;
	}
	
}
