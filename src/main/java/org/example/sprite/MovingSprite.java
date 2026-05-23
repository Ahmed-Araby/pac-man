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
import org.example.model.GameState;
import org.example.util.SpriteUtil;

import java.util.List;


@Getter
@Setter
public abstract class MovingSprite extends Sprite {

    protected DirectionsE dir;

    public MovingSprite(GameState gameState, SpriteE type, double col, double row, DirectionsE dir) {
        super(gameState, type, col, row);
        this.dir = dir;
    }


    public abstract void move(Event event);

    // [TODO] this method need to be generic, i.e. handle sprites with different Dimensions, to do this we need to define the dimensions within the sprite calling this method
    protected boolean isGoingOutOfCanvas(CanvasCoordinate topLeftCorner) {
        return topLeftCorner.getRow() < 0 || topLeftCorner.getRow() > DimensionsC.CANVAS_HEIGHT_PIXELS - DimensionsC.GHOST_HEIGHT_PIXELS ||
                topLeftCorner.getCol() < 0 || topLeftCorner.getCol() > DimensionsC.CANVAS_WIDTH_PIXELS - DimensionsC.GHOST_WIDTH_PIXELS;
    }

    protected boolean isCollidingWithWallOrGhostHWall(CanvasCoordinate topLeftCorner) {
        final CanvasRect rect = SpriteUtil.toRect(topLeftCorner, type);
        List<CollisionReport> collisionReports = M2SSpriteCollisionDetector.detect(rect, List.of(SpriteE.WALL, SpriteE.GHOST_HOUSE_WALL));
        return !collisionReports.isEmpty();
    }
}
