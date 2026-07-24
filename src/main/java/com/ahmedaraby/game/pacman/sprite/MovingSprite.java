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
     * first, it checks if the destination cell is a valid cell for the sprite to move to, i.e. it is not out of canvas
     * neither it is a wall or a ghost house wall.
     * <p>
     * second, it checks if the sprite is actually capable of moving to this cell by applying the calibrated stride
     * and make sure that the hypothetical sprite (i.e. after applying the move) is not going out of canvas
     * neither colliding.
     * <p>
     * you might wander, why in earth the first and second check has to be made, either one of them on its own is enough,
     * well,
     * <p>
     * the second check is needed because the sprite is almost never perfectly aligned with the cell it is trying to move to,
     * so, the cell might be a valid cell but the sprite can't do the move at the right moment because if it does, it will
     * collide with a wall. instead the sprite might need to move in the current direction in order to near perfect align
     * with the next cell and do the move without collision.
     * <p>
     * and the first part is needed to prevent the sprite from moving on the sub pixel level (using small calibrated strides)
     * towards a cell that will eventually appear to be not a possible goal, without the first check the sprites might look
     * like they are stuck for a while, by repeatedly applying tiny strides.
     *
     * @param move an object that contains the cell to which the sprite is trying to move (to) and the movement direction
     *             relative to the current direction of the sprite.
     * @return true if the movement is possible.
     */
    protected boolean isPossibleMove(MazeMove move) {
        if (Playground.isWall(move.getTo())
                || Playground.isGhostHWall(move.getTo())
                || Vector.STILL.equals(move.getDir())) {
            return false;
        }
        if (move.getDir().equals(dirV)) {
            // [TODO] should consider opposite direction, (i.e. any direction parallel to the current direction)
            return isPossibleToMoveInSameDir();
        }
        return isPossibleToMoveInNewDir(move.getDir());
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
        final Coordinate nextCord = calcNextCord(stride, dir);
        return !isGoingOutOfCanvas(stride, dir) && !isCollidingWithWallOrGhostHWall(nextCord);
    }

    public void move(double stride, Vector dir) {
        final Coordinate nextCord = calcNextCord(stride, dir);
        setTopLeftCorner(nextCord);
        setDirV(dir);
    }
}
