package com.ahmedaraby.game.pacman.sprite;

import com.ahmedaraby.game.pacman.collision.M2SSpriteCollisionDetector;
import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.event.movement.PacManMovementRequestEvent;
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
    protected Vector dirV; // [TODO] move this to MovingSprite later

    public MovingSprite(GameState gameState, ConfigsEx configs, SpriteE type, Coordinate cord, double width, double height, DirectionsE dir) {
        super(gameState, configs, type, cord, width, height);
        this.dir = dir;
    }


    public abstract void move(Event event);

    protected boolean isCollidingWithWallOrGhostHWall(Coordinate topLeftCorner) {
        final Rectangle rect = new Rectangle(topLeftCorner, getWidth(), getHeight());
        List<CollisionReport> collisionReports = M2SSpriteCollisionDetector.detect(rect, List.of(SpriteE.WALL, SpriteE.GHOST_HOUSE_WALL));
        return !collisionReports.isEmpty();
    }

    protected double getSpeed() {
        throw new IllegalStateException("getSpeed in sprite " + getClass().getSimpleName() +  " is not implemented");
    }

    protected double calcStride() {
        final double elapsedTime = Math.abs(gameState.getPrevFrameEndedAt() - gameState.getCurrFrameStartedAt()) / 1_000_000_000.0;
        return getSpeed() * elapsedTime;
    }

    protected Coordinate calcNextCord(double stride, Vector dir) {
        final double newCol = getCol() + dir.getX() * stride;
        final double newRow = getRow() + dir.getY() * stride;
        return new Coordinate(newRow, newCol);
    }

    protected boolean attemptMovement(PacManMovementRequestEvent event) {
        final double stride = calcStride();
        final Coordinate nextCord = calcNextCord(stride, event.getDir());
        final Rectangle nextRect = new Rectangle(nextCord, getWidth(), getHeight());

        if (nextRect.within(gameState.getMaze().getRect()) && !isCollidingWithWallOrGhostHWall(nextCord)) {
            setTopLeftCorner(nextCord);
            setDir(DirectionsE.fromVector(event.getDir()));
            setDirV(event.getDir());
            return true;
        }
        return false;
    }
}
