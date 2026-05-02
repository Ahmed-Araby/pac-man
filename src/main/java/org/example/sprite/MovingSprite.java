package org.example.sprite;

import lombok.Getter;
import lombok.Setter;
import org.example.collision.sprite.M2SSpriteCollisionDetector;
import org.example.constant.DimensionsC;
import org.example.constant.DirectionsE;
import org.example.constant.SpriteE;
import org.example.entity.CanvasCoordinate;
import org.example.entity.CanvasRect;
import org.example.event.Event;
import org.example.model.CollisionReport;
import org.example.util.SpriteUtil;

import java.util.Optional;

@Getter
@Setter
public abstract class MovingSprite extends Sprite {

    protected DirectionsE dir;

    public MovingSprite(SpriteE type, double col, double row, DirectionsE dir) {
        super(type, col, row);
        this.dir = dir;
    }


    public abstract void move(Event event);

    // [TODO] this method need to be generic, i.e. handle sprites with different Dimensions, to do this we need to define the dimensions within the sprite calling this method
    protected boolean isGoingOutOfCanvas(CanvasCoordinate topLeftCorner) {
        return topLeftCorner.getRow() < 0 || topLeftCorner.getRow() > DimensionsC.CANVAS_HEIGHT_PIXELS - DimensionsC.GHOST_HEIGHT_PIXELS ||
                topLeftCorner.getCol() < 0 || topLeftCorner.getCol() > DimensionsC.CANVAS_WIDTH_PIXELS - DimensionsC.GHOST_WIDTH_PIXELS;
    }

    protected boolean isCollidingWithWall(CanvasCoordinate topLeftCorner) {
        final CanvasRect rect = SpriteUtil.toRect(topLeftCorner, type);
        return M2SSpriteCollisionDetector.detect(rect, SpriteE.WALL).isPresent();
    }
}
