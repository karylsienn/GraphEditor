/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fxgraph.cells;

import com.fxgraph.graph.CellType;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * FXML Controller class
 *
 * @author Karol
 */
public class DragIcon extends StackPane implements Initializable {

    
    public CellType cellType;
    public Shape shape;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public DragIcon() {
    
        FXMLLoader fxmlLoader = new FXMLLoader(
            getClass().getResource("DragIconXML.fxml"));

        fxmlLoader.setRoot(this); 
        fxmlLoader.setController(this);

        try { 
            fxmlLoader.load();

        } catch (IOException exception) {
             throw new RuntimeException(exception);
        }
    }
    
    public CellType getCellType() {
        return cellType;
    }
    
    public void setCellType(CellType cellType) throws Exception {
        this.cellType = cellType;
        
        switch(cellType) {
            case RECTANGLE :
                this.shape = new Rectangle(30, 30);
                
                break;
            case CIRCLE :
                this.shape = new Circle(30.0 / 2.0);
                break;
            case TRIANGLE :
                this.shape = new Polygon(30.0, 0.0, 0.0, 0.0, 30.0/2.0, 30.0);
                shape.setRotate(180.0);
                break;
            default :
               throw new Exception("Unsupported yet");
                     
        }
        shape.setFill(Color.GRAY);
        this.getChildren().add(shape);
        
    }
    
    public void relocateToPoint (Point2D p) {

    
        Point2D localCoords =  getParent().sceneToLocal(p);

        relocate (
            (int) (localCoords.getX() - 
                  (getBoundsInLocal().getWidth() / 2)),
            (int) (localCoords.getY() -
                  (getBoundsInLocal().getHeight() / 2))
        );
    }
    
    
    
    
}
