package ex1;

public class Arrete {
	
	private Mot mot1;
	private Mot mot2;
	
	public Arrete(Mot mot1, Mot mot2) {
		this.mot1 = mot1;
		this.mot2 = mot2;
	}
	
	public Mot getMot1() {
		return this.mot1;
	}
	
	public Mot getMot2() {
		return this.mot2;
	}
	
	public String toString() {
		return this.mot1.getValue() + " - " + this.mot2.getValue();
	}

}
