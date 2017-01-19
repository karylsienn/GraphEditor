package com.jgraph;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.alg.BellmanFordShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.fxgraph.graph.Cell;
import com.fxgraph.graph.Model;

public class BellmanFord {
	
	public static ArrayList<String[]> getShortestPath(com.fxgraph.graph.Graph g,Cell source, Cell target)
	{
		Model model = g.getModel();
		
        SimpleDirectedWeightedGraph<Cell, DefaultWeightedEdge> graph = GraphUtilities.makeGraphForSearch(model);
        
        List<DefaultWeightedEdge> shortestPath = BellmanFordShortestPath.findPathBetween(graph, source, target);
        
        ArrayList<String[]> shortestPathList = GraphUtilities.convertPathToList(shortestPath);
		
		return shortestPathList;	
		
	}
	
	public static double getCostToVertex(com.fxgraph.graph.Graph g,Cell source,Cell target)
	{
		Model model = g.getModel();
		SimpleDirectedWeightedGraph<Cell, DefaultWeightedEdge> graph = GraphUtilities.makeGraphForSearch(model);
      
		BellmanFordShortestPath<Cell, DefaultWeightedEdge> belmanford = new BellmanFordShortestPath<Cell, DefaultWeightedEdge>(graph, source);
		
		
		return belmanford.getCost(target);
		
	}
}
