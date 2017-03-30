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
	public String parcoursEnProfondeur(Mot mot, Mot mot2, Set<Mot> dejaVisites, boolean parcoursPropre) {
		// Dans le cas oÃ¹ on veut parcourir un seul mot
		if(dejaVisites == null)
			dejaVisites = new HashSet<Mot>();
		List<Mot> parcours = new ArrayList<Mot>();
		if(parcoursPropre)
			return this.parcoursEnProfondeur(mot, mot2, dejaVisites, parcours, parcoursPropre);
		else
			return mot.getValue() + this.parcoursEnProfondeur(mot, mot2, dejaVisites, parcours, parcoursPropre);

	}
	
	private String parcoursEnProfondeur(Mot mot, Mot mot2, Set<Mot> dejaVisites, List<Mot> parcours, boolean parcoursPropre) {
		//ajout du mot au mots visités 
		dejaVisites.add(mot);
		
		//ajout du mot au parcours
		if(!parcours.contains(mot))
			parcours.add(mot);
		
		//condition d'arrêt : si le mot courant a dans ses voisins le mot recherché
		if(mot.getVoisins().contains(mot2)) {
			if(parcoursPropre){
				parcours.add(mot2);
				return printParcours(parcours);
			}
			else
				return " " + mot2.getValue();
		}
		
		//sinon on appel recursivement sur chaque voisins
		for(Mot voisin : mot.getVoisins()) {
			
			//on verifie qu'il n'a pas déjà été visité ultérieurement
			if(!dejaVisites.contains(voisin)) {
				if(!parcoursPropre)				
					return " " + voisin.getValue() + this.parcoursEnProfondeur(voisin, mot2, dejaVisites, parcours, parcoursPropre);
				else
					return this.parcoursEnProfondeur(voisin, mot2, dejaVisites, parcours, parcoursPropre);
			}
		}
		
		//on arrive ici si les voisisn ont tous déjà été visités et qu'on a pas trouvé le mot
		/*String res = "";
		for(Mot m : parcours)
			res += " " + m.getValue();
		System.out.print(parcours.size());
		System.out.println(" : " + res.substring(1));*/
		
		parcours.remove(parcours.size() - 1);
		if(!parcours.isEmpty()) {
			return this.parcoursEnProfondeur(parcours.get(parcours.size() - 1), mot2, dejaVisites, parcours, parcoursPropre);
		}
		
		//if(mot2 == null)
		return "";
		//else return null;
	}

	public String printParcours(List<Mot> parcours) {
		String res = "";
		for(Mot m : parcours)
			res += " " + m.getValue();
		return res.substring(1);
	}
	
}
