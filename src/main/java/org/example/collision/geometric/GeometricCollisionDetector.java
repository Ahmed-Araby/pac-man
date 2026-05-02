package org.example.collision.geometric;

import org.example.constant.DimensionsC;
import org.example.entity.CanvasCoordinate;
import org.example.entity.CanvasRect;

public interface GeometricCollisionDetector {

    boolean collide(CanvasCoordinate topLeftCorner1, CanvasCoordinate topLeftCorner2);

    default boolean outOfCanvas(CanvasRect rect) {
        return rect.topEdgeRow() < 0 || rect.bottomEdgeRow() >= DimensionsC.CANVAS_HEIGHT_PIXELS
                || rect.leftEdgeCol() < 0 || rect.rightEdgeCol() >= DimensionsC.CANVAS_WIDTH_PIXELS;

    }
}
