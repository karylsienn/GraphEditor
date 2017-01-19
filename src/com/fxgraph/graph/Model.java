/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fxgraph.graph;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fxgraph.cells.TriangleCell;
import com.fxgraph.cells.RectangleCell;
import com.fxgraph.cells.CircleCell;
import static java.util.stream.Collectors.toList;

public class Model {

    Cell graphParent;

    List<Cell> allCells;
    List<Cell> addedCells;
    List<Cell> removedCells;

    List<Edge> allEdges;
    List<Edge> addedEdges;
    List<Edge> removedEdges;

    Map<String,Cell> cellMap; // <id,cell>

    public Model() {

         graphParent = new Cell( "_ROOT_");

         // clear model, create lists
         clear();
    }

    public final void clear() {

        allCells = new ArrayList<>();
        addedCells = new ArrayList<>();
        removedCells = new ArrayList<>();

        allEdges = new ArrayList<>();
        addedEdges = new ArrayList<>();
        removedEdges = new ArrayList<>();

        cellMap = new HashMap<>(); // <id,cell>

    }

    public void clearAddedLists() {
        addedCells.clear();
        addedEdges.clear();
    }

    public List<Cell> getAddedCells() {
        return addedCells;
    }

    public List<Cell> getRemovedCells() {
        return removedCells;
    }

    public List<Cell> getAllCells() {
        return allCells;
    }

    public List<Edge> getAddedEdges() {
        return addedEdges;
    }

    public List<Edge> getRemovedEdges() {
        return removedEdges;
    }

    public List<Edge> getAllEdges() {
        return allEdges;
    }
    
    /**
     * Assumes that is called after graph.endUpdate();
     * @return the last added cell.
     */
    public Cell getLastCell() {
        return allCells.get(allCells.size() - 1);
    }
    
    

    public void addCell(String id, CellType type) {

        Cell cell;
        
        switch (type) {

        case RECTANGLE:
            cell = new RectangleCell(id);
            addCell(cell);
            break;

        case TRIANGLE:
            cell = new TriangleCell(id);
            addCell(cell);
            break;
            
        case CIRCLE:
            cell = new CircleCell(id);
            addCell(cell);
            break;

        default:
            throw new UnsupportedOperationException("Unsupported type: " + type);
        }
    }
    
   public Edge getEdge(String sourceId, String targetId) {
       return allEdges.stream().filter((Edge edge) -> (
           edge.getSource().getCellId().equals(sourceId) &
           edge.getTarget().getCellId().equals(targetId)
       ) ).findAny().get();
       
   }

    

    public void addCell( Cell cell) {

        addedCells.add(cell);

        cellMap.put( cell.getCellId(), cell);

    }

    public void addEdge( String sourceId, String targetId, double weight) {

        Cell sourceCell = cellMap.get( sourceId);
        Cell targetCell = cellMap.get( targetId);

        if(sourceCell != targetCell & 
                allEdges.stream()
                        .filter((Edge edge) ->
                                (edge.getSource() == sourceCell)
                                        && (edge.getTarget() == targetCell))
                        .collect(toList()).isEmpty() ) 
        {
            
                Edge edge = new Edge( sourceCell, targetCell, weight);

            addedEdges.add( edge);
        }
        
        

    }
    
    public void removeCell(Cell cell) {
        removedCells.add(cell);
        allEdges.forEach(edge -> {
            if(edge.getSource() == cell || edge.getTarget() == cell) {
                removedEdges.add(edge);
            }
        });
    }
    
    


    /**
     * Attach all cells which don't have a parent to graphParent 
     * @param cellList
     */
    public void attachOrphansToGraphParent( List<Cell> cellList) {

        cellList.stream().filter((Cell cell) -> {
            return cell.getCellParents().isEmpty();
        }).forEach(graphParent::addCellChild);

    }

    /**
     * Remove the graphParent reference if it is set
     * @param cellList
     */
    public void disconnectFromGraphParent( List<Cell> cellList) {

        cellList.stream().forEach(graphParent::removeCellChild);
    }

    public void merge() {

        // cells
        allCells.addAll( addedCells);
        allCells.removeAll( removedCells);

        addedCells.clear();
        removedCells.clear();

        // edges
        allEdges.addAll( addedEdges);
        allEdges.removeAll( removedEdges);

        addedEdges.clear();
        removedEdges.clear();

    }
    
    public void renameCell(String sourceName, String targetName) {
        Cell cell = cellMap.get(sourceName);
        cellMap.keySet().remove(sourceName);
        cellMap.put(targetName, cell);
    }

    
}
