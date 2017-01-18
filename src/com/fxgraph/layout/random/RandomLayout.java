/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fxgraph.layout.random;

import java.util.List;
import java.util.Random;

import com.fxgraph.graph.Cell;
import com.fxgraph.graph.Graph;
import com.fxgraph.layout.base.Layout;
import javafx.geometry.Point2D;

public class RandomLayout extends Layout {

    Graph graph;

    Random rnd = new Random();

    
    public RandomLayout(Graph graph) {
        
        this.graph = graph; 
    }

    @Override
    public void execute() {

        List<Cell> cells = graph.getModel().getAllCells();

        cells.stream().forEach((Cell cell) -> {
            double x = rnd.nextDouble() * 500;
            double y = rnd.nextDouble() * 500;

            cell.relocate(x, y);
        });

    }

    @Override
    public void insertCellAtPoint(Point2D point) {
        Cell lastCell = graph.getModel().getLastCell();
        Point2D local = lastCell.sceneToLocal(point)
                .subtract(lastCell.getWidth() / 2.0, lastCell.getHeight() / 2.0);
                
        lastCell.relocate(local.getX(), local.getY());
    }
    
    

}
