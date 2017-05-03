package ex1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Main {
	
	public void lettreQuiSaute(Graphe g) {
		List<Mot> mots = g.getMots();
		int nbMots = mots.size();
		for(int i = 0; i <nbMots-1; i++) {
			for(int j = i+1; j < nbMots; j++) {
				Mot mot1 = mots.get(i);
				Mot mot2 = mots.get(j);
				if(mot1.differeDeNLettres(mot2, 1)) {
					g.ajouterArrete(mot1, mot2);
					//System.out.println("arrete ajoutee : (" + mot1.getValue() + " : " + mot2.getValue() + ")");
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Main main = new Main();
		Graphe g = new Graphe(Dicos.dico3);
		
		/*
		Graphe g = new Graphe(Dicos.dico6);
		g.ajouterArrete(g.getMots().get(0), g.getMots().get(1));
		g.ajouterArrete(g.getMots().get(0), g.getMots().get(3));
		g.ajouterArrete(g.getMots().get(1), g.getMots().get(2));
		g.ajouterArrete(g.getMots().get(1), g.getMots().get(3));
		g.ajouterArrete(g.getMots().get(1), g.getMots().get(4));
		g.ajouterArrete(g.getMots().get(2), g.getMots().get(4));
		g.ajouterArrete(g.getMots().get(3), g.getMots().get(5));
		g.ajouterArrete(g.getMots().get(4), g.getMots().get(5));
		
		*/
		
		
		main.lettreQuiSaute(g);
		//System.out.println(g.parcoursEnProfondeur());
		/*for(Arrete a : g.getArretes()) {			
			System.out.println(a);
		}*/
		
		//exo3
		// gag
		Mot mot1 = g.getMots().get(g.getMots().indexOf(new Mot("gag")));
		// arc
		//Mot mot2 = g.getMots().get(g.getMots().size() - 1);
		Mot mot2 = g.getMots().get(g.getMots().indexOf(new Mot("arc")));
		//System.out.println(g.parcoursEnProfondeur(mot1, mot2, null, true));
		
		//Mot depart = g.getMots().get(0);
		//Mot arrivee = g.getMots().get(5);
		
		//System.out.println(g.parcoursEnProfondeur(mot1, mot2, null, true));
		Map<Mot, Integer> poids = new HashMap<Mot, Integer>();
		poids.put(mot1, 0);
		System.out.println(g.parcoursEnProfondeurQ4(mot1, mot2, new HashSet<Mot>(), new ArrayList<Mot>(), true, poids, new HashMap<Mot, Mot>()));
		System.out.println(g.bfs(mot1, mot2));
		
		//////
	}

}
