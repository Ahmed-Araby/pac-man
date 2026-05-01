package org.example.collision.sprite;


import org.example.collision.geometric.Rect2RectCollisionDetectorUtil;
import org.example.constant.DimensionsC;
import org.example.entity.CanvasRect;
import org.example.model.CollisionReport;

import java.util.List;
import java.util.Optional;

public class M2MSpriteCollisionDetector {

    public Optional<CollisionReport> detect(CanvasRect rectA, CanvasRect rectB) {
        final Boolean collide = Rect2RectCollisionDetectorUtil.collide(rectA, rectB);
        return collide ? Optional.of(new CollisionReport(rectA, List.of(rectB))) : Optional.empty();
    }

    private boolean outOfCanvas(CanvasRect rect) {
        return rect.topEdgeRow() < 0 || rect.bottomEdgeRow() >= DimensionsC.CANVAS_HEIGHT_PIXELS
                || rect.leftEdgeCol() < 0 || rect.rightEdgeCol() >= DimensionsC.CANVAS_WIDTH_PIXELS;
    }
}
