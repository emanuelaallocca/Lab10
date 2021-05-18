package it.polito.tdp.rivers.model;

import it.polito.tdp.rivers.db.RiversDAO;

import java.time.LocalDate;
import java.util.*;

public class Model {
	
	private  RiversDAO dao;
	
	public Model() {
		dao = new RiversDAO();
	}
	public List<River> getRivers(){
		return dao.getAllRivers();
	}
	public List<Flow> getFlowByRiver(River r){
		return dao.getAllFlowByRiver(r);
	}
	public LocalDate getDataMinima(River r) {
		List <Flow> flows = dao.getAllFlowByRiver(r);
		LocalDate datamin = flows.get(0).getDay();
		
		for (Flow f : flows)
			if (datamin.isAfter(f.getDay()))
				datamin = f.getDay();
		return datamin;
	}
	
	public LocalDate getDataMassima(River r) {
		List <Flow> flows = dao.getAllFlowByRiver(r);
		LocalDate datamax = flows.get(0).getDay();
		
		for (Flow f : flows)
			if (datamax.isBefore(f.getDay()))
				datamax = f.getDay();
		return datamax;
	}
	public double getFlussoMedio(River r) {
		List <Flow> flows = dao.getAllFlowByRiver(r);
		double somma = 0;
		
		for (Flow f : flows)
			somma = somma + f.getFlow();
		return (somma/flows.size());
	}
	
	private List<Flow> flows;
	double k = 1.0;
	private double f_med ;
	double Q ;
	private double C;
	private double f_out_min ;
	private double f_out_special ;
	int giorni;
	double prob;
	double C_med;
	
	public void analizzaEvento(River r) {
		flows = this.getFlowByRiver(r);
		f_med = this.getFlussoMedio(r);
		
		Q = k*(f_med/(60*60*24))*30;
		C = Q/2;
		f_out_min =  f_med *0.8;
		f_out_special = f_out_min *10;
		
		prob = Math.random()*100;
		giorni =0;
		C_med = C;
		for (Flow f: flows) {
		if (prob <=5) {
			if (f.getFlow()>=f_out_special) {
				C_med = (C + C+ (f.getFlow()- f_out_special))/2;
				C = C+ (f.getFlow()- f_out_special);
			}
			else
				giorni++;
			
			if (C>Q)
				C=Q;
		}
		else {
			if (f.getFlow()>f_out_min) {
				C_med = (C + C+ (f.getFlow()- f_out_min))/2;
				C = C+ (f.getFlow()- f_out_min);
			}
			else
				giorni++;
			
			if (C>Q)
				C=Q;
		}
	
	  }
	}

	public void setK(double k) {
		this.k = k;
	}

	public int getGiorni() {
		return giorni;
	}

	public double getC_med() {
		return C_med;
	}
	
}
