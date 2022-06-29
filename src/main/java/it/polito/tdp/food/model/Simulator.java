package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.food.model.Event.EventType;

public class Simulator {
	
	    //Modello
	    private Graph<Food, DefaultWeightedEdge> grafo;
		private List<Stazione> stazioni;
		private List<Adiacenza> adiacenzeIniziali;
		
		//Parametri della simulazione 
		private int k;
		
		//Coda degli eventi
		private PriorityQueue<Event> queue;
		
		//Statistiche
		private List<Food> cibiPreparati;
		private double durata;
		
		private void creaStazioni() {
			for (int i = 0; i < k; i ++)
				this.stazioni.add(new Stazione(i,false));
		}
		
		private void creaEventi() {
			int inizio = 0;
			for (int i = 0; i < k; i++) {
				Food cibo = adiacenzeIniziali.get(i).getF2();
				double durata = adiacenzeIniziali.get(i).getPeso();
				Stazione stazione = stazioni.get(i);
				Event e = new Event(inizio, EventType.INIZIO_LAVORAZIONE, cibo, durata, stazione);
				this.queue.add(e);
				
			}
		}
		
		
		public void init(Graph<Food, DefaultWeightedEdge> grafo, Food cibo, int k, List<Adiacenza> adiacenze) {
			this.grafo = grafo;
			this.k = k;
			this.adiacenzeIniziali = adiacenze;
			this.queue = new PriorityQueue<Event>();
			cibiPreparati = new LinkedList<Food>();
			this.durata = 0;
			this.stazioni = new LinkedList<Stazione>();
			creaStazioni();
			creaEventi();
		}
		
		
		public void run() {
			while(!queue.isEmpty()) {
				Event e = queue.poll();
				this.durata = e.getTime();
				processaEvento(e);
			}
		}
		
		
		private void processaEvento(Event e) {
			switch (e.getType()){
			case INIZIO_LAVORAZIONE:
				e.getStazione().setOccupata(true);
				cibiPreparati.add(e.getCibo());
				queue.add(new Event(e.getTime()+e.getDurata(), EventType.STAZIONE_LIBERATA, null, 0.0, e.getStazione()));
				
				double inizio = e.getTime()+e.getDurata();
				Food cibo = null;
				Double durata = null;
				for (Adiacenza a : doGrassi(e.getCibo(),Graphs.successorListOf(grafo, e.getCibo()).size())) {
					if (!cibiPreparati.contains(a.getF2())) {
						cibo = a.getF2();
						durata = a.getPeso();
					}
				}
				Stazione stazione = null;
				for (Stazione s : this.stazioni) {
					if (!s.isOccupata()) {
						stazione = s;
						break;
					}
				}
				if (cibo==null || stazione==null)
					return;
				queue.add(new Event(inizio, EventType.INIZIO_LAVORAZIONE, cibo, durata, stazione));
			case STAZIONE_LIBERATA:
				e.getStazione().setOccupata(false);
				break;
		    }
		}
		
		public List<Adiacenza> doGrassi(Food f1, int n) {
			List<Adiacenza> result = new ArrayList<>();
			List<Food> successori = Graphs.successorListOf(grafo, f1);
			List<Adiacenza> lista = new ArrayList<>();
			for (Food f2 : successori) {
				lista.add(new Adiacenza(f1,f2,grafo.getEdgeWeight(grafo.getEdge(f1, f2))));
			}
			Collections.sort(lista);
			for (int i=0;i<n;i++) {
				result.add(lista.get(i));
			}
			return result;
		}

		public List<Food> getCibiPreparati() {
			return cibiPreparati;
		}

		public double getDurata() {
			return durata;
		}
		
}
