package com.ahmedaraby.jengine.collision.sprite;


import com.ahmedaraby.jengine.collision.geometric.Rect2RectCollisionDetectorUtil;
import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.entity.Rectangle;
import com.ahmedaraby.game.pacman.model.CollisionReport;
import com.ahmedaraby.game.pacman.util.SpriteUtil;
import com.ahmedaraby.game.pacman.util.canvas.CanvasRectUtils;

import java.util.List;
import java.util.Optional;

public class M2SSpriteCollisionDetector {

    public static List<CollisionReport> detect(Rectangle rect, List<SpriteE> targets) {
        // [TODO] optimize, either run in parallel, or check for all targets at once
        return targets.stream()
                .map(target -> detect(rect, target))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
    public static Optional<CollisionReport> detect(Rectangle rect, SpriteE target) {
        final List<Coordinate> corners = CanvasRectUtils.get4Corners(rect);
        final Optional<Rectangle> collidingRect = corners.stream()
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


    private static Coordinate toTopLeftCornerOfRectContainingPoint(Coordinate point) {
        return CanvasRectUtils.getTopLeftCornerOfRectContainingPoint(DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS, point);
    }
    private static boolean isTarget(Coordinate topLeftCornerCord, SpriteE targetSpriteType) {
        return SpriteUtil.getSpriteType(topLeftCornerCord).equals(targetSpriteType);
    }
}
