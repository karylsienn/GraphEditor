/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fxgraph.cells;


import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import com.fxgraph.graph.Cell;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class TriangleCell extends Cell  {

    private double width;
    private double height;
    private String cellId;
    private Text textId;
    private Color color;
    
    public TriangleCell( String id) {
        super( id);
        cellId = id;
        textId = new Text(id);
        
        double width = 32; //text.getLayoutBounds().getWidth();
        double height =  16; //text.getLayoutBounds().getHeight();
      
        Polygon view = new Polygon( 1.5*width, 0, 3*width, 4*height, 0, 4*height);
        
        view.setStroke(Color.RED);
        view.setFill(Color.RED);

        setViews( new Node[]{view, textId});

    }
    
    
    
    public void setCellColor(String color) {
        getChildren().stream().filter((node) -> (node instanceof Polygon))
                .forEach((node) -> {
            ((Polygon)node).setFill(Color.web(color));
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