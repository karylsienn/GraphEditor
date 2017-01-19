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
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author Karol
 */
public class EditPaneController implements Initializable {

    @FXML private BorderPane base_pane;
    @FXML private AnchorPane right_pane;
    @FXML private TextField name_txt_field;
    @FXML private Slider height_slider;
    @FXML private Slider width_slider;
    @FXML private ColorPicker color_picker;
    @FXML private Button ok_button;
    @FXML private Button cancel_button;
    
    private static int countInit;
    
    private CellType cellType;
    private Cell cell;
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        countInit += 1;
  
        
        System.out.println("Initialize");
        
        height_slider.setMin(Cell.MIN_HEIGHT);
        height_slider.setMax(Cell.MAX_HEIGHT);
        height_slider.setBlockIncrement(0.1);
        
        width_slider.setMin(Cell.MIN_WIDTH);
        width_slider.setMax(Cell.MAX_WIDTH);
        width_slider.setBlockIncrement(0.1);
        
        
       
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
        
       // height_slider.valueProperty().addListener(
         //       (observable, oldValue, newValue) -> cell.setHeight(newValue.doubleValue()));
        //width_slider.valueProperty().addListener(
        //        (observable, oldValue, newValue) -> cell.setWidth(newValue.doubleValue()));
        
       // color_picker.valueProperty().addListener((observable, oldValue, newValue) 
       //         -> cell.setColor(newValue.toString()));
        
    }
    
    public void onOKAction() {
        cell.setCellText(name_txt_field.getText().isEmpty() ? 
                "Node" + String.valueOf(countInit) : 
                name_txt_field.getText());
        
        Stage stage = (Stage) base_pane.getScene().getWindow();
        stage.close();
    }
    
    public void onCancelAction() {
        cell.setCellText("Node" + String.valueOf(countInit));
        Stage stage = (Stage) base_pane.getScene().getWindow();
        stage.close();
    }
    
    public Cell getCell() {
        return cell;
    }

    
    
    
}
