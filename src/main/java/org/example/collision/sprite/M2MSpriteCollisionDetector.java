package org.example.collision.sprite;


import org.example.constant.DimensionsC;
import org.example.entity.CanvasRect;
import org.example.event.collision.CollisionDetectionEvent;
import org.example.event.collision.M2MCollisionDetectionEvent;
import org.example.model.CollisionReport;

import java.util.Optional;

public class M2MSpriteCollisionDetector implements SpriteCollisionDetector {

    @Override
    public Optional<CollisionReport> detect(CollisionDetectionEvent event) {
        return detect((M2MCollisionDetectionEvent) event);
    }

    private Optional<CollisionReport> detect(M2MCollisionDetectionEvent event) {
        return Optional.empty();
    }

    private boolean outOfCanvas(CanvasRect rect) {
        return rect.topEdgeRow() < 0 || rect.bottomEdgeRow() >= DimensionsC.CANVAS_HEIGHT_PIXELS
                || rect.leftEdgeCol() < 0 || rect.rightEdgeCol() >= DimensionsC.CANVAS_WIDTH_PIXELS;
    }
}
