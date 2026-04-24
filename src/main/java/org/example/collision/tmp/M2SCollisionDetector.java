package org.example.collision.tmp;


import org.example.constant.DimensionsC;
import org.example.entity.CanvasRect;
import org.example.event.collision.CollisionDetectionEvent;
import org.example.event.collision.M2SCollisionDetectionEvent;

public class M2SCollisionDetector implements CollisionDetector {

    @Override
    public boolean detect(CollisionDetectionEvent event) {
        return detect((M2SCollisionDetectionEvent) event);
    }

    private boolean detect(M2SCollisionDetectionEvent event) {
        return false;
    }

    private boolean outOfCanvas(CanvasRect rect) {
        return rect.topEdgeRow() < 0 || rect.bottomEdgeRow() >= DimensionsC.CANVAS_HEIGHT_PIXELS
                || rect.leftEdgeCol() < 0 || rect.rightEdgeCol() >= DimensionsC.CANVAS_WIDTH_PIXELS;
    }
}
