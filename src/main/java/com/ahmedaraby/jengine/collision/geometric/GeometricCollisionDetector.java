package com.ahmedaraby.jengine.collision.geometric;

import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.entity.Rectangle;

public interface GeometricCollisionDetector {

    boolean collide(Coordinate topLeftCorner1, Coordinate topLeftCorner2);

    default boolean outOfCanvas(Rectangle rect) {
        return rect.topEdgeRow() < 0 || rect.bottomEdgeRow() >= DimensionsC.CANVAS_HEIGHT_PIXELS
                || rect.leftEdgeCol() < 0 || rect.rightEdgeCol() >= DimensionsC.CANVAS_WIDTH_PIXELS;

    }
}
