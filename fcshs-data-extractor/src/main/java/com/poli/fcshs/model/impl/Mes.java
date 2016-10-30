package com.poli.fcshs.model.impl;

import java.util.ArrayList;
import java.util.List;

public class Mes {

	private double m�s;
	
	private List<DataObject> dados;

	
	public double getM�s() {
		return m�s;
	}

	public void setM�s(double m�s) {
		this.m�s = m�s;
	}

	public List<DataObject> getDados() {
		return dados;
	}

	public void setDados(List<DataObject> dados) {
		this.dados = dados;
	}
	
	public void createNewListDataObject(String[] linha, String[] indicadores) {
		List<DataObject> listData = new ArrayList<DataObject>();
		for(int i=2; i < linha.length; i++){
			DataObject obj = new DataObject();
			obj.setIndicador(indicadores[i]);
			obj.setValorIndicador(Double.parseDouble(linha[i]));
			listData.add(obj);
		}
		
		this.dados = listData;
	}
	
}
