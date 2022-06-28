package it.polito.tdp.food.model;

public class Event implements Comparable<Event>{
	
	public enum EventType {
		INIZIO_LAVORAZIONE,
		STAZIONE_LIBERATA
	}
	
	private double time;
	private EventType type;
	private Food cibo;
	private Double durata;
	private Stazione stazione;
	
	public Event(double time, EventType type, Food cibo, Double durata, Stazione stazione) {
		super();
		this.time = time;
		this.type = type;
		this.cibo = cibo;
		this.durata = durata;
		this.stazione = stazione;
	}

	public double getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public Food getCibo() {
		return cibo;
	}

	public void setCibo(Food cibo) {
		this.cibo = cibo;
	}

	public double getDurata() {
		return durata;
	}

	public void setDurata(double durata) {
		this.durata = durata;
	}

	public Stazione getStazione() {
		return stazione;
	}

	public void setStazione(Stazione stazione) {
		this.stazione = stazione;
	}

	@Override
	public int compareTo(Event o) {
		if (time>o.getTime())
			return 1;
		else 
			return -1;
	}
	
	
	


}
