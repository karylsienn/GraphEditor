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
import com.fxgraph.graph.Graph;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Karol
 */
public class EditPaneController implements Initializable {


    @FXML private TextField name_txt_field;
    @FXML private Slider height_slider;
    @FXML private Slider width_slider;
    @FXML private ColorPicker color_picker;
    
    private Cell cell;
    private Graph g;
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        System.out.println("Initialize");
        
        height_slider.setMin(Cell.MIN_HEIGHT);
        height_slider.setMax(Cell.MAX_HEIGHT);
        height_slider.setBlockIncrement(0.1);
        
        width_slider.setMin(Cell.MIN_WIDTH);
        width_slider.setMax(Cell.MAX_WIDTH);
        width_slider.setBlockIncrement(0.1);

       
    }
    
    public void setGraph(Graph g) {
        this.g = g;
    }
    
    public void setCell(Cell cell) {
        this.cell = cell;
        
        height_slider.setValue(cell.getHeight());
        width_slider.setValue(cell.getWidth());
        color_picker.setValue(cell.getCellColor());
        name_txt_field.setText(cell.getCellId());
        
        height_slider.valueProperty().addListener(
              (observable, oldValue, newValue) -> cell.setCellHeight(newValue.doubleValue()));
        width_slider.valueProperty().addListener(
              (observable, oldValue, newValue) -> cell.setCellWidth(newValue.doubleValue()));
        color_picker.valueProperty().addListener((observable, oldValue, newValue) 
               -> cell.setCellColor(newValue.toString()));
        
        
    }
    
    public Cell getCell() {
        return cell;       
    }
    
    public void applyButton() {
        
        g.beginUpdate();
        g.getModel().renameCell(cell.getCellId(), name_txt_field.getText());
        g.endUpdate();
        cell.setCellId(name_txt_field.getText());
    }

    
    
    
}
