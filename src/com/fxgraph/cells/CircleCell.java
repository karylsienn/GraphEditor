/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fxgraph.cells;


import com.fxgraph.graph.Cell;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;

/**
 *
 * @author Karol
 */
public class CircleCell extends Cell  {
    
    private String id;
    private Text textId;
    private Color color;
    private double height;
    private double width; 
    
    
    public CircleCell( String id) {
        super( id);

        this.id = id;
        
        Circle view = new Circle();
        textId = new Text(id);
        
        view.setStroke(Color.BLACK);
        view.setFill(Color.GREEN);
        
        view.setRadius(40);
        //view.setRadius(text.getLayoutBounds().getWidth());
        
        setViews( new Node[]{view, textId} );

    }
    
    @Override
    public void setCellWidth(double width) {
        this.width = width;
        getChildren().stream().filter((node) -> (node instanceof Circle))
                .forEach((node) -> {
            ((Circle) node).setRadius(width);
        });
        
    }
    
    @Override
    public void setCellHeight(double height) {
        this.height = height;
        // nothing
    }
    
    public void setCellColor(String color) {
        getChildren().stream().filter((node) -> (node instanceof Circle))
                .forEach((node) -> {
            ((Circle)node).setFill(Color.web(color));
        });
    }
    
    public void setCellText(String id) {
        this.id = id;
        getChildren().remove(textId);
        setView(textId = new Text(id));
    }
    
    


    
}
