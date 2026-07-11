package com.ahmedaraby.game.pacman.sprite;

import com.ahmedaraby.game.pacman.collision.M2SSpriteCollisionDetector;
import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
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

    protected boolean isGoingOutOfCanvas(double stride, Vector dir) {
        final Coordinate nextCord = calcNextCord(stride, dir);
        final Rectangle nextRect = new Rectangle(nextCord, getWidth(), getHeight());
        return !nextRect.within(gameState.getMaze().getRect());
    }

    protected double getSpeed() {
        throw new IllegalStateException("getSpeed in sprite " + getClass().getSimpleName() +  " is not implemented");
    }

    protected double calcStride(Vector dir) {
        final double elapsedTime = Math.abs(gameState.getPrevFrameEndedAt() - gameState.getCurrFrameStartedAt()) / 1_000_000_000.0;
        final double originalStride = getSpeed() * elapsedTime;

        // refine stride
        double refinedStride = originalStride;
        if (isGoingOutOfCanvas(originalStride, dir)) {
            refinedStride = originalStride + calcStrideCorrectiveOffsetToPreventGoingOut(originalStride, dir);
        }
        return refinedStride;
    }

    private double calcStrideCorrectiveOffsetToPreventGoingOut(double stride, Vector dir) {
        final Coordinate nextCord = calcNextCord(stride, dir);
        if (Vector.RIGHT == dir) {
            return (configs.CANVAS_WIDTH() - getWidth()) - nextCord.getCol();
        } else if (Vector.LEFT == dir) {
            return nextCord.getCol();
        } else if (Vector.UP == dir) {
            return nextCord.getRow();
        } else if (Vector.DOWN == dir) {
            return (configs.CANVAS_HEIGHT() - getHeight()) - nextCord.getRow();
        }
        return 0;
    }

    private double calibrateToUnblock() {
        throw new IllegalStateException("not implemented");
    }


    protected Coordinate calcNextCord(double stride, Vector dir) {
        final double newCol = getCol() + dir.getX() * stride;
        final double newRow = getRow() + dir.getY() * stride;
        return new Coordinate(newRow, newCol);
    }

    // [TODO] remove the dir argument
    protected boolean attemptMovementInSameDir(Vector dir) {
        final double stride = calcStride(dir);
        if (isPossibleMove(stride, dir)) {
            move(stride, dir);
            return true;
        }
        return false;
    }

    protected boolean attemptMovementInNewDir(Vector dir) {
        /**
         * when moving in a new direction, the validity of the move is checked with the static
         * stride = 2, because this helps prevents the sprite from moving between walls on the sub pixel level.
         * however, if the movement is valid, the actual movement applied on the sprite happens using the original stride.
         */
        if (isPossibleMove(2, dir)) {
            final double stride = calcStride(dir);
            move(stride, dir);
            return true;
        }
        return false;
    }

    protected boolean isPossibleMove(double stride, Vector dir) {
        final Coordinate calibratedNextCord = calcNextCord(stride, dir);
        return !isGoingOutOfCanvas(stride, dir) && !isCollidingWithWallOrGhostHWall(calibratedNextCord);
    }

    protected void move(double stride, Vector dir) {
        final Coordinate nextCord = calcNextCord(stride, dir);
        setTopLeftCorner(nextCord);
        setDir(DirectionsE.fromVector(dir));
        setDirV(dir);
    }
}
