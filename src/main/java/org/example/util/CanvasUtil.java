package org.example.util;

import org.example.constant.DimensionsC;
import org.example.constant.DirectionsE;
import org.example.entity.CanvasCoordinate;
import org.example.entity.MazeCoordinate;
import org.example.entity.CanvasRect;

import java.util.List;

public class CanvasUtil {

    public static List<MazeCoordinate> getIntersectingMazeCells(CanvasCoordinate cord) {
        final CanvasRect canvasRect = new CanvasRect(cord, DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS);
        final List<CanvasCoordinate> rectCorners = CanvasRectUtils.get4Corners(canvasRect);
        return rectCorners
                .stream()
                .map(corner -> CanvasRectUtils.getTopLeftCornerOfRectContainingPoint(DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS, corner))
                .map(topLeftCorner -> toMazeCoordinate(topLeftCorner, DirectionsE.STILL))
                .distinct()
                .toList();
    }



    public static DirectionsE getMovementDir(CanvasCoordinate from, MazeCoordinate to) {
        final CanvasCoordinate canvasToCord = MazeUtil.mazeCordToCanvasCord(to.getRow(), to.getCol());

        if(canvasToCord.getRow() > from.getRow()) {
            return DirectionsE.DOWN;
        } else if(canvasToCord.getRow() < from.getRow()) {
            return DirectionsE.UP;
        } else if(canvasToCord.getCol() > from.getCol()) {
            return DirectionsE.RIGHT;
        } else if(canvasToCord.getCol() < from.getCol()) {
            return DirectionsE.LEFT;
        } else {
            return DirectionsE.STILL;
        }
    }

    public static MazeCoordinate toMazeCoordinate(CanvasCoordinate cord, DirectionsE dir) {
        switch (dir) {
            case RIGHT, DOWN:
                return toMazeCoordinateFlooring(cord);
            case LEFT, UP:
                return toMazeCoordinateCeiling(cord);
            default:
                return toMazeCoordinateFlooring(cord);
        }
    }

    private static MazeCoordinate toMazeCoordinateFlooring(CanvasCoordinate cord) {
        final int mazeRow = (int) Math.floor(cord.getRow() / DimensionsC.MAZE_CELL_SIZE_PIXELS);
        final int mazeCol = (int) Math.floor(cord.getCol() / DimensionsC.MAZE_CELL_SIZE_PIXELS);
        return new MazeCoordinate(mazeRow, mazeCol);
    }

    private static MazeCoordinate toMazeCoordinateCeiling(CanvasCoordinate cord) {
        final int mazeRow = (int) Math.ceil(cord.getRow() / DimensionsC.MAZE_CELL_SIZE_PIXELS);
        final int mazeCol = (int) Math.ceil(cord.getCol() / DimensionsC.MAZE_CELL_SIZE_PIXELS);
        return new MazeCoordinate(mazeRow, mazeCol);
    }

}
