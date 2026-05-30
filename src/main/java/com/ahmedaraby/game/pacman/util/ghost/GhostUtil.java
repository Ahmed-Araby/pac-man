package com.ahmedaraby.game.pacman.util.ghost;

import com.ahmedaraby.game.pacman.config.Configs;
import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.game.pacman.entity.MazeCell;
import com.ahmedaraby.jengine.entity.Vector;
import com.ahmedaraby.game.pacman.util.canvas.CanvasUtil;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.playground.Playground;

import java.util.List;

public class GhostUtil {
    private GhostUtil() {
    }

    // [TODO] put this movement behaviour in the Ghost abstract class
    public static Coordinate move(Coordinate cord, DirectionsE dir) {
        switch (dir) {
            case RIGHT:
                return new Coordinate(cord.getRow(),
                        cord.getCol() + DimensionsC.GHOST_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_STRIDE);
            case LEFT:
                return new Coordinate(cord.getRow(),
                        cord.getCol() - DimensionsC.GHOST_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_STRIDE);
            case UP:
                return new Coordinate(cord.getRow() - DimensionsC.GHOST_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_STRIDE,
                        cord.getCol());
            case DOWN:
                return new Coordinate(cord.getRow() + DimensionsC.GHOST_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_STRIDE,
                        cord.getCol());
            default:
                return cord;
        }
    }

    public static Coordinate move(Coordinate cord, Vector dir) {
        final double newX = cord.getCol() + dir.getX() * (DimensionsC.GHOST_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_STRIDE);
        final double newY = cord.getRow() + dir.getY() * (DimensionsC.GHOST_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_STRIDE);
        return new Coordinate(newY, newX);
    }

    public static List<MazeCell> getCandidateNextCells(Coordinate cord) {
        List<MazeCell> candidateNextCells = null;
        candidateNextCells = CanvasUtil.getIntersectingMazeCells(cord);
        System.out.println("interestingMazeCells = " + candidateNextCells);
        if (candidateNextCells.size() == 1) {
            // ghost lies completely in a maze cell
            candidateNextCells = CanvasUtil.get90DegAdjMazeCells(cord);
        }
        return candidateNextCells
                .stream()
                .filter(cell -> !Playground.isWall(cell) && !Playground.isGhostHWall(cell))
                .toList();
    }
}
