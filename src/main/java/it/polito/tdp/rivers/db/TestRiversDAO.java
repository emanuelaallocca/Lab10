package it.polito.tdp.rivers.db;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;
import java.util.*;

public class TestRiversDAO {

	public static void main(String[] args) {
		RiversDAO dao = new RiversDAO();
		System.out.println(dao.getAllRivers());
		
		System.out.println("******");
		River r = new River (2, "Vatnsdalsa River");
	    List <Flow> flows = dao.getAllFlowByRiver(r);
	    for (Flow f : flows)
	    	System.out.println(f);
	}

}
