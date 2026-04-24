package org.example.collision.tmp;


import org.example.event.collision.CollisionDetectionEvent;
import org.example.event.collision.M2MCollisionDetectionEvent;

public class M2MCollisionDetector implements CollisionDetector {

    @Override
    public boolean detect(CollisionDetectionEvent event) {
        return detect((M2MCollisionDetectionEvent) event);
    }

    private boolean detect(M2MCollisionDetectionEvent event) {
        return false;
    private boolean outOfCanvas(CanvasRect rect) {
        return rect.topEdgeRow() < 0 || rect.bottomEdgeRow() >= DimensionsC.CANVAS_HEIGHT_PIXELS
                || rect.leftEdgeCol() < 0 || rect.rightEdgeCol() >= DimensionsC.CANVAS_WIDTH_PIXELS;
    }
}
