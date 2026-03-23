package org.example.util;

import org.example.constant.Configs;
import org.example.constant.DimensionsC;
import org.example.constant.DirectionsE;
import org.example.constant.SpriteE;
import org.example.entity.CanvasCoordinate;
import org.example.entity.MazeCell;
import org.example.event.ghost.GhostMovementAttemptEvent;

import java.util.List;

public class GhostUtil
{
    private GhostUtil() {}

    public static CanvasCoordinate move(GhostMovementAttemptEvent event) {
        return move(event.getGhostCurrCord(), event.getMovementDir());
    }

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

    public static List<MazeCell> getCandidateNextCells(CanvasCoordinate cord, SpriteE[][] maze) {
        List<MazeCell> candidateNextCells = null;
        candidateNextCells = CanvasUtil.getIntersectingMazeCells(cord);
        System.out.println("interestingMazeCells = " + candidateNextCells);
        if (candidateNextCells.size() == 1) {
            // ghost lies completely in a maze cell
            candidateNextCells = CanvasUtil.get90DegAdjMazeCells(cord);
        }
        return candidateNextCells
                .stream()
                .filter(cell -> maze[cell.getRow()][cell.getCol()] != SpriteE.WALL)
                .toList();
    }
}
