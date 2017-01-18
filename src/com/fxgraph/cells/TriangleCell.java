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
import javafx.scene.text.Text;

public class TriangleCell extends Cell  {

    public TriangleCell( String id) {
        super( id);

        Text text = new Text(id);
        
        double width = 32; //text.getLayoutBounds().getWidth();
        double height =  16; //text.getLayoutBounds().getHeight();
      
        Polygon view = new Polygon( 1.5*width, 0, 3*width, 4*height, 0, 4*height);
        
        view.setStroke(Color.RED);
        view.setFill(Color.RED);

        setViews( new Node[]{view, text});

    }

    
    

}