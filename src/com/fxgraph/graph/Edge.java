/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fxgraph.graph;

/**
 *
 * @author Karol
**/

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;


public class Edge extends Group {

    protected Cell source;
    protected Cell target;
    protected double weight;

    Line line;
    Label weightLabel;

    public Edge(Cell source, Cell target, double weight) {

        this.weight = weight;
        this.source = source;
        this.target = target;

        source.addCellChild(target);
        target.addCellParent(source);

        line = new Line();

        line.startXProperty().bind( source.layoutXProperty().add(source.getBoundsInParent().getWidth() / 2.0));
        line.startYProperty().bind( source.layoutYProperty().add(source.getBoundsInParent().getHeight() / 2.0));

        line.endXProperty().bind( target.layoutXProperty().add( target.getBoundsInParent().getWidth() / 2.0));
        line.endYProperty().bind( target.layoutYProperty().add( target.getBoundsInParent().getHeight() / 2.0));
        
        
        weightLabel = new Label(String.valueOf(weight));
        
        weightLabel.layoutXProperty().bind(line.endXProperty().add(line.startXProperty()).divide(2));
        weightLabel.layoutYProperty().bind(line.endYProperty().add(line.startYProperty()).divide(2));
        
        getChildren().addAll( line, weightLabel);

    }

    public Cell getSource() {
        return source;
    }

    public Cell getTarget() {
        return target;
    }
    
    public double getWeigth() {
        return weight;
    }

}
