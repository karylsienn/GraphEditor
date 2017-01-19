/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fxgraph.editpanes;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Karol
 */
public class EdgeEditPaneController implements Initializable {

    @FXML private TextField weight_text_field;
    private double weight;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        weight_text_field.setOnKeyPressed((KeyEvent ke) -> 
        {
            if(ke.getCode().equals(KeyCode.ENTER)) {
                System.out.println("Ok");
               String content = weight_text_field.getText().isEmpty() ? 
                       String.valueOf(0.0) : 
                       weight_text_field.getText();
               
               weight = Double.parseDouble(content);
               
               Stage stage = (Stage) weight_text_field.getScene().getWindow();
               stage.close();
            }
        });
    }

    public double getWeight() {
        return weight;
    }
    
}
