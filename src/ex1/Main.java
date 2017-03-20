package ex1;

import java.util.HashSet;
import java.util.List;

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
		main.lettreQuiSaute(g);
		System.out.println(g.parcoursEnProfondeur());
		
		//exo3
		// gag
		Mot mot1 = g.getMots().get(0);
		// arc
		Mot mot2 = g.getMots().get(g.getMots().size() - 1);
		System.out.println(g.parcoursEnProfondeur(mot1, mot2, new HashSet<Mot>()));
	}

}
