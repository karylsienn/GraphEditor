/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fxgraph.graph;

import com.fxgraph.editpanes.EdgeEditPaneController;
import com.fxgraph.editpanes.EditPaneController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class MouseGestures {

    final DragContext dragContext = new DragContext();
    
    private boolean isSelected = false;

    Graph graph;
    
    String edgeSourceId;
    String edgeTargetId;
    
    private Node algoSource;
    public Node getAlgoSource() {return algoSource; }
    private Node algoTarget;
    public Node getAlgoTarget() {return algoTarget; }
    
    Line dragLine;
    
    private ContextMenu contextCellMenu;

    public MouseGestures( Graph graph) {
        this.graph = graph;
        makeContextMenu();
    }

    public void makeDraggable( final Node node) {

        node.setOnDragDetected(onDragDetectedEventHandler);
        node.setOnMouseDragReleased(onDragReleasedEventHandler);
        
        node.setOnMousePressed(onMousePressedEventHandler);
        node.setOnMouseDragged(onMouseDraggedEventHandler);
        node.setOnMouseReleased(onMouseReleasedEventHandler);
        
    }
    
    public void makeSelectable( final Node node) {
        
        node.setOnMouseClicked(onMouseClickedEventHandler);
    }
    
    EventHandler<MouseEvent> onDragDetectedEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Node node = (Node) event.getSource();
            if(event.getButton().equals(MouseButton.SECONDARY)) {
                
                System.out.println(String.valueOf( ((Cell)node).getCellId() ));
                edgeSourceId = String.valueOf( ((Cell)node).getCellId() );
                ((Node) event.getSource()).startFullDrag();
            } 
        }
        
    };
    
    EventHandler<MouseDragEvent> onDragReleasedEventHandler = new EventHandler<MouseDragEvent>() {
        @Override
        public void handle(MouseDragEvent event) {
            try {
                System.out.println("Dreag released");
                Node node = (Node) event.getSource();
                System.out.println(String.valueOf( ((Cell)node).getCellId()));
                edgeTargetId = String.valueOf( ((Cell)node).getCellId());
                
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/fxgraph/editpanes/EdgeEditPane.fxml"));
                Parent editPane = fxmlLoader.load();
                EdgeEditPaneController epc = fxmlLoader.getController();
                
                Stage secondStage = new Stage();        
                Scene scene = new Scene(editPane);

                secondStage.setScene(scene);
                secondStage.setAlwaysOnTop(true);
                secondStage.showAndWait();
                
                
                graph.beginUpdate();
                graph.getModel().addEdge(edgeSourceId, edgeTargetId, epc.getWeight());
                graph.endUpdate();
                
                graph.getScrollPane();
            } catch (IOException ex) {
                Logger.getLogger(MouseGestures.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    };
    
    EventHandler<MouseEvent> onMouseClickedEventHandler = new EventHandler<MouseEvent>() {
        
        @Override
        public void handle(MouseEvent event) {
            Node source = (Node) event.getSource();
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(event.getClickCount() == 2){
                    if(source instanceof Cell) {
                        Cell cell = (Cell) source;
                        if(cell.isSelected()) {
                            //System.out.println("My screen coords are " + event.getScreenX() + " " + event.getScreenY());
                            source.setStyle("-fx-effect: dropshadow(three-pass-box, grey, 0, 0, 0, 0);");
                            cell.setSelected(false);
                        } else {
                            source.setStyle("-fx-effect: dropshadow(three-pass-box, grey, 2, 2, 0, 0);");
                            cell.setSelected(true);
                        }
                    }
                }
            }
            if(event.getButton().equals(MouseButton.SECONDARY)) {
                if(source instanceof Cell) {
                    if(((Cell) source).isSelected()) {
                      contextCellMenu.show(source, event.getScreenX(), event.getScreenY());
                      setOnMenuAction(source);
                    }                    
                }

            }
            
        }
        
    };

    EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            
            Node node = (Node) event.getSource();
            if( event.getButton().equals(MouseButton.PRIMARY)) {
                

                double scale = graph.getScale();

                dragContext.x = node.getBoundsInParent().getMinX() * scale - event.getScreenX();
                dragContext.y = node.getBoundsInParent().getMinY()  * scale - event.getScreenY();

            } 
            
        }
    };

    EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

            Node node = (Node) event.getSource();
            if(event.getButton().equals(MouseButton.PRIMARY)) {
                double offsetX = event.getScreenX() + dragContext.x;
                double offsetY = event.getScreenY() + dragContext.y;

            // adjust the offset in case we are zoomed
                double scale = graph.getScale();
            
                offsetX /= scale;
                offsetY /= scale;

                node.relocate(offsetX, offsetY);

            }
            
        }
    };
    
    

    EventHandler<MouseEvent> onMouseReleasedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            Node node = (Node) event.getTarget();

        }
    };
    

    private void makeContextMenu() {
        
        contextCellMenu = new ContextMenu();
        MenuItem asSource = new MenuItem("Mark as Source");
        MenuItem asTarget = new MenuItem("Mark as Target");
        MenuItem edit = new MenuItem("Edit");
        MenuItem delete = new MenuItem("Delete");
        
        contextCellMenu.getItems().addAll(asSource, asTarget, edit, delete);
        
        
    }
    
    private void setOnMenuAction(Node source) {
        // as source
        contextCellMenu.getItems().get(0).setOnAction(
                (ActionEvent event) -> 
                    algoSource = source
        );
        // as target
        contextCellMenu.getItems().get(1).setOnAction(
                (ActionEvent event) -> 
                    algoTarget = source
        );
        
        contextCellMenu.getItems().get(2).setOnAction(
                (ActionEvent event) -> {
                    
                    
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/com/fxgraph/editpanes/EditPane.fxml"));
                        Parent editPane = fxmlLoader.load();
                        EditPaneController epc = fxmlLoader.getController();
                        System.out.println(editPane);
                        System.out.println(source);
                        epc.setGraph(graph);
                        epc.setCell((Cell) source);
                        
                        Stage secondStage = new Stage();
                        
                        Scene scene = new Scene(editPane);

                        secondStage.setScene(scene);
                        secondStage.setAlwaysOnTop(true);
                        secondStage.showAndWait();
                        
                        
                        
                    } catch (IOException ex) {
                        Logger.getLogger(MouseGestures.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
        );
        
        contextCellMenu.getItems().get(3).setOnAction(
                (ActionEvent event) -> {
                    graph.beginUpdate();
                    graph.getModel().removeCell((Cell)source);
                    graph.endUpdate();
                }
                        
        );
        
    } 


    
    

    class DragContext {

        double x;
        double y;

    }
}
