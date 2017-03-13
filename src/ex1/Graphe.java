package ex1;

import java.util.ArrayList;
import java.util.List;

public class Graphe {

	private List<Mot> mots;
	private List<Arrete> arretes;
	
	public Graphe(String[] mots) {
		this.mots = new ArrayList<Mot>();
		this.arretes = new ArrayList<Arrete>();
		for(String str : mots) {
			this.mots.add(new Mot(str));
		}
	}
	
	public List<Mot> getMots() {
		return this.mots;
	}
	
	public List<Arrete> getArretes() {
		return this.arretes;
	}
	
	public void ajouterArrete(Mot mot1, Mot mot2) {
		mot1.addVoisin(mot2);
		mot2.addVoisin(mot1);
		Arrete arrete = new Arrete(mot1, mot2);
		this.arretes.add(arrete);
	}
	
}
