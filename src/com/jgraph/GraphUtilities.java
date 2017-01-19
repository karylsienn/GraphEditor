package com.jgraph;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.fxgraph.graph.Cell;
import com.fxgraph.graph.Edge;
import com.fxgraph.graph.Model;

public class GraphUtilities {
	/**
	 * 
	 * @param path - list of DefaultWeightedEdge object with edges path
	 * @return converted path to ArrayList with String Arrays as each edge
	 */
	public static ArrayList<String[]> convertPathToList(List<DefaultWeightedEdge> path)
	{
		 ArrayList<String[]> edges = new ArrayList<String[]>();
	        
	        for(DefaultWeightedEdge e : path)
	        {
	        	String[] edgeString = String.valueOf(e).split(":");
	        	edgeString[0]  = edgeString[0].substring(1,edgeString[0].length()-1);
	        	edgeString[1]  = edgeString[1].substring(1,edgeString[1].length()-1);
	        	edges.add(edgeString);        	
	        }
		return edges;
		
	}
	// Teraz, żeby zastosować algorytm Dijkstry trzeba zamienić Graph
    // na klasę z biblioteki jgrapht
    // Zakładamy póki co, że grafy są skierowane i ich krawędzie mają wagi.
	public static SimpleDirectedWeightedGraph<Cell, DefaultWeightedEdge> makeGraphForSearch(Model model)
	{
		
        SimpleDirectedWeightedGraph<Cell, DefaultWeightedEdge> graph =
                new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);

        model.getAllCells().forEach( (Cell cell) -> graph.addVertex(cell));
        
        model.getAllEdges().forEach( (Edge edge) -> {
            DefaultWeightedEdge e = graph.addEdge(edge.getSource(), edge.getTarget());
            graph.setEdgeWeight(e, edge.getWeigth());
        });
        
        return graph;
	}
}
