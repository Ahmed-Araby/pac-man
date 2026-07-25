package com.ahmedaraby.game.pacman.sprite;

import com.ahmedaraby.game.pacman.collision.M2SSpriteCollisionDetector;
import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.model.MazeMove;
import com.ahmedaraby.game.pacman.ghostmode.navigation.StrideCalculator;
import com.ahmedaraby.game.pacman.playground.Playground;
import com.ahmedaraby.jengine.entity.Vector;
import lombok.Getter;
import lombok.Setter;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.entity.Rectangle;
import com.ahmedaraby.game.pacman.model.event.Event;
import com.ahmedaraby.game.pacman.model.CollisionReport;
import com.ahmedaraby.game.pacman.model.GameState;

import java.util.List;

@Getter
@Setter
public abstract class MovingSprite extends Sprite {

    protected Vector dirV;
    private final StrideCalculator strideCalc;

    public MovingSprite(GameState gameState, ConfigsEx configs, StrideCalculator strideCalc,
                        SpriteE type, Coordinate cord, double width, double height, Vector dirV) {
        super(gameState, configs,
                type, cord, width, height);
        this.dirV = dirV;
        this.strideCalc = strideCalc;
    }

    public abstract void move(Event event);

    protected boolean isCollidingWithWallOrGhostHWall(Coordinate topLeftCorner) {
        final Rectangle rect = new Rectangle(topLeftCorner, getWidth(), getHeight());
        List<CollisionReport> collisionReports = M2SSpriteCollisionDetector.detect(rect, List.of(SpriteE.WALL, SpriteE.GHOST_HOUSE_WALL));
        return !collisionReports.isEmpty();
    }
    public boolean isCollidingWithWallOrGhostHWall(double stride, Vector dir) {
        final Coordinate nextCord = calcNextCord(stride, dir);
        return isCollidingWithWallOrGhostHWall(nextCord);
    }

    public boolean isGoingOutOfCanvas(double stride, Vector dir) {
        final Coordinate nextCord = calcNextCord(stride, dir);
        final Rectangle nextRect = new Rectangle(nextCord, getWidth(), getHeight());
        return !nextRect.within(gameState.getMaze().getRect());
    }

    // [TODO] make this a property with out of the box getter
    public double getSpeed() {
        throw new IllegalStateException("getSpeed in sprite " + getClass().getSimpleName() +  " is not implemented");
    }

    public double calcCalibratedStride(Vector newDir) {
        return strideCalc.calcCalibratedStride(this, newDir);
    }


    public Coordinate calcNextCord(double stride, Vector dir) {
        final double newCol = getCol() + dir.getX() * stride;
        final double newRow = getRow() + dir.getY() * stride;
        return new Coordinate(newRow, newCol);
    }


    /**
     * check the possibility of moving in the current direction, using the original stride
     * or a calibrated stride.
     * <p>
     * a calibrated stride is considered because the original stride might be too big which can cause the sprite
     * to go out of canvas or collide with a wall.
     * <p>
     * hence a smaller (still within the accepted threshold) stride is calculated (i.e. the calibrated stride)
     * to check if it is possible to make a more cautious move.
     *
     * @return true is the movement is possible.
     */
    protected boolean isPossibleToMoveInSameDir() {
        if (dirV == Vector.STILL) {
            return false;
        }
        final double stride = strideCalc.calcCalibratedStride(this, dirV);
        return isPossibleMove(stride, dirV);
    }

    /**
     * the main goal of this check is to prevent movement between walls in an empty area or on the sub pixel level,
     * and for this the applied stride has to be a minimum of the empty space that the sprite can move in within a cell
     * without colliding with walls horizontal to the movement direction.
     * <p>
     * however, if the movement is valid, it is expected that the actual movement applied on the sprite happens
     * using the original stride after calibration.
     * <p>
     * refer to the empty space calculation methods to know more.
     *
     * @param dir new direction
     * @return true of the movement is possible
     */
    protected boolean isPossibleToMoveInNewDir(Vector dir) {
        if (dir == Vector.STILL) {
            return false;
        }
        double emptySpace;
        if (dir.isVertical()) {
            emptySpace = calcVEmptySpaceInPlaygroundCell();
        } else {
            emptySpace = calcHEmptySpaceInPlaygroundCell();
        }
        return isPossibleMove(emptySpace, dir);
    }

    protected boolean isPossibleMove(double stride, Vector dir) {
        // [TODO] make this threshold be configured
        if (stride < 0.001) {
            return false;
        }
        final Coordinate nextCord = calcNextCord(stride, dir);
        return !isGoingOutOfCanvas(stride, dir) && !isCollidingWithWallOrGhostHWall(nextCord);
    }

    // [TODO] this should be moveAt like the GhostMode, or maybe it can be the other way around
    public void move(double stride, Vector dir) {
        final Coordinate nextCord = calcNextCord(stride, dir);
        setTopLeftCorner(nextCord);
        setDirV(dir);
    }
}
