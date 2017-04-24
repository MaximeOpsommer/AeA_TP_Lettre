package ex1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
	
	// ici, on laisse le param�tre dejaVisites, car utilis�s lors du parcours pour tous les mots
	public String parcoursEnProfondeur(Mot mot, Mot mot2, Set<Mot> dejaVisites, boolean parcoursPropre) {
		// Dans le cas où on veut parcourir un seul mot
		if(dejaVisites == null)
			dejaVisites = new HashSet<Mot>();
		List<Mot> parcours = new ArrayList<Mot>();
		if(parcoursPropre){
			//on init les map de poids et des précédents
			Map<Mot, Mot> previous = new HashMap<Mot, Mot>();
			previous.put(mot, null);
			Map<Mot, Integer> poids = new HashMap<Mot, Integer>();
			poids.put(mot, 0);
			return this.parcoursEnProfondeur(mot, mot2, dejaVisites, parcours, parcoursPropre, poids, previous);
		} else{
			return mot.getValue() + this.parcoursEnProfondeur(mot, mot2, dejaVisites, parcours, parcoursPropre, null, null);
		}

	}
	
	private boolean allDejaVisites(Set<Mot> v){
		for(Mot m : this.mots){
			if(!v.contains(m)){
				return false;
			}
		}
		return true;
	}
	
	private boolean voisinsTousMarques(Mot m, Set<Mot> dejaV){
		for(Mot v : m.getVoisins()){
			if(!dejaV.contains(v)){
				return false;
			}
		}
		return true;
	}
	
	private String parcoursEnProfondeur(Mot mot, Mot mot2, Set<Mot> dejaVisites, List<Mot> parcours, boolean parcoursPropre, Map<Mot, Integer> poids, Map<Mot, Mot> previous) {
		//ajout du mot au mots visit�s 
		dejaVisites.add(mot);
		
		//ajout du mot au parcours
		if(!parcours.contains(mot))
			parcours.add(mot);
		
		//condition d'arr�t : si le mot courant a dans ses voisins le mot recherch�
		if(mot.getVoisins().contains(mot2)) {
			if(parcoursPropre){
				//parcours.add(mot2);
				
				if(allDejaVisites(dejaVisites)){
					return printParcours(parcours);
				} else {
					Mot precedent = mot;
					while(voisinsTousMarques(precedent, dejaVisites)){
						if(precedent.equals(parcours.get(0))){
							parcours.add(mot2);
							return printParcours(parcours);
						}
						precedent = previous.get(precedent);
					}
				}
			} else{
				return " " + mot2.getValue();
			}
		}
		
		//sinon on appel recursivement sur chaque voisins
		for(Mot voisin : mot.getVoisins()) {
			
			//on verifie qu'il n'a pas d�j� �t� visit� ult�rieurement
			if(!dejaVisites.contains(voisin)) {
				if(!parcoursPropre)				
					return " " + voisin.getValue() + this.parcoursEnProfondeur(voisin, mot2, dejaVisites, parcours, parcoursPropre, poids, previous);
				else {
					// Si le prochain noeud a deja au moins un voisin marque (dejaVisite)
					// (qui n'est pas le noeud actuel), alors on vérifie s'il existe un plus court chemin
					if(aUnVoisinMarque(mot, dejaVisites, voisin)){
						Mot plusPetitPoids = mot;
						for(Mot m : voisin.getVoisins()) {
							if(dejaVisites.contains(m)) {
								if(poids.get(m) < poids.get(plusPetitPoids) ) {
									plusPetitPoids = m;
								}
							}
						}
						//TODO on supprime le dernier du parcours tant que le mot n'est pas celui avec le plus petit poids proposé
						while(!parcours.get(parcours.size() -1).equals(plusPetitPoids)){
							parcours.remove(parcours.size() -1);
						}
						
					}
					previous.put(voisin, mot);
					poids.put(voisin, poids.get(previous.get(voisin)) + 1);
					return this.parcoursEnProfondeur(voisin, mot2, dejaVisites, parcours, parcoursPropre, poids, previous);
				}
			}
		}

		if(parcoursPropre && voisinsTousMarques(mot, dejaVisites)) {
			// on reprends le parcours a partit d'un mot où il reste des voisins non marques
			Mot next = null;
			for(Mot m : mot.getVoisins()) {
				if(!voisinsTousMarques(m, dejaVisites)) {
					next = m;
					break;
				}
			}
			// on réécrit le parcours
			parcours.clear();
			int p = poids.get(next);
			for(int i = 0; i <= p; i++)
				parcours.add(null);
			parcours.set(p, next);
			Mot prev = previous.get(next);
			while(prev != null) {
				parcours.set(poids.get(prev), prev);
				prev = previous.get(prev);
			}
			// on rapelle la fonction
			return this.parcoursEnProfondeur(next, mot2, dejaVisites, parcours, parcoursPropre, poids, previous);
		}
		
		//on arrive ici si les voisisn ont tous d�j� �t� visit�s et qu'on a pas trouv� le mot		
		parcours.remove(parcours.size() - 1);
		if(!parcours.isEmpty()) {
			return this.parcoursEnProfondeur(parcours.get(parcours.size() - 1), mot2, dejaVisites, parcours, parcoursPropre, poids, previous);
		}
		return "";
	}

	private boolean aUnVoisinMarque(Mot mot, Set<Mot> dejaVisites, Mot voisin) {
		boolean aUnVoisinMarque = false;
		for(Mot m : voisin.getVoisins()) {
			if(dejaVisites.contains(m) && !m.equals(mot)) {
				aUnVoisinMarque = true;
				break;
			}
		}
		return aUnVoisinMarque;
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
			//si c'est le final on v�rifie que ses voisins ont �t� marqu�s, si oui return
			//si on trouve -1, cela signifie qu'il n'y a pas de chemin possible
			int indexOfMinSommet = getMinSommet(marques, longeurs);
		
			if(indexOfMinSommet != -1){
				marques[indexOfMinSommet] = true;
				
				if(current.equals(arrivee)){
					//verifier que ses voisins sont marqu�s, si oui return
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
