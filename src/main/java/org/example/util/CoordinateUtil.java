package org.example.util;

import org.example.constant.DimensionsC;
import org.example.constant.DirectionsE;
import org.example.entity.CanvasCoordinate;
import org.example.entity.MazeCoordinate;
import org.example.entity.Rect;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CoordinateUtil {

    private static final int[] dRow = {-1, 1, 0, 0}; // up, down, don't change, don't change
    private static final int[] dCol = {0, 0, 1, -1}; // don't change, don't change, right, left

    public static List<MazeCoordinate> getIntersectingMazeCells(CanvasCoordinate cord) {
        final Rect canvasRect = new Rect(cord, DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS);
        final List<CanvasCoordinate> rectCorners = RectUtils.get4Corners(canvasRect);
        return rectCorners
                .stream()
                .map(corner -> RectUtils.getTopLeftCornerOfRectContainingPoint(DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS, corner))
                .map(topLeftCorner -> toMazeCoordinate(topLeftCorner, DirectionsE.STILL))
                .distinct()
                .toList();
    }
    
    public static List<MazeCoordinate> get90DegMoves(MazeCoordinate cord) {
        final List<MazeCoordinate> ninetyDegMoves = new ArrayList<>();
        int nRow, nCol;
        for(int i=0; i<4; i++) {
            nRow = cord.getRow() + dRow[i];
            nCol = cord.getCol() + dCol[i];
            if(nRow == -1 || nCol == -1 || nRow == DimensionsC.MAZE_HEIGHT || nCol == DimensionsC.MAZE_WIDTH) {
                continue;
            }
            ninetyDegMoves.add(new MazeCoordinate(nRow, nCol));
        }


        return ninetyDegMoves;
    }

    public static DirectionsE getMovementDir(CanvasCoordinate from, MazeCoordinate to) {
        final CanvasCoordinate canvasToCord = mazeCordToCanvasCord(to.getRow(), to.getCol());

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

    public static CanvasCoordinate mazeCordToCanvasCord(int mazeRow, int mazeCol) {
        return new CanvasCoordinate(mazeRow * DimensionsC.MAZE_CELL_SIZE_PIXELS, mazeCol * DimensionsC.MAZE_CELL_SIZE_PIXELS);
    }
}
