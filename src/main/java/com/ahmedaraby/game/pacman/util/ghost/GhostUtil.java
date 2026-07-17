package com.ahmedaraby.game.pacman.util.ghost;

import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.game.pacman.entity.Cell;
import com.ahmedaraby.jengine.entity.Rectangle;
import com.ahmedaraby.game.pacman.playground.Playground;

import java.util.List;

public class GhostUtil {
    private GhostUtil() {
    }
    public static List<Cell> getCandidateNextCells(Coordinate cord) {
        List<Cell> candidateNextCells = null;
        candidateNextCells = getIntersectingMazeCells(cord);
        System.out.println("interestingMazeCells = " + candidateNextCells);
        if (candidateNextCells.size() == 1) {
            // ghost lies completely in a maze cell
            Cell cell = cord.toCell();
            candidateNextCells = cell.getAdjCells(DimensionsC.MAZE_WIDTH, DimensionsC.MAZE_HEIGHT);
        }
        return candidateNextCells
                .stream()
                .filter(cell -> !Playground.isWall(cell) && !Playground.isGhostHWall(cell))
                .toList();
    }

    private static List<Cell> getIntersectingMazeCells(Coordinate cord) {
        final Rectangle rectangle = new Rectangle(cord, DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS);
        final List<Coordinate> rectCorners = rectangle.corners();
        return rectCorners
                .stream()
                .map(corner -> Playground.getRectContainingPoint(corner).topLeftCorner())
                .map(topLeftCorner -> topLeftCorner.toCell())
                .distinct()
                .toList();
    }
}
