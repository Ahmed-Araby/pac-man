package org.example.collision;

import org.example.entity.CanvasRect;

public class Rect2RectCollisionDetectorUtil {

    public static boolean collide(CanvasRect r1, CanvasRect r2) {
        return r1.rightEdgeCol() >= r2.leftEdgeCol()
                && r1.leftEdgeCol() <= r2.rightEdgeCol()
                && r1.bottomEdgeRow() >= r2.topEdgeRow()
                && r1.topEdgeRow() <= r2.bottomEdgeRow();
    }


}
