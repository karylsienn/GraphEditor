package com.jgraph;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.fxgraph.graph.Cell;
import com.fxgraph.graph.Model;

public class Dijkstra {

	public static ArrayList<String[]> getShortestPath(com.fxgraph.graph.Graph g,Cell source, Cell target)
	{
		Model model = g.getModel();
		
        SimpleDirectedWeightedGraph<Cell, DefaultWeightedEdge> graph = GraphUtilities.makeGraphForSearch(model);
        
        List<DefaultWeightedEdge> shortestPath = DijkstraShortestPath.findPathBetween(graph, source, target);
        
        ArrayList<String[]> shortestPathList = GraphUtilities.convertPathToList(shortestPath);
		
		return shortestPathList;	
	}
}
