/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import com.fxgraph.cells.CircleCell;
import com.fxgraph.cells.DragIcon;
import com.fxgraph.editpanes.EditPaneController;
import com.fxgraph.graph.Cell;
import com.fxgraph.graph.CellType;
import com.fxgraph.graph.DragContainer;
import com.fxgraph.graph.Edge;
import com.fxgraph.graph.Graph;
import com.fxgraph.graph.Model;
import com.fxgraph.layout.base.Layout;
import com.fxgraph.layout.random.RandomLayout;
import com.fxgraph.layout.random.StaticLayout;
import com.jgraph.BellmanFord;
import com.jgraph.Dijkstra;
import com.jgraph.DijkstraTest;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author Karol
 */
public class MainSceneController extends BorderPane implements Initializable {

    /**
     * The left pane containing the icons, representations 
     * of elements that can be added to the graph.
     */
    @FXML AnchorPane left_pane;
    
    /**
     * The right pane which holds the ScrollPane containing the graph.
     */
    @FXML AnchorPane right_pane;
    
    /**
     * The menu bar with File, Edit, Algorithms and Help options.
     */
    @FXML MenuBar menu_bar;
    
    /**
     * The split Pane containing the the right_pane and left_pane.
     */
    @FXML SplitPane base_pane;
    
    /**
     * The pane for cell editing.
     */
    @FXML AnchorPane edit_pane;
    
    /**
     * Graph to be showed.
     */
    private Graph graph;
    
    /**
     * The model of the graph, manipulating 
     * the cells and edges adding and retrieiving.
     */
    private Model model;
    
    /**
     * The Layout to be executed.
     */
    private Layout layout;
    
    /**
     * The DragIcon that is the image of the 
     * icon dragged between right and left pane.
     */
    private DragIcon dragOverIcon;
    
    /**
     * The handler for dragging the icon over this BorderPane.
     */
    private EventHandler iconDragOverRoot;
    
    /**
     * Handler for dropping the icon on the right_pane.
     */
    private EventHandler iconDragDropped;
    
    /**
     * Hanlder for dragging the icon over the right_pane.
     */
    private EventHandler iconDragOverRightPane;
    
    /**
     * 
     * The gridpane that holds the shapes for vertices.
     */
    @FXML private GridPane vertices_gridpane; 
  
    private static final String NEW_NODE_NAME = "Node";
    
    private static int NEW_NODE_COUNTER;
   

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            
            // Fill the left_pane with dragIcons.
            addRightPaneIcons(); 
                        
            // Build drag handlers for handling the 
            // drags and drops between left and right pane.
            buildDragHandlers();
            
            //setOnCellInsertedEditPane();
            
            // Create a dragOverIcon - silent for now
            dragOverIcon = new DragIcon();
            dragOverIcon.setVisible(false);
            dragOverIcon.setOpacity(0.65);
            this.getChildren().add(dragOverIcon);
            
            // Create a graph that will be showed.
            graph = new Graph();
            
            // Get a pane that shows a graph. Place it so that it fits the whole right_pane.
            ScrollPane graphPane = graph.getScrollPane();
            right_pane.getChildren().add(graphPane);
            right_pane.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            setAnchorSides(graphPane);            
            
            // Get the graph model, to be able to manipulate the cells and edges.
            model = graph.getModel();
            
            
            
            // add the components
            //addGraphComponents();
            
            // Create a layout of this graph.
            //layout = new RandomLayout(graph);
            //layout.execute();
            
