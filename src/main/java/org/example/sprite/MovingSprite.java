package org.example.sprite;

import lombok.Getter;
import lombok.Setter;
import org.example.constant.DimensionsC;
import org.example.constant.DirectionsE;
import org.example.entity.CanvasCoordinate;
import org.example.event.Event;

@Getter
@Setter
public abstract class MovingSprite extends Sprite {

    protected double col;
    protected double row;
    protected DirectionsE dir;

    public abstract void move(Event event);


    public CanvasCoordinate getTopLeftCorner() {
        return new CanvasCoordinate(row, col);
    }

    public boolean isGoingOutOfCanvas(CanvasCoordinate topLeftCorner) {
        return topLeftCorner.getRow() < 0 || topLeftCorner.getRow() > DimensionsC.CANVAS_HEIGHT_PIXELS - DimensionsC.GHOST_HEIGHT_PIXELS ||
                topLeftCorner.getCol() < 0 || topLeftCorner.getCol() > DimensionsC.CANVAS_WIDTH_PIXELS - DimensionsC.GHOST_WIDTH_PIXELS;
    }
}
