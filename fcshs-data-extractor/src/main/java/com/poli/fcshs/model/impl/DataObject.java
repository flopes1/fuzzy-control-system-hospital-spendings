package com.poli.fcshs.model.impl;

import com.poli.fcshs.model.IDataObject;

public class DataObject implements IDataObject{

	private String indicador;
	
	private double valorIndicador;

	public DataObject() {
	}

	public String getIndicador() {
		return indicador;
	}

	public void setIndicador(String indicador) {
		this.indicador = indicador;
	}

	public double getValorIndicador() {
		return valorIndicador;
	}

	public void setValorIndicador(double valorIndicador) {
		this.valorIndicador = valorIndicador;
	}


	
	
	
	
}
