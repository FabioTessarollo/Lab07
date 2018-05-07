package it.polito.tdp.poweroutages.model;

import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.poweroutages.db.PowerOutageDAO;

public class Model {

	PowerOutageDAO podao;
	LinkedList<Blackout> soluzione;
	LinkedList<Blackout> parziale;
	
	int nerc;
	int oreMax;
	int annoMax;
	int best = 0;
	
	public Model() {
		podao = new PowerOutageDAO();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	public List<Blackout> analysis(int annoMax, int oreMax, int nerc) {
		this.parziale = null;
		this.nerc = nerc;
		this.oreMax = oreMax;
		this.annoMax = annoMax;
		this.soluzione = null;
		float livelloTempo = 0;
		
		for(int i = 0; i < 15 - annoMax; i++) {
			LinkedList<Blackout> listaBO = podao.getBlackoutNercData(2000 + i, 2000 + annoMax + i , nerc);
			parziale = new LinkedList<Blackout>();
			recursive(parziale, livelloTempo, listaBO);
			if(numeroPersone(parziale) > numeroPersone(soluzione)) {
				soluzione = new LinkedList<Blackout>(parziale);
			}
			listaBO.clear();
			parziale.clear();
		}
		return soluzione;
	}

	private void recursive(LinkedList<Blackout> listaBlackOut, float livelloTempo, LinkedList<Blackout> lista) {
		for(Blackout b : lista) {
			if(livelloTempo + Math.abs(b.getDurata().toHours()) < oreMax) {
				listaBlackOut.add(b);
				recursive(listaBlackOut, livelloTempo + Math.abs(b.getDurata().toHours()), lista);
				listaBlackOut.remove(listaBlackOut.size()-1);
			}
			else {
				if(numeroPersone(listaBlackOut) > best) {
					best = numeroPersone(listaBlackOut);
					this.parziale = new LinkedList<Blackout>(listaBlackOut);
				}
			}
		}
		
	}
	
	public int numeroPersone(LinkedList<Blackout> lista) {
		if (lista == null)
			return 0;
		
		int conteggio = 0;
		for(Blackout b : lista) {
			conteggio += b.getNumeroPersone();
		}
		return conteggio;
	}
	
	public float tempoTotaleLista(LinkedList<Blackout> lista) {
		if (lista.size() == 0)
			return 0;
		
		float tot = 0;
		for(Blackout b : lista) {
			tot += Math.abs(b.getDurata().toHours());
		}
		return tot;
	}

}
