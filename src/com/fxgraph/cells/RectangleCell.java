/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fxgraph.cells;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import com.fxgraph.graph.Cell;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class RectangleCell extends Cell {
    
    private double width;
    private double height;
    private String cellId;
    private Text textId;
    private Color color;
    public RectangleCell( String id) {
        super( id);
        cellId = id;
        Rectangle view = new Rectangle( 60,60);
        textId = new Text(id);
        

        view.setStroke(Color.BLACK);
        view.setFill(Color.WHITE);
        
        setViews( new Node[]{view, textId} );

    }
    
    @Override
    public void setCellWidth(double width) {
        this.width = width;
        getChildren().stream().filter((node) -> (node instanceof Rectangle))
                .forEach((node) -> {
            ( (Rectangle) node).setWidth(width);
        });
        
    }
    
    @Override
    public void setCellHeight(double height) {
        this.height = height;
        getChildren().stream().filter((node) -> (node instanceof Rectangle))
                .forEach((node) -> {
            ( (Rectangle) node).setHeight(height);
        });
    }
    
    public void setCellColor(String color) {
        getChildren().stream().filter((node) -> (node instanceof Rectangle))
                .forEach((node) -> {
            ((Rectangle)node).setFill(Color.web(color));
        });
    }
    
    public void setCellId(String id) {
        this.cellId = id;
        getChildren().remove(textId);
        setView(textId = new Text(id));
    }
    
    
    public double getCellHeight() {
        return this.height;
    }
    
    public double getCellWidth() {
        return this.width;
    }
    
    public Color getCellColor() {
        return this.color;
    }
    
    public String getCellId() {
        return this.cellId;
    }
    
    

}