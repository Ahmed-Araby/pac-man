package org.example.ghostmode;

import org.example.constant.DirectionsE;
import org.example.entity.CanvasCoordinate;

public abstract class Ghost {

    public abstract double getCanvasCol();
    public abstract double getCanvasRow();
    public abstract void setCanvasCol(double pixel);
    public abstract void setCanvasRow(double pixel);

    public abstract CanvasCoordinate getPacManCanvasCord();

    public abstract DirectionsE getDirectionsE();
    public abstract void setDirectionsE(DirectionsE directionsE);

    public CanvasCoordinate getTopLeftCorner() {
        return new CanvasCoordinate(getCanvasRow(), getCanvasCol());
    }
}
