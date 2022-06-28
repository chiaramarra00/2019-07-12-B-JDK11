package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
    private FoodDao dao;
	
	private Graph<Food,DefaultWeightedEdge> grafo;
	private Map<Integer,Food> idMap;
	
	private Simulator sim;
	
	public Model() {
		dao = new FoodDao();
		idMap = new HashMap<>();
		
		this.dao.listAllFoods(idMap);
		
		sim = new Simulator();
	}
	
	public void creaGrafo(int porzioni) {
		//creo il grafo
		this.grafo = 
				new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		//aggiungo i vertici
		Graphs.addAllVertices(this.grafo, 
				this.dao.getVertici(porzioni, this.idMap));
		
		//aggiungo gli archi
		for(Adiacenza a : this.dao.getArchi(porzioni, idMap)) {
			Graphs.addEdgeWithVertices(this.grafo, a.getF1(), 
					a.getF2(), a.getPeso());
		}
		
		System.out.println("Grafo creato!");
		System.out.println(String.format("# Vertici: %d", 
				this.grafo.vertexSet().size()));
		System.out.println(String.format("# Archi: %d", 
				this.grafo.edgeSet().size()));
	}
	
	public List<Food> getVertici(){
		return new ArrayList<>(this.grafo.vertexSet());
	}
	
	public boolean grafoCreato() {
		if(this.grafo == null)
			return false;
		else 
			return true;
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
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
	
	public void simula (Food cibo, int k) {
		List<Adiacenza> adiacenze = doGrassi(cibo,k);
		sim.init(grafo,cibo,k,adiacenze);
		sim.run();
	}
	
}
