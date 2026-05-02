package org.example.util.ghost;

import org.example.config.Configs;
import org.example.constant.DimensionsC;
import org.example.constant.DirectionsE;
import org.example.entity.CanvasCoordinate;
import org.example.entity.MazeCell;
import org.example.entity.Vector;
import org.example.maze.MazeMatrix;
import org.example.util.canvas.CanvasUtil;

import java.util.List;

public class GhostUtil {
    private GhostUtil() {
    }

    // [TODO] put this movement behaviour in the Ghost abstract class
    public static CanvasCoordinate move(CanvasCoordinate cord, DirectionsE dir) {
        switch (dir) {
            case RIGHT:
                return new CanvasCoordinate(cord.getRow(),
                        cord.getCol() + DimensionsC.BLINKY_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_BLINKY_STRIDE);
            case LEFT:
                return new CanvasCoordinate(cord.getRow(),
                        cord.getCol() - DimensionsC.BLINKY_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_BLINKY_STRIDE);
            case UP:
                return new CanvasCoordinate(cord.getRow() - DimensionsC.BLINKY_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_BLINKY_STRIDE,
                        cord.getCol());
            case DOWN:
                return new CanvasCoordinate(cord.getRow() + DimensionsC.BLINKY_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_BLINKY_STRIDE,
                        cord.getCol());
            default:
                return cord;
        }
    }

    public static CanvasCoordinate move(CanvasCoordinate cord, Vector dir) {
        final double newX = cord.getCol() + dir.getX() * (DimensionsC.BLINKY_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_BLINKY_STRIDE);
        final double newY = cord.getRow() + dir.getY() * (DimensionsC.BLINKY_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_BLINKY_STRIDE);
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
                .filter(cell -> !MazeMatrix.isWall(cell))
                .toList();
    }
}
