package com.poli.fcshs.model.impl;

import java.util.ArrayList;
import java.util.List;

public class Hospital {

	private String name;
	
	private List<Mes> meses;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Mes> getMeses() {
		return meses;
	}

	public void setMeses(List<Mes> meses) {
		this.meses = meses;
	}
	
	public void createNewMes(String[] novoObjeto,  String[] indicadores ){
		if(this.meses == null){
			this.meses = new ArrayList<Mes>();
		}
		Mes newMes = new Mes();
		newMes.setMês(Double.parseDouble(novoObjeto[1]));
		newMes.createNewListDataObject(novoObjeto, indicadores);
		
		this.meses.add(newMes);
	}
	
	
	
}
