package it.polito.tdp.poweroutages.model;

import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.poweroutages.db.PowerOutageDAO;

public class Model {

	PowerOutageDAO podao;
	
	
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
	}

	private void recursive(LinkedList<Blackout> listaBlackOut, float livelloTempo, LinkedList<Blackout> lista) {

		
	}
	

}
