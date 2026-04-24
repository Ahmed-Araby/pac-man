package org.example.collision.sprite;


import org.example.collision.geometric.Rect2RectCollisionDetectorUtil;
import org.example.constant.DimensionsC;
import org.example.constant.SpriteE;
import org.example.entity.CanvasCoordinate;
import org.example.entity.CanvasRect;
import org.example.event.collision.CollisionDetectionEvent;
import org.example.event.collision.M2SCollisionDetectionEvent;
import org.example.model.CollisionReport;
import org.example.util.SpriteUtil;
import org.example.util.canvas.CanvasRectUtils;

import java.util.List;
import java.util.Optional;

public class M2SSpriteCollisionDetector implements SpriteCollisionDetector {

    @Override
    public Optional<CollisionReport> detect(CollisionDetectionEvent event) {
        return detect((M2SCollisionDetectionEvent) event);
    }

    private Optional<CollisionReport> detect(M2SCollisionDetectionEvent event) {
        final CanvasRect rectA = event.getRectA();
        final List<CanvasCoordinate> corners = CanvasRectUtils.get4Corners(rectA);
        final Optional<CanvasRect> collidingRect = corners.stream()
                .map(this::toTopLeftCornerOfRectContainingPoint)
                .filter(topLeftCornerCord -> this.isTarget(topLeftCornerCord, event.getTarget()))
                .map(topLeftCornerCord -> SpriteUtil.toRect(topLeftCornerCord, event.getTarget()))
                .filter(rectB -> Rect2RectCollisionDetectorUtil.collide(rectA, rectB))
                .findFirst();

        return collidingRect
                .map(CanvasRect::getTopLeftCorner)
                .map(CollisionReport::new);
    }


    private CanvasCoordinate toTopLeftCornerOfRectContainingPoint(CanvasCoordinate point) {
        return CanvasRectUtils.getTopLeftCornerOfRectContainingPoint(DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS, point);
    }
    private boolean isTarget(CanvasCoordinate topLeftCornerCord, SpriteE targetSpriteType) {
        return SpriteUtil.getSpriteType(topLeftCornerCord).equals(targetSpriteType);
    }
}
