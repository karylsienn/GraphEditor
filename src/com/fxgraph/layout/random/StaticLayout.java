/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fxgraph.layout.random;

import com.fxgraph.graph.Cell;
import com.fxgraph.graph.Graph;
import com.fxgraph.layout.base.Layout;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;

/**
 *
 * @author Karol
 */
public class StaticLayout extends Layout {

    
    private final Graph graph;
    
    public StaticLayout(Graph graph) {
        this.graph = graph;
    }
    
    public Graph getGraph() {
        return this.graph;
    }
        
    @Override
    public void execute() {
        List<Cell> cells = graph.getModel().getAllCells();
        cells.stream().forEach( (Cell cell) -> 
            cell.relocate(cell.getLayoutX(), cell.getLayoutY())
        ); 
    }
    
    /**
     * Assumes that graph.endUpdate() is called first.
     * @param positions to which the cells should be placed.
     * @throws Exception when size of cell container is 
     * different than for positions container
     */
    public void setInitialPositions(ArrayList<Point2D> positions) throws Exception {
        List<Cell> cells = graph.getModel().getAllCells();
        if( cells.size() != positions.size() ) 
            throw new Exception("Initial positions must match the number of cells");
        
        for(int i = 0; i < cells.size(); i++) {
            Point2D point = positions.get(i);
            cells.get(i).relocate(
                    point.getX(), point.getY());
        } 
       
    }

    /**
     * Assumes that a cell is first added to the model.
     * @param point 
     */
    @Override
    public void insertCellAtPoint(Point2D point) {
        
        Cell lastCell = graph.getModel().getLastCell();
        Point2D local = lastCell.sceneToLocal(point)
                .subtract(lastCell.getWidth() / 2.0, lastCell.getHeight() / 2.0);
                
        lastCell.relocate(local.getX(), local.getY());
    }
    
    
    
    
    
    
}
