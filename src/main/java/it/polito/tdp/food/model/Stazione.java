package it.polito.tdp.food.model;

public class Stazione {
	
	private int id;
	private boolean occupata;
	
	public Stazione(int i, boolean b) {
		this.id = i;
		this.occupata = b;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isOccupata() {
		return occupata;
	}

	public void setOccupata(boolean occupata) {
		this.occupata = occupata;
	}
	
}