            layout = new StaticLayout(graph);
            /*((StaticLayout) layout).setInitialPositions(
                    new ArrayList<>(
                            Arrays.asList(
                            new Point2D(10.5, 315.0),
                            new Point2D(4.5, 434.7),
                            new Point2D(435.6, 723.46),
                            new Point2D(133.4, 74.43),
                            new Point2D(4.5, 210.4)
                    ))); */
            layout.execute();

        } catch (Exception ex) {
            Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
    private void addRightPaneIcons() throws Exception {
        
        // Start filling the gridPane with icons, from (0, 0).
        int rowNum = 0, colNum = 0;

        // We are filling with each type.
        for(CellType cellType : CellType.values()) {

            DragIcon dragIcon = new DragIcon();
            // Add drag detection to each icon.
            addDragDetection(dragIcon);
            dragIcon.setCellType(cellType);
            vertices_gridpane.add(dragIcon, rowNum++, colNum);

            if(rowNum == 3) { rowNum = 0; colNum += 1; }

        }
            
    }
    
    /**
     * Set the ScrollPane to fill the whole AnchorPane.
     * In this case, the AnchorPane is the right_pane that will hold
     * ZoomableScrollPane for showing the graph.
     * @param pane the pane to be filled.
     */
    private void setAnchorSides(ScrollPane pane) {
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
    }
    
    /**
     * Add graph components to the model.
     * @throws Exception if there is no such CellType
     */
    private void addGraphComponents() throws Exception {

        System.out.println("Jestem tutaj.");
        graph.beginUpdate();

        model.addCell("Cell A", CellType.TRIANGLE);
        model.addCell("Cell B", CellType.CIRCLE);
        model.addCell("Cell C", CellType.TRIANGLE);
        model.addCell("Cell D", CellType.CIRCLE);
        model.addCell("Cell E", CellType.CIRCLE);
        
        model.addEdge("Cell A", "Cell B", 10.0);
        model.addEdge("Cell A", "Cell C", 10.0);
        model.addEdge("Cell B", "Cell C", 10.0);
        model.addEdge("Cell D", "Cell B", 3.4);
        model.addEdge("Cell E", "Cell D", 4.5);
        
        graph.endUpdate();
        
    } 
    
    /**
     * Add drag detection to the given node.
     * @param node 
     */
    private void addDragDetection(Node node) {
        
        // uses lambda instead of anonymous class.
        node.setOnDragDetected((MouseEvent me) -> {
            // along with drag detection we set all other handlers.
            // we are now sure that they are added.
            base_pane.setOnDragOver(iconDragOverRoot);
            right_pane.setOnDragOver(iconDragOverRightPane);
            right_pane.setOnDragDropped(iconDragDropped);
            
            System.out.println("Drag detected");
            DragIcon source = (DragIcon) me.getSource();
            
            source.setVisible(true);
            
            try {
                dragOverIcon.setCellType(source.getCellType());
            } catch (Exception ex) {
                Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            source.relocateToPoint(new Point2D (me.getSceneX(), me.getSceneY()));
            
            ClipboardContent content = new ClipboardContent();
            DragContainer container = new DragContainer();
            
            container.addData("type", dragOverIcon.getCellType().toString());
            content.put(DragContainer.ADD_NODE, container);
            
            source.startDragAndDrop (TransferMode.ANY).setContent(content);
            source.setVisible(true);
            
            me.consume();
        });
   
        
    }
    
    
    /**
     * Built drag handlers for dragging and dropping the icons into the editing pane.
     */
    public void buildDragHandlers() {
        
        iconDragOverRoot = new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                
                Point2D p = right_pane.sceneToLocal(event.getSceneX(), event.getSceneY());

                if (!right_pane.boundsInLocalProperty().get().contains(p)) {
                    dragOverIcon.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                    return;
                }

                event.consume();
            }
        
        };
        
        iconDragOverRightPane = new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                event.acceptTransferModes(TransferMode.ANY);
 
                dragOverIcon.relocateToPoint(
                    new Point2D(event.getSceneX(), event.getSceneY())
                );

                event.consume();
            }
            
        };
        
        iconDragDropped = new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                DragContainer container =
                (DragContainer) event.getDragboard().getContent(DragContainer.ADD_NODE);

                container.addData("scene_coords",
                    new Point2D(event.getSceneX(), event.getSceneY()));

                ClipboardContent content = new ClipboardContent();
                content.put(DragContainer.ADD_NODE, container);

                event.getDragboard().setContent(content);
                event.setDropCompleted(true);
                
                event.consume();
                
            }
            
        };
        
        base_pane.setOnDragDone (new EventHandler<DragEvent>  () {

            @Override
            public void handle (DragEvent event) {
            System.out.println("DragDone");
            right_pane.removeEventHandler(DragEvent.DRAG_OVER, iconDragOverRightPane);
            right_pane.removeEventHandler(DragEvent.DRAG_DROPPED, iconDragDropped);
            base_pane.removeEventHandler(DragEvent.DRAG_OVER, iconDragOverRoot);
           
            dragOverIcon.setVisible(false);

            DragContainer container =
                (DragContainer) event.getDragboard().getContent(DragContainer.ADD_NODE);
            
            if (container != null) {
                if (container.getValue("scene_coords") != null) {
                    
                    NEW_NODE_COUNTER += 1;
                    
                    Point2D cursorPoint = (Point2D) container.getValue("scene_coords");
                    
                    System.out.println(new Point2D(cursorPoint.getX(), cursorPoint.getY()));
                    
                    CellType ct = CellType.valueOf((String) container.getValue("type"));
                    

                    graph.beginUpdate();
                    model.addCell(NEW_NODE_NAME + NEW_NODE_COUNTER, ct);
                    graph.endUpdate();
                    layout.insertCellAtPoint(cursorPoint);
                    layout.execute();
                     
                   
                }
            }
            
            event.consume();
            
            }
            
        });

    }
    
    @FXML public void handleDijkstra() {
        Cell algoSource = (Cell) graph.getMouseGestures().getAlgoSource();
        Cell algoTarget = (Cell) graph.getMouseGestures().getAlgoTarget();
        
        if(algoSource!=null & algoTarget!=null & algoSource != algoTarget) {
            ArrayList<String[]> path = Dijkstra.getShortestPath(graph, algoSource, algoTarget);
            for(String[] p : path) {
                Edge edge = graph.getModel().getEdge(p[0], p[1]);
                System.out.println(edge.getSource() + " " + edge.getTarget());
                graph.beginUpdate();
                edge.getLine().setFill(Color.BLUE);
                edge.getLine().setStroke(Color.AQUA);
                graph.endUpdate();
                layout.execute();
            }
        }
    }
    
    @FXML public void AlgorithmClearAction() {
        for(Edge edge : graph.getModel().getAllEdges()) {
            edge.getLine().setStroke(Color.BLACK);
        }
    }
    
    @FXML public void handleBellmanFord() {
        Cell algoSource = (Cell) graph.getMouseGestures().getAlgoSource();
        Cell algoTarget = (Cell) graph.getMouseGestures().getAlgoTarget();
        
        if(algoSource!=null & algoTarget!=null & algoSource != algoTarget) {
            ArrayList<String[]> path = BellmanFord.getShortestPath(graph, algoSource, algoTarget);
            for(String[] p : path) {
                Edge edge = graph.getModel().getEdge(p[0], p[1]);
                System.out.println(edge.getSource() + " " + edge.getTarget());
                graph.beginUpdate();
                edge.getLine().setFill(Color.BLUE);
                edge.getLine().setStroke(Color.RED);
                graph.endUpdate();
                layout.execute();
            }
            
            System.out.println("The cost is:" + BellmanFord.getCostToVertex(graph, algoSource, algoTarget));
        }
    }
    
    @FXML public void onDelete() {
        NEW_NODE_COUNTER = 0;
        graph.beginUpdate();
        graph.getModel().clear();
        right_pane.getChildren().remove(graph.getScrollPane());
        graph.endUpdate();
        
        graph = new Graph();
        ScrollPane graphPane;
        right_pane.getChildren().add(graphPane = graph.getScrollPane());
        right_pane.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        setAnchorSides(graphPane);
        model = graph.getModel();
        
        layout = new StaticLayout(graph);
        layout.execute();
    }
    
    @FXML public void onClose() {
        ((Stage)this.base_pane.getScene().getWindow()).close();
    }
}
