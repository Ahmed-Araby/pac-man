package org.example.sprite.ghost;

import org.example.entity.CanvasCoordinate;
import org.example.sprite.MovingSprite;

public abstract class Ghost extends MovingSprite {
    public abstract CanvasCoordinate getPacManCanvasCord();
}
