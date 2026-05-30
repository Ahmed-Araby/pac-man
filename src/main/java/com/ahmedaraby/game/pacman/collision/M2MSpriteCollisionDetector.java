package com.ahmedaraby.game.pacman.collision;


import com.ahmedaraby.jengine.collision.geometric.Rect2RectCollisionDetectorUtil;
import com.ahmedaraby.jengine.entity.Rectangle;
import com.ahmedaraby.jengine.models.CollisionReport;

import java.util.List;
import java.util.Optional;

public class M2MSpriteCollisionDetector {

    public static Optional<CollisionReport> detect(Rectangle rectA, Rectangle rectB) {
        final Boolean collide = Rect2RectCollisionDetectorUtil.collide(rectA, rectB);
        return collide ? Optional.of(new CollisionReport(rectA, List.of(rectB))) : Optional.empty();
    }
}
