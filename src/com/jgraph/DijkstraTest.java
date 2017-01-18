/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jgraph;

import com.fxgraph.cells.CircleCell;
import com.fxgraph.cells.RectangleCell;
import com.fxgraph.cells.TriangleCell;
import com.fxgraph.graph.Cell;
import com.fxgraph.graph.CellType;
import com.fxgraph.graph.Edge;
import com.fxgraph.graph.Model;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

/**
 *
 * @author Karol
 */
public class DijkstraTest {
   
    public static void main() {
        
        // Z rozszerzeniem, bo klasa Graph znajduje się też w bibliotece jgrapht
        com.fxgraph.graph.Graph g  = new com.fxgraph.graph.Graph();
        
        // Model zarządzający dodawaniem wierzchołków i krawędzi.
        Model model = g.getModel();
        
        // Dodawanie wierzchołków i krawędzi do modelu.
        g.beginUpdate();
        model.addCell("First node", CellType.CIRCLE);
        model.addCell("Second node", CellType.TRIANGLE);
        model.addCell("Third node", CellType.RECTANGLE);
        model.addCell("Fourth node", CellType.RECTANGLE);
        
        model.addEdge("First node", "Second node", 13.4);
        model.addEdge("First node", "Third node", 4.3);
        model.addEdge("Second node", "Third node", 2);
        model.addEdge("Third node", "Fourth node", 3.4);
        model.addEdge("Fourth node", "Second node", 3);
        model.addEdge("Second node", "First node", 2.3);
        g.endUpdate();
        
        
        // Teraz, żeby zastosować algorytm Dijkstry trzeba zamienić Graph
        // na klasę z biblioteki jgrapht
        // Zakładamy póki co, że grafy są skierowane i ich krawędzie mają wagi.
        SimpleDirectedWeightedGraph<Cell, DefaultWeightedEdge> graph =
                new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);

        model.getAllCells().forEach( (Cell cell) -> graph.addVertex(cell));

        
        model.getAllEdges().forEach( (Edge edge) -> {
            DefaultWeightedEdge e = graph.addEdge(edge.getSource(), edge.getTarget());
            graph.setEdgeWeight(e, edge.getWeigth());
        });
        
        // Chcemy znaleźć ścieżkę pomiędzy First node i Second node. 
        // Wiemy, że w liście znajdują się tylko dwa takie, więc nasza lista będzie miała dwa elementy.
        // ponieważ F < S, to wystarczy posortować wynik alfabetycznie 
        List<Cell> algo = model.getAllCells()
                .stream()	
                .filter((cell) -> cell.getCellId().equals("First node") || cell.getCellId().equals("Second node") )	
                .collect(Collectors.toList());
        
        Collections.sort(algo, (Cell t, Cell t1) -> {
            if((int) t.getCellId().charAt(0) < (int) t1.getCellId().charAt(0))
                return -1;
            if((int) t.getCellId().charAt(0) > (int) t1.getCellId().charAt(0))
                return 1;
            return 0;
        });
        //algo.forEach(x -> System.out.println(x));
        
        List<DefaultWeightedEdge> shortestPath = DijkstraShortestPath.findPathBetween(graph, algo.get(0), algo.get(1));
        
        shortestPath.forEach((DefaultWeightedEdge edge)->System.out.println(edge));
       
    }
    
    
   
    
}
