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
import javafx.scene.text.Text;

public class RectangleCell extends Cell {
    
    public RectangleCell( String id) {
        super( id);

        Rectangle view = new Rectangle( 60,60);
        Text text = new Text(id);
        

        view.setStroke(Color.BLACK);
        view.setFill(Color.WHITE);
        
        setViews( new Node[]{view, text} );

    }
    
    

}