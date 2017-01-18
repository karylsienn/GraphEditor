
package com.fxgraph.layout.base;

import javafx.geometry.Point2D;

public abstract class Layout {

    public abstract void execute();
    public abstract void insertCellAtPoint(Point2D point);

}
