package com.ahmedaraby.game.pacman.sprite;

import com.ahmedaraby.game.pacman.collision.M2SSpriteCollisionDetector;
import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.util.SpriteUtil;
import com.ahmedaraby.jengine.entity.Vector;
import lombok.Getter;
import lombok.Setter;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.entity.Rectangle;
import com.ahmedaraby.game.pacman.event.Event;
import com.ahmedaraby.game.pacman.model.CollisionReport;
import com.ahmedaraby.game.pacman.model.GameState;

import java.util.List;


@Getter
@Setter
public abstract class MovingSprite extends Sprite {

    protected DirectionsE dir;

    public MovingSprite(GameState gameState, ConfigsEx configs, SpriteE type, Coordinate cord, double width, double height, DirectionsE dir) {
        super(gameState, configs, type, cord, width, height);
        this.dir = dir;
    }


    public abstract void move(Event event);

    protected boolean isCollidingWithWallOrGhostHWall(Coordinate topLeftCorner) {
        final Rectangle rect = SpriteUtil.toRect(topLeftCorner, type);
        List<CollisionReport> collisionReports = M2SSpriteCollisionDetector.detect(rect, List.of(SpriteE.WALL, SpriteE.GHOST_HOUSE_WALL));
        return !collisionReports.isEmpty();
    }


    protected double getSpeed() {
        throw new IllegalStateException("getSpeed() is not implemented for class : " + this.getClass().getSimpleName());
    }

    protected double calculateStride() {
        final double elapsedTimeSec = Math.abs(gameState.getCurrFrameStartNanos() - gameState.getPrevFrameEndNanos()) / 1_000_000_000.0;
        return getSpeed() * elapsedTimeSec;
    }

    protected double calibrateStride(double stride, Vector dir) {
        if (dir == Vector.LEFT) {
            if (getCol() - stride < 0) {
                return getCol();
            }
        } else if (dir == Vector.RIGHT) {
            if (getCol() + stride > configs.CANVAS_WIDTH() - configs.PLAYGROUND_CELL_SIZE()) {
                return configs.CANVAS_WIDTH() - configs.PLAYGROUND_CELL_SIZE() - getCol();
            }
        } else if (dir == Vector.UP) {
            if (getRow() - stride < 0) {
                return getRow();
            }
        } else if (dir == Vector.DOWN) {
            if (getRow() + stride > configs.CANVAS_HEIGHT() - configs.PLAYGROUND_CELL_SIZE()) {
                return configs.CANVAS_HEIGHT() - configs.PLAYGROUND_CELL_SIZE() - getRow();
            }
        }

        return stride;
    }
}
