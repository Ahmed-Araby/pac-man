package com.ahmedaraby.game.pacman.sprite;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.game.pacman.model.Cell;
import com.ahmedaraby.game.pacman.model.MazeMove;
import com.ahmedaraby.game.pacman.ghostmode.navigation.StrideCalculator;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.playground.Playground;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.entity.Vector;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class MachineControlledSprite extends MovingSprite {
    public MachineControlledSprite(GameState gameState, ConfigsEx configs, StrideCalculator strideCalc,
                                   SpriteE type, Coordinate cord, double width, double height, Vector dirV) {
        super(gameState, configs, strideCalc,
                type, cord, width, height, dirV);
    }

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
                .filter(this::isPossibleMove)
                .collect(Collectors.toList());
    }

    public List<MazeMove> getPossibleMazeMoves() {
        final List<MazeMove> moves = getPossibleMazeMovesToAdjacentCells();
        getMazeMoveToCurrCell().ifPresent(moves::add);
        return moves;
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
        // [TODO] the concept of sprite current cell to next cell alignment seems to be promising
        // and it can make the movement check logic simpler
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
}
