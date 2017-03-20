package ex1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	
	// parcours en profondeur pour tous les mots
	public String parcoursEnProfondeur() {
		Set<Mot> dejaVisites = new HashSet<Mot>();
		StringBuilder builder = new StringBuilder();
		int i = 0;
		for(Mot mot : this.getMots()) {
			if(!dejaVisites.contains(mot)) {
				i++;
				builder.append(i + ": " + this.parcoursEnProfondeur(mot, null, dejaVisites) + "\n");
			}
		}
		return builder.toString();
	}
	
	public String parcoursEnProfondeur(Mot mot, Mot mot2, Set<Mot> dejaVisites) {
		// Dans le cas où on veut parcourir un seul mot
		if(dejaVisites == null)
			dejaVisites = new HashSet<Mot>();
		List<Mot> parcours = new ArrayList<Mot>();
		return mot.getValue() + this.parcoursEnProfondeur(mot, mot2, dejaVisites, parcours);

	}
	
	private String parcoursEnProfondeur(Mot mot, Mot mot2, Set<Mot> dejaVisites, List<Mot> parcours) {
		dejaVisites.add(mot);
		if(!parcours.contains(mot))
			parcours.add(mot);
		for(Mot voisin : mot.getVoisins()) {
			if(voisin.equals(mot2)){
				return " " + voisin.getValue();
			}
			
			if(!dejaVisites.contains(voisin)) {
				return " " + voisin.getValue() + this.parcoursEnProfondeur(voisin, mot2, dejaVisites, parcours);
			}
		}
		parcours.remove(parcours.size() - 1);
		if(!parcours.isEmpty())
			return this.parcoursEnProfondeur(parcours.get(parcours.size() - 1), mot2, dejaVisites, parcours);
		
		if(mot2 == null)
			return "";
		else return null;
	}
	
}
