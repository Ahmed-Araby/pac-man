package com.ahmedaraby.game.pacman.collision.sprite;


import com.ahmedaraby.game.pacman.collision.geometric.Rect2RectCollisionDetectorUtil;
import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.game.pacman.entity.CanvasCoordinate;
import com.ahmedaraby.game.pacman.entity.CanvasRect;
import com.ahmedaraby.game.pacman.model.CollisionReport;
import com.ahmedaraby.game.pacman.util.SpriteUtil;
import com.ahmedaraby.game.pacman.util.canvas.CanvasRectUtils;

import java.util.List;
import java.util.Optional;

public class M2SSpriteCollisionDetector {

    public static List<CollisionReport> detect(CanvasRect rect, List<SpriteE> targets) {
        // [TODO] optimize, either run in parallel, or check for all targets at once
        return targets.stream()
                .map(target -> detect(rect, target))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
    public static Optional<CollisionReport> detect(CanvasRect rect, SpriteE target) {
        final List<CanvasCoordinate> corners = CanvasRectUtils.get4Corners(rect);
        final Optional<CanvasRect> collidingRect = corners.stream()
                .map(M2SSpriteCollisionDetector::toTopLeftCornerOfRectContainingPoint)
                .filter(topLeftCornerCord -> isTarget(topLeftCornerCord, target))
                .map(topLeftCornerCord -> SpriteUtil.toRect(topLeftCornerCord, target))
                .filter(rectB -> Rect2RectCollisionDetectorUtil.collide(rect, rectB))
                .findFirst();

        if (collidingRect.isPresent()) {
            return Optional.of(new CollisionReport(rect, List.of(collidingRect.get())));
        }
        return Optional.empty();
    }


    private static CanvasCoordinate toTopLeftCornerOfRectContainingPoint(CanvasCoordinate point) {
        return CanvasRectUtils.getTopLeftCornerOfRectContainingPoint(DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS, point);
    }
    private static boolean isTarget(CanvasCoordinate topLeftCornerCord, SpriteE targetSpriteType) {
        return SpriteUtil.getSpriteType(topLeftCornerCord).equals(targetSpriteType);
    }
}
