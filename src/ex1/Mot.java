package ex1;

import java.util.ArrayList;
import java.util.List;

public class Mot implements Comparable<Mot> {
	
	private String value;
	private List<Mot> voisins;
	
	public Mot(String value) {
		this.value = value;
		this.voisins = new ArrayList<Mot>();
	}
	
	public String getValue() {
		return this.value;
	}
	
	public List<Mot> getVoisins() {
		return this.voisins;
	}
	
	public void addVoisin(Mot mot) {
		this.voisins.add(mot);
	}
	
	/**
	 * Methode permettant de verifier si un mot differe de N lettres avec un autre mot.<br>
	 * On suppose les 2 mots de meme longueur
	 * @param mot Le mot a comparer
	 * @param n Nombre de differences recherchees
	 * @return true si le mot differe de n lettres, false sinon
	 */
	public boolean differeDeNLettres(Mot mot, int n) {
		int i = 0;
		int diff = 0;
		while(i < this.value.length())
			if(this.getValue().charAt(i) != mot.getValue().charAt(i))
				diff++;
		return diff == n;
	}
	
	public String toString() {
		String res = "Mot: " + this.value + "\nVoisins : [";
		for(Mot mot : this.voisins)
			res += mot + ", ";
		res = res.substring(0, res.length() - 2) + "]";
		return res;
	}

	@Override
	public int compareTo(Mot mot) {
		return this.value.compareToIgnoreCase(mot.getValue());
	}
	
}
