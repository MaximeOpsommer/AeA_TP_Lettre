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
				builder.append(i + ": " + this.parcoursEnProfondeur(mot, null, dejaVisites, false) + "\n");
			}
		}
		return builder.toString();
	}
	
	// ici, on laisse le paramètre dejaVisites, car utilisés lors du parcours pour tous les mots
	public String parcoursEnProfondeur(Mot mot, Mot mot2, Set<Mot> dejaVisites, boolean chemin) {
		// Dans le cas oÃ¹ on veut parcourir un seul mot
		if(dejaVisites == null)
			dejaVisites = new HashSet<Mot>();
		List<Mot> parcours = new ArrayList<Mot>();
		return mot.getValue() + this.parcoursEnProfondeur(mot, mot2, dejaVisites, parcours, chemin);

	}
	
	private String parcoursEnProfondeur(Mot mot, Mot mot2, Set<Mot> dejaVisites, List<Mot> parcours, boolean chemin) {
		dejaVisites.add(mot);
		if(!parcours.contains(mot))
			parcours.add(mot);
		if(mot.getVoisins().contains(mot2)) {
			if(chemin) {
				String res = "";
				for(Mot m : parcours)
					res += " " + m.getValue();
				return res.substring(1);
			}
			else
				return " " + mot2.getValue();
		}
		for(Mot voisin : mot.getVoisins()) {
			if(!dejaVisites.contains(voisin)) {
				if(!chemin){					
					return " " + voisin.getValue() + this.parcoursEnProfondeur(voisin, mot2, dejaVisites, parcours, chemin);
				}
				this.parcoursEnProfondeur(voisin, mot2, dejaVisites, parcours, chemin);
			}
		}
		String res = "";
		for(Mot m : parcours)
			res += " " + m.getValue();
		System.out.print(parcours.size());
		System.out.println(" : " + res.substring(1));
		parcours.remove(parcours.size() - 1);
		if(!parcours.isEmpty()) {
			if(!chemin) {				
				return this.parcoursEnProfondeur(parcours.get(parcours.size() - 1), mot2, dejaVisites, parcours, chemin);
			}
			this.parcoursEnProfondeur(parcours.get(parcours.size() - 1), mot2, dejaVisites, parcours, chemin);
		}
		
		//if(mot2 == null)
		return "";
		//else return null;
	}
	
}
