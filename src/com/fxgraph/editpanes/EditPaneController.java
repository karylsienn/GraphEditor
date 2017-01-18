/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fxgraph.editpanes;

import application.MainSceneController;
import com.fxgraph.cells.CircleCell;
import com.fxgraph.cells.RectangleCell;
import com.fxgraph.cells.TriangleCell;
import com.fxgraph.graph.Cell;
import com.fxgraph.graph.CellType;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Karol
 */
public class EditPaneController implements Initializable {

    @FXML private AnchorPane right_pane;
    @FXML private TextField name_txt_field;
    @FXML private TextField height_txt_field;
    @FXML private TextField width_txt_field;
    
    private CellType cellType;
    private Cell cell;
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //msc = new MainSceneController();
        System.out.println("Initialize");
        //msc.setEditPaneController(this);
        
        //cell.setVisible(true);
        
        //right_pane.getChildren().add(cell);
       
    }
    
    public final void setCellType(CellType cellType) {
        this.cellType = cellType;
    }
    
    
    public final void createCell() {
        System.out.println(cellType);
        switch(cellType) {
            case CIRCLE:
                cell = new CircleCell("");
                break;
            case RECTANGLE :
                cell = new RectangleCell("");
                break;
            case TRIANGLE :
                cell = new TriangleCell("");
                break;
            default: 
                break;
        }
    }
    
    public void showShape() {
        cell.setVisible(true);
        right_pane.getChildren().add(cell);
        //System.out.println(String.valueOf(new Point2D(right_pane.getBoundsInLocal().getWidth(), right_pane.getHeight())));
        Bounds localBounds = right_pane.getBoundsInParent();
        
        cell.relocate(
                localBounds.getWidth() + cell.getWidth() , 
                localBounds.getHeight() + cell.getHeight()
        );
        
    }
    
    
}
