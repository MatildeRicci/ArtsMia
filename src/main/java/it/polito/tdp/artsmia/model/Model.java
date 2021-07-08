package it.polito.tdp.artsmia.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	//come attributo della classe avremo un grafo pesato, semplice e non orientato
	//i cui vertici I vertici rappresentano tutti gli oggetti presenti nel DB (tabella objects, classe ArtObject)
	private Graph<ArtObject, DefaultWeightedEdge> grafo;
	private ArtsmiaDAO dao;
	private Map<Integer, ArtObject> idMap;
	
	public Model() {
		dao = new ArtsmiaDAO();
		idMap = new HashMap<Integer, ArtObject>();
	}
	
	public void creaGrafo() {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//Devo riempire il grafo
		//Aggiungo i vertici :
		//1) recupero tutti gli ArtObject dal db
		//2) li inserisco come vertici 
		
		dao.listObjects(idMap);
		Graphs.addAllVertices(grafo, idMap.values());
		
		//Aggiungo gli archi :
		//APPROCCIO 1 (poco efficiente, non sempre va a buon fine->funziona solo quando il numero di vertici è molto molto basso)
		//-->doppio ciclo for sui vertici, dati due vertici controllo se sono collegati
		
	/*	for(ArtObject a1 : this.grafo.vertexSet()) {
			for(ArtObject a2 : this.grafo.vertexSet()) {
				if(!a1.equals(a2) && !this.grafo.containsEdge(a1,a2)) {
					//Devo collegare a1 ad a2?
					int peso = dao.getPeso(a1,a2);
					if(peso > 0) {
						Graphs.addEdge(this.grafo, a1, a2, peso);
					}
				}
			}
		} */  //L'approccio 1 non giunge a termine poichè ci sono troppi vertici 
		
		//APPROCCIO 3 :
		for(Adiacenza a : dao.getAdiacenze()) {
		
				Graphs.addEdge(this.grafo, idMap.get(a.getId1()), idMap.get(a.getId2()), a.getPeso());
	
		}
		
		System.out.println("GRAFO CREATO!");
		System.out.println("numero VERTICI : " + grafo.vertexSet().size());
		System.out.println("numero ARCHI : " + grafo.edgeSet().size());
	}
	
}






















