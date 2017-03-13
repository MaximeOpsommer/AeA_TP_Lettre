package ex1;

import java.util.List;

public class Main {
	
	public void lettreQuiSaute(Graphe g) {
		List<Mot> mots = g.getMots();
		int nbMots = mots.size();
		for(int i = 0; i <nbMots-1; i++) {
			for(int j = i+1; j < nbMots; j++) {
				Mot mot1 = mots.get(i);
				Mot mot2 = mots.get(j);
				if(mot1.differeDeNLettres(mot2, 1))
					g.ajouterArrete(mot1, mot2);
			}
		}
	}
	
	public static void main(String[] args) {
		Main main = new Main();
		Graphe g = new Graphe(Dicos.dico3);
		main.lettreQuiSaute(g);
	}

}
