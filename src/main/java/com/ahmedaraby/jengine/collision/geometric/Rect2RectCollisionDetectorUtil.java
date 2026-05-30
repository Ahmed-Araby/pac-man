package com.ahmedaraby.jengine.collision.geometric;

import com.ahmedaraby.jengine.entity.Rectangle;

public class Rect2RectCollisionDetectorUtil {

    public static boolean collide(Rectangle r1, Rectangle r2) {
        return r1.rightEdgeCol() >= r2.leftEdgeCol()
                && r1.leftEdgeCol() <= r2.rightEdgeCol()
                && r1.bottomEdgeRow() >= r2.topEdgeRow()
                && r1.topEdgeRow() <= r2.bottomEdgeRow();
    }


}
