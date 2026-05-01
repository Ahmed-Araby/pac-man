package org.example.collision.sprite;


import org.example.collision.geometric.Rect2RectCollisionDetectorUtil;
import org.example.constant.DimensionsC;
import org.example.constant.SpriteE;
import org.example.entity.CanvasCoordinate;
import org.example.entity.CanvasRect;
import org.example.model.CollisionReport;
import org.example.util.SpriteUtil;
import org.example.util.canvas.CanvasRectUtils;

import java.util.List;
import java.util.Optional;

public class M2SSpriteCollisionDetector {

    public Optional<CollisionReport> detect(CanvasRect rect, SpriteE target) {
        final List<CanvasCoordinate> corners = CanvasRectUtils.get4Corners(rect);
        final Optional<CanvasRect> collidingRect = corners.stream()
                .map(this::toTopLeftCornerOfRectContainingPoint)
                .filter(topLeftCornerCord -> this.isTarget(topLeftCornerCord, target))
                .map(topLeftCornerCord -> SpriteUtil.toRect(topLeftCornerCord, target))
                .filter(rectB -> Rect2RectCollisionDetectorUtil.collide(rect, rectB))
                .findFirst();

        if (collidingRect.isPresent()) {
            return Optional.of(new CollisionReport(rect, List.of(collidingRect.get())));
        }
        return Optional.empty();
    }


    private CanvasCoordinate toTopLeftCornerOfRectContainingPoint(CanvasCoordinate point) {
        return CanvasRectUtils.getTopLeftCornerOfRectContainingPoint(DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS, point);
    }
    private boolean isTarget(CanvasCoordinate topLeftCornerCord, SpriteE targetSpriteType) {
        return SpriteUtil.getSpriteType(topLeftCornerCord).equals(targetSpriteType);
    }
}
