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
import javafx.scene.text.Text;

/**
 *
 * @author Karol
 */
public class CircleCell extends Cell  {
    
    public CircleCell( String id) {
        super( id);

        Circle view = new Circle();
        Text text = new Text(id);
        
        view.setStroke(Color.BLACK);
        view.setFill(Color.GREEN);
        
        view.setRadius(30);
        //view.setRadius(text.getLayoutBounds().getWidth());
        
        setViews( new Node[]{view, text} );

    }


    
}
