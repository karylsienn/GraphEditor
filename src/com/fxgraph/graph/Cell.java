/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fxgraph.graph;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;


public class Cell extends StackPane  {

    String cellId;
 
    List<Cell> children = new ArrayList<>();
    
    List<Cell> parents = new ArrayList<>();

    Node view;
    
    Node[] views;
    
    boolean selected = false;
    
    

    public Cell() {
        super();
    }
    
    public Cell(String cellId) {
        this.cellId = cellId;
    }

    public void addCellChild(Cell cell) {
        children.add(cell);
    }

    public List<Cell> getCellChildren() {
        return children;
    }

    public void addCellParent(Cell cell) {
        parents.add(cell);
    }

    public List<Cell> getCellParents() {
        return parents;
    }

    public void removeCellChild(Cell cell) {
        children.remove(cell);
    }

    public void setView(Node view) {

        this.view = view;
        getChildren().add(view);

    }
    
    public void setViews(Node[] nodes) {
        this.views = nodes;
        getChildren().addAll(nodes);
    }

    public Node getView() {
        return this.view;
    }

    public String getCellId() {
        return cellId;
    }
    
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    public boolean isSelected() {
        return selected;
    }
    
    
    public static void Marshalling(Cell cell) throws JAXBException {
        JAXBContext jcb = JAXBContext.newInstance(Cell.class);
        Marshaller mc = jcb.createMarshaller();
        mc.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        mc.marshal(cell, new File("cell.xml"));
        
    }
    
    @Override
    public String toString() {
        return this.cellId;
    }


    
}