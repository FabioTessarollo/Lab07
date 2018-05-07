package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.poweroutages.db.PowerOutageDAO;

public class Model {

	PowerOutageDAO podao;
	ArrayList<Blackout> blackoutNerc;
	ArrayList<Blackout> soluzione;
	
	int nerc;
	int oreMax;
	int annoMax;
	
	public Model() {
		podao = new PowerOutageDAO();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	public List<Blackout> analysis(int annoMax, int oreMax, int nerc) {
		this.annoMax = annoMax;
		this.oreMax = oreMax;
		this.nerc = nerc;
		this.soluzione = null;
		this.blackoutNerc = new ArrayList(podao.getBlackoutNerc(nerc));
		ArrayList<Blackout> parziale = new ArrayList<Blackout>();
		recursive(parziale);
		return soluzione;
	}

	private void recursive(ArrayList<Blackout> parziale) {
		
		if(soluzione == null || personeCoinvolte(parziale) > personeCoinvolte(soluzione)) {
			this.soluzione = new ArrayList(parziale);
		}
		
		for(Blackout b : blackoutNerc) {
			if(!parziale.contains(b)) {
				parziale.add(b);
				
				if(valida(parziale)) {			
					recursive(parziale);
				}
	
				parziale.remove(parziale.size()-1);
			
			}
		}
		
	}

	private boolean valida(ArrayList<Blackout> parziale) {
		if(parziale.size() == 0)
			return true;
		
		LocalDateTime t1 = parziale.get(0).getT1();
		LocalDateTime t2 = parziale.get(0).getT2();
		
		for(Blackout b : parziale) {
			if(b.getT1().isBefore(t1) || b.getT1().isEqual(t1))
				t1 = b.getT1();
			
			if(b.getT2().isAfter(t2) || b.getT2().isEqual(t2))
				t2 = b.getT2();
		}
		
		float tot = 0;
		for(Blackout b : parziale) {
			tot += Math.abs(b.getDurata().toMinutes());
			
		}
		
		if(tot > oreMax*60 || t2.getYear() - t1.getYear() > annoMax)
			return false;
		else
			return true;

		
	}
	
	public int personeCoinvolte(ArrayList<Blackout> parziale) {
		if(parziale.size() == 0) {
			return 0;
		}
		int i = 0;
		for(Blackout b : parziale) {
			i += b.getNumeroPersone();
		}
		return i;
	}
	

}
