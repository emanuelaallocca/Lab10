package it.polito.tdp.rivers.model;

import java.util.*;

public class Simulator {

	private Model model;
	
    River river = new River(0, null);
    
	private List<Flow> flows = model.getFlowByRiver(river);
	
	double k = 1.0;
	private double f_med = model.getFlussoMedio(river);
	double Q = k*(f_med/(60*60*24))*30;
	private double C = Q/2;
	private double f_out_min =  f_med *0.8;
	private double f_out_special = f_out_min *10;
	int giorni;

	double C_med;
	public void analizzaEvento() {
		double prob = Math.random()*100;
		giorni =0;
		C_med = C;
		for (Flow f: flows) {
		if (prob <=5) {
			if (f.getFlow()>f_out_special) {
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

	public void setR(River r) {
		this.river = r;
	}

	public void setFlows(List<Flow> flows) {
		this.flows = flows;
	}

	public int getGiorni() {
		return giorni;
	}

	public double getC_med() {
		return C_med;
	}
	
	
	
}
