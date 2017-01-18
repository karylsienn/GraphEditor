/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fxgraph.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.input.DataFormat;
import javafx.util.Pair;

/**
 *
 * @author Karol
 */
public class DragContainer implements Serializable {
    
    private static final long serialVersionUID = -1458406119115196098L;
    
    private final List <Pair<String, Object> > mDataPairs = new ArrayList < > ();
    
    /**
     * 
     */
    public static final DataFormat DRAG_NODE = 
            new DataFormat("application.DraggableNode.drag");
    
    /**
     *
     */
    public static final DataFormat ADD_NODE = 
            new DataFormat("com.fxgraph.cells.DragIcon.add");
    
    /**
     * 
     */
    public static final DataFormat ADD_LINK =
            new DataFormat("application.NodeLink.add");
    

    
    /**
     *
     */
    public static final DataFormat BINDIND = 
            new DataFormat("com.buddyware.treefrog.filesystem.view.FileSystemBinding");
    
    /**
     *
     */
    public static final DataFormat NODE =
            new DataFormat("com.buddyware.treefrog.filesystem.view.FileSystemNode");
    
    public DragContainer () {
    }
    
    public void addData (String key, Object value) {
        mDataPairs.add( new Pair<>(key, value));        
    }
    
    public  Object getValue (String key) {
 
        for (Pair<String, Object> data : mDataPairs) {
            if (data.getKey().equals(key))
                return  data.getValue();
        }
 
        return null;
    }
    
    public List <Pair<String, Object> > getData () { return mDataPairs; }
}


