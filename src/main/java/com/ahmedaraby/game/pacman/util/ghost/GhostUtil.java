package com.ahmedaraby.game.pacman.util.ghost;

import com.ahmedaraby.game.pacman.config.Configs;
import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.entity.CanvasCoordinate;
import com.ahmedaraby.game.pacman.entity.MazeCell;
import com.ahmedaraby.game.pacman.entity.Vector;
import com.ahmedaraby.game.pacman.util.canvas.CanvasUtil;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.maze.Playground;

import java.util.List;

public class GhostUtil {
    private GhostUtil() {
    }

    // [TODO] put this movement behaviour in the Ghost abstract class
    public static CanvasCoordinate move(CanvasCoordinate cord, DirectionsE dir) {
        switch (dir) {
            case RIGHT:
                return new CanvasCoordinate(cord.getRow(),
                        cord.getCol() + DimensionsC.GHOST_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_STRIDE);
            case LEFT:
                return new CanvasCoordinate(cord.getRow(),
                        cord.getCol() - DimensionsC.GHOST_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_STRIDE);
            case UP:
                return new CanvasCoordinate(cord.getRow() - DimensionsC.GHOST_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_STRIDE,
                        cord.getCol());
            case DOWN:
                return new CanvasCoordinate(cord.getRow() + DimensionsC.GHOST_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_STRIDE,
                        cord.getCol());
            default:
                return cord;
        }
    }

    public static CanvasCoordinate move(CanvasCoordinate cord, Vector dir) {
        final double newX = cord.getCol() + dir.getX() * (DimensionsC.GHOST_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_STRIDE);
        final double newY = cord.getRow() + dir.getY() * (DimensionsC.GHOST_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_STRIDE);
        return new CanvasCoordinate(newY, newX);
    }

    public static List<MazeCell> getCandidateNextCells(CanvasCoordinate cord) {
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
