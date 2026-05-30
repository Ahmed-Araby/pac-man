package com.ahmedaraby.game.pacman.sprite;

import com.ahmedaraby.jengine.collision.sprite.M2SSpriteCollisionDetector;
import com.ahmedaraby.game.pacman.util.SpriteUtil;
import lombok.Getter;
import lombok.Setter;
import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.game.pacman.entity.CanvasCoordinate;
import com.ahmedaraby.game.pacman.entity.CanvasRect;
import com.ahmedaraby.game.pacman.event.Event;
import com.ahmedaraby.game.pacman.model.CollisionReport;
import com.ahmedaraby.game.pacman.model.GameState;

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
