package com.ahmedaraby.game.pacman.sprite;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.game.pacman.entity.Cell;
import com.ahmedaraby.game.pacman.entity.MazeMove;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.playground.Playground;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.entity.Vector;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class MachineControlledSprite extends MovingSprite {
    public MachineControlledSprite(GameState gameState, ConfigsEx configs, SpriteE type, Coordinate cord, double width, double height, Vector dirV) {
        super(gameState, configs, type, cord, width, height, dirV);
    }

    // [TODO] find a smarter way to implement this method
    private Optional<MazeMove> getMazeMoveToCurrCell() {
        final Coordinate spriteCord = getTopLeftCorner();
        final Cell currCell = calcCell();
        final Coordinate currCellCord = currCell.toCord(configs.PLAYGROUND_CELL_SIZE(), configs.PLAYGROUND_CELL_SIZE());
        Vector moveDir = Vector.STILL;
        final double colDiff = spriteCord.getCol() - currCellCord.getCol();
        final double rowDiff = spriteCord.getRow() - currCellCord.getRow();
        if (colDiff < -configs.CORD_EQ_THRESHOLD()) {
             moveDir = Vector.RIGHT;
        } else if (colDiff > configs.CORD_EQ_THRESHOLD()) {
            moveDir = Vector.LEFT;
        } else if (rowDiff < -configs.CORD_EQ_THRESHOLD()) {
            moveDir = Vector.DOWN;
        } else if (rowDiff > configs.CORD_EQ_THRESHOLD()) {
            moveDir = Vector.UP;
        }
        return moveDir.equals(Vector.STILL) ? Optional.empty() : Optional.of(new MazeMove(currCell, currCell, moveDir));
    }

    private List<MazeMove> getPossibleMazeMovesToAdjacentCells() {
        final Cell currCell = calcCell();
        return currCell
                .getMoves(configs.PLAYGROUND_WIDTH(), configs.PLAYGROUND_HEIGHT())
                .stream()
                .filter(move -> !Playground.isWall(move.getTo()))
                .filter(move -> !Playground.isGhostHWall(move.getTo()))
                .filter(this::isPossibleMove)
                .collect(Collectors.toList());
    }

    public List<MazeMove> getPossibleMazeMoves() {
        final List<MazeMove> moves = getPossibleMazeMovesToAdjacentCells();
        getMazeMoveToCurrCell().ifPresent(moves::add);
        return moves;
    }

    @Override
    protected boolean isPossibleMove(double stride, Vector dir) {
        // [TODO] make this threshold be configured
        if (stride < 0.001) {
            return false;
        }
        final Coordinate calibratedNextCord = calcNextCord(stride, dir);
        return !isGoingOutOfCanvas(stride, dir) && !isCollidingWithWallOrGhostHWall(calibratedNextCord);
    }
}
