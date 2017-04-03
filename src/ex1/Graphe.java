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
	
	// ici, on laisse le paramï¿½tre dejaVisites, car utilisï¿½s lors du parcours pour tous les mots
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
		//ajout du mot au mots visitï¿½s 
		dejaVisites.add(mot);
		
		//ajout du mot au parcours
		if(!parcours.contains(mot))
			parcours.add(mot);
		
		//condition d'arrï¿½t : si le mot courant a dans ses voisins le mot recherchï¿½
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
			
			//on verifie qu'il n'a pas dï¿½jï¿½ ï¿½tï¿½ visitï¿½ ultï¿½rieurement
			if(!dejaVisites.contains(voisin)) {
				if(!parcoursPropre)				
					return " " + voisin.getValue() + this.parcoursEnProfondeur(voisin, mot2, dejaVisites, parcours, parcoursPropre);
				else
					return this.parcoursEnProfondeur(voisin, mot2, dejaVisites, parcours, parcoursPropre);
			}
		}

		//on arrive ici si les voisisn ont tous dï¿½jï¿½ ï¿½tï¿½ visitï¿½s et qu'on a pas trouvï¿½ le mot		
		parcours.remove(parcours.size() - 1);
		if(!parcours.isEmpty()) {
			return this.parcoursEnProfondeur(parcours.get(parcours.size() - 1), mot2, dejaVisites, parcours, parcoursPropre);
		}
		return "";
	}

	
	public String bfs(Mot depart, Mot arrivee){
		
		Mot current = depart;
		
		//si les mots sont deja marques
		boolean[] marques = initBoolArray();
		
		//longeurs du plus petit chemin pour aller au mot
		int[] longeurs = initLongeurs();
		
		//on marque le premier mot et on met sa longeur a 0
		marques[this.mots.indexOf(current)] = true;
		longeurs[this.mots.indexOf(current)] = 0;
		Mot[] previous = new Mot[this.mots.size()];
		
		boolean arriveeFound = false;
		while(!allVisited(marques)){
			int indexOfCurrent = this.mots.indexOf(current);
			for(Mot voisin : current.getVoisins()){
				//calculer la longueur pour aller a ce voisin
				int indexOfVoisin = this.mots.indexOf(voisin); //index du voisin dans le graphe
				int nvlleValeur = longeurs[indexOfCurrent]+1;
				
				if(longeurs[indexOfVoisin] == -1 || nvlleValeur < longeurs[indexOfVoisin]){
					longeurs[indexOfVoisin] = nvlleValeur; //on affecte la nouvelle valeur
					previous[indexOfVoisin] = this.mots.get(indexOfCurrent); // on affecte le meilleur precedent
				}
			}
			
			//on choisit le sommet non marque de plus petit poids different de -1
			//si c'est le final on vérifie que ses voisins ont été marqués, si oui return
			//si on trouve -1, cela signifie qu'il n'y a pas de chemin possible
			int indexOfMinSommet = getMinSommet(marques, longeurs);
		
			if(indexOfMinSommet != -1){
				marques[indexOfMinSommet] = true;
				
				if(current.equals(arrivee)){
					//verifier que ses voisins sont marqués, si oui return
					arriveeFound = true;
					if(verifyArriveeVoisins(arrivee, marques)){
						return printParcrous(depart, arrivee, previous);
					}
				}
				
				current = this.mots.get(indexOfMinSommet);
				
			} else {
				if(arriveeFound)
					return printParcrous(depart, arrivee, previous);
				else
					return "Il n'y a pas de chemin possible";
			}
			
		}
		
		//on affiche le parcours
		 return printParcrous(depart, arrivee, previous);
	}
	
	private boolean verifyArriveeVoisins(Mot arrivee, boolean[] marques){
		for(Mot voisinsArrivee : arrivee.getVoisins()){
			if(!marques[this.mots.indexOf(voisinsArrivee)]){
				return false;
			}
		}
		return true;
	}


	private String printParcrous(Mot depart, Mot arrivee, Mot[] previous) {
		int indexDepart = this.mots.indexOf(depart);
		int indexArrivee = this.mots.indexOf(arrivee);
		
		String res = ""; 
		Mot tmp = arrivee;
		while(!tmp.equals(depart)){
			res =  " -> " + tmp.getValue() + res;
			tmp = previous[this.mots.indexOf(tmp)];
		}
		return depart.getValue() + res;
	}

	private int getMinSommet(boolean[] marques, int[] longeur) {
		int min = -1;
		for(int i = 0; i < marques.length ; i++){
			if(!marques[i]){
				if(longeur[i] != -1) {					
					if(min == -1)
						min = i;
					else if(longeur[i] < longeur[min])
						min = i;
				}
			}
		}
		
		return min;
	}

	private int[] initLongeurs() {
		int[] l = new int[this.mots.size()];
		for(int i = 0; i < l.length; i++){
			l[i] = -1;
		}
		return l;
	}
	
	private boolean allVisited(boolean[] v){
		for(int i = 0; i < v.length; i++){
			if(!v[i]){
				return v[i];
			}
		}
		return true;
	}
	
	private boolean[] initBoolArray(){
		boolean[] b = new boolean[this.mots.size()];
		for(int i = 0; i < this.mots.size();i++)
			b[i] = false;
		
		return b;
	}
	
	private String printParcours(List<Mot> parcours) {
		String res = "";
		for(Mot m : parcours)
			res += " " + m.getValue();
		return res.substring(1);
	}
	
}
