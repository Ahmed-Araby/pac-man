package org.example.util;

import org.example.constant.DimensionsC;
import org.example.constant.DirectionsE;
import org.example.entity.CanvasCoordinate;
import org.example.entity.MazeCell;
import org.example.entity.CanvasRect;

import java.util.List;

public class CanvasUtil {

    public static List<MazeCell> getIntersectingMazeCells(CanvasCoordinate cord) {
        final CanvasRect canvasRect = new CanvasRect(cord, DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS);
        final List<CanvasCoordinate> rectCorners = CanvasRectUtils.get4Corners(canvasRect);
        return rectCorners
                .stream()
                .map(corner -> CanvasRectUtils.getTopLeftCornerOfRectContainingPoint(DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS, corner))
                .map(topLeftCorner -> toMazeCoordinate(topLeftCorner, DirectionsE.STILL))
                .distinct()
                .toList();
    }



    public static DirectionsE getMovementDir(CanvasCoordinate from, CanvasCoordinate to) {
        if(to.getRow() > from.getRow()) {
            return DirectionsE.DOWN;
        } else if(to.getRow() < from.getRow()) {
            return DirectionsE.UP;
        } else if(to.getCol() > from.getCol()) {
            return DirectionsE.RIGHT;
        } else if(to.getCol() < from.getCol()) {
            return DirectionsE.LEFT;
        } else {
            return DirectionsE.STILL;
        }
    }

    public static MazeCell toMazeCoordinate(CanvasCoordinate cord, DirectionsE dir) {
        switch (dir) {
            case RIGHT, DOWN:
                return toMazeCoordinateFlooring(cord);
            case LEFT, UP:
                return toMazeCoordinateCeiling(cord);
            default:
                return toMazeCoordinateFlooring(cord);
        }
    }

    private static MazeCell toMazeCoordinateFlooring(CanvasCoordinate cord) {
        final int mazeRow = (int) Math.floor(cord.getRow() / DimensionsC.MAZE_CELL_SIZE_PIXELS);
        final int mazeCol = (int) Math.floor(cord.getCol() / DimensionsC.MAZE_CELL_SIZE_PIXELS);
        return new MazeCell(mazeRow, mazeCol);
    }

    private static MazeCell toMazeCoordinateCeiling(CanvasCoordinate cord) {
        final int mazeRow = (int) Math.ceil(cord.getRow() / DimensionsC.MAZE_CELL_SIZE_PIXELS);
        final int mazeCol = (int) Math.ceil(cord.getCol() / DimensionsC.MAZE_CELL_SIZE_PIXELS);
        return new MazeCell(mazeRow, mazeCol);
    }

}
