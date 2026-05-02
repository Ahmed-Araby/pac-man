package org.example.sprite.ghost;

import org.example.constant.DirectionsE;
import org.example.constant.SpriteE;
import org.example.entity.CanvasCoordinate;
import org.example.sprite.MovingSprite;

public abstract class Ghost extends MovingSprite {

    public Ghost(SpriteE type, double col, double row, DirectionsE dir) {
        super(type, col, row, dir);
    }

    public abstract CanvasCoordinate getPacManCanvasCord();
}
