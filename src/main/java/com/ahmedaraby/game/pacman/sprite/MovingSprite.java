package com.ahmedaraby.game.pacman.sprite;

import com.ahmedaraby.game.pacman.collision.M2SSpriteCollisionDetector;
import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.entity.MazeMove;
import com.ahmedaraby.jengine.entity.Vector;
import lombok.Getter;
import lombok.Setter;
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

    protected Vector dirV;

    public MovingSprite(GameState gameState, ConfigsEx configs, SpriteE type, Coordinate cord, double width, double height, Vector dirV) {
        super(gameState, configs, type, cord, width, height);
        this.dirV = dirV;
    }


    public abstract void move(Event event);

    protected boolean isCollidingWithWallOrGhostHWall(Coordinate topLeftCorner) {
        final Rectangle rect = new Rectangle(topLeftCorner, getWidth(), getHeight());
        List<CollisionReport> collisionReports = M2SSpriteCollisionDetector.detect(rect, List.of(SpriteE.WALL, SpriteE.GHOST_HOUSE_WALL));
        return !collisionReports.isEmpty();
    }
    protected boolean isCollidingWithWallOrGhostHWall(double stride, Vector dir) {
        final Coordinate nextCord = calcNextCord(stride, dir);
        return isCollidingWithWallOrGhostHWall(nextCord);
    }

    protected boolean isGoingOutOfCanvas(double stride, Vector dir) {
        final Coordinate nextCord = calcNextCord(stride, dir);
        final Rectangle nextRect = new Rectangle(nextCord, getWidth(), getHeight());
        return !nextRect.within(gameState.getMaze().getRect());
    }

    protected double getSpeed() {
        throw new IllegalStateException("getSpeed in sprite " + getClass().getSimpleName() +  " is not implemented");
    }

    public double calcStride(Vector dir) {
        if (dir.equals(Vector.STILL)) {
            return 0;
        }
        final double elapsedTime = Math.abs(gameState.getPrevFrameEndedAt() - gameState.getCurrFrameStartedAt()) / 1_000_000_000.0;
        final double stride = getSpeed() * elapsedTime;
        // [TODO] separate calibration from calculation
        return calibrateStride(stride, dir);
    }

    protected double calibrateStride(double stride, Vector dir) {
        // [TODO] return offset with the correct sign from methods calculating the offset
        double calibratedStride = stride;
        if (isGoingOutOfCanvas(stride, dir)) {
            calibratedStride = stride + calcStrideCorrectiveOffsetToPreventGoingOut(stride, dir);
        } else if (isCollidingWithWallOrGhostHWall(stride, dir)) {
            calibratedStride = stride - calcStrideCorrectiveOffsetToUnblockWallStuck(stride, dir);
        }
        return calibratedStride;
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

    private double calcStrideCorrectiveOffsetToUnblockWallStuck(double stride, Vector dir) {
        final Coordinate nextCord = calcNextCord(stride, dir);
        if (Vector.RIGHT == dir) {
            return nextCord.getCol() % configs.PLAYGROUND_CELL_SIZE();
        } else if (Vector.LEFT == dir) {
            return configs.PLAYGROUND_CELL_SIZE() - (nextCord.getCol() % configs.PLAYGROUND_CELL_SIZE());
        } else if (Vector.UP == dir) {
            return configs.PLAYGROUND_CELL_SIZE() - (nextCord.getRow() % configs.PLAYGROUND_CELL_SIZE());
        } else if (Vector.DOWN == dir) {
            return nextCord.getRow() % configs.PLAYGROUND_CELL_SIZE();
        }
        return 0;
    }


    public Coordinate calcNextCord(double stride, Vector dir) {
        final double newCol = getCol() + dir.getX() * stride;
        final double newRow = getRow() + dir.getY() * stride;
        return new Coordinate(newRow, newCol);
    }

    protected boolean isPossibleMove(MazeMove move) {
        if (move.getDir().equals(dirV)) {
            return isPossibleToMoveInSameDir();
        }
        return isPossibleToMoveInNewDir(move.getDir());
    }
    protected boolean isPossibleToMoveInSameDir() {
        if (dirV == Vector.STILL) {
            return false;
        }
        final double stride = calcStride(dirV);
        return isPossibleMove(stride, dirV);
    }

    protected boolean isPossibleToMoveInNewDir(Vector dir) {
        if (dir == Vector.STILL) {
            return false;
        }
        /**
         * when moving in a new direction, the validity of the move is checked with the static stride.
         * the static stride is equal to the empty space that the sprite can move in within a cell without colliding with a wall,
         * because this helps to prevent the sprite from moving between walls on the sub pixel level.
         * however, if the movement is valid, the actual movement applied on the sprite happens using the original stride.
         */
        double emptySpace;
        if (dir.isVertical()) {
            emptySpace = calcVEmptySpaceInPlaygroundCell();
        } else {
            emptySpace = calcHEmptySpaceInPlaygroundCell();
        }
        return isPossibleMove(emptySpace, dir);
    }

    protected boolean isPossibleMove(double stride, Vector dir) {
        final Coordinate calibratedNextCord = calcNextCord(stride, dir);
        return !isGoingOutOfCanvas(stride, dir) && !isCollidingWithWallOrGhostHWall(calibratedNextCord);
    }

    public void move(double stride, Vector dir) {
        final Coordinate nextCord = calcNextCord(stride, dir);
        setTopLeftCorner(nextCord);
        setDirV(dir);
    }
}
