package com.ahmedaraby.jengine.collision.sprite;


import com.ahmedaraby.jengine.collision.geometric.Rect2RectCollisionDetectorUtil;
import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.jengine.entity.Rectangle;
import com.ahmedaraby.game.pacman.model.CollisionReport;

import java.util.List;
import java.util.Optional;

public class M2MSpriteCollisionDetector {

    public static Optional<CollisionReport> detect(Rectangle rectA, Rectangle rectB) {
        final Boolean collide = Rect2RectCollisionDetectorUtil.collide(rectA, rectB);
        return collide ? Optional.of(new CollisionReport(rectA, List.of(rectB))) : Optional.empty();
    }

    // [TODO] remove this method
    private static boolean outOfCanvas(Rectangle rect) {
        return rect.topEdgeRow() < 0 || rect.bottomEdgeRow() >= DimensionsC.CANVAS_HEIGHT_PIXELS
                || rect.leftEdgeCol() < 0 || rect.rightEdgeCol() >= DimensionsC.CANVAS_WIDTH_PIXELS;
    }
}
