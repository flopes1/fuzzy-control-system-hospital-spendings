package com.poli.fcshs.model.impl;

import java.util.ArrayList;
import java.util.List;

public class Mes {

	private double mês;
	
	private List<DataObject> dados;

	
	public double getMês() {
		return mês;
	}

	public void setMês(double mês) {
		this.mês = mês;
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
