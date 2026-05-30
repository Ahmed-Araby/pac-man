package com.ahmedaraby.game.pacman.util.canvas;

import com.ahmedaraby.game.pacman.entity.MazeCell;
import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.entity.Rectangle;
import com.ahmedaraby.game.pacman.util.MazeUtil;

import java.util.List;

public class CanvasUtil {

    public static boolean inCanvas(Coordinate cord) {
        return cord.getCol() >=0 && cord.getCol() < DimensionsC.CANVAS_WIDTH_PIXELS
                && cord.getRow() >= 0 && cord.getRow() < DimensionsC.CANVAS_HEIGHT_PIXELS;
    }
    public static List<MazeCell> getIntersectingMazeCells(Coordinate cord) {
        final Rectangle rectangle = new Rectangle(cord, DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS);
        final List<Coordinate> rectCorners = CanvasRectUtils.get4Corners(rectangle);
        return rectCorners
                .stream()
                .map(corner -> CanvasRectUtils.getTopLeftCornerOfRectContainingPoint(DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS, corner))
                .map(topLeftCorner -> toMazeCoordinate(topLeftCorner, DirectionsE.STILL))
                .distinct()
                .toList();
    }

    public static List<MazeCell> get90DegAdjMazeCells(Coordinate cord) {
        final MazeCell mazeCell = CanvasUtil.toMazeCoordinate(cord, DirectionsE.STILL);
        return MazeUtil.get90DegMoves(mazeCell);
    }



    public static DirectionsE getMovementDir(Coordinate from, Coordinate to) {
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

    public static MazeCell toMazeCoordinate(Coordinate cord, DirectionsE dir) {
        switch (dir) {
            case RIGHT, DOWN:
                return toMazeCoordinateFlooring(cord);
            case LEFT, UP:
                return toMazeCoordinateCeiling(cord);
            default:
                return toMazeCoordinateFlooring(cord);
        }
    }

    private static MazeCell toMazeCoordinateFlooring(Coordinate cord) {
        final int mazeRow = (int) Math.floor(cord.getRow() / DimensionsC.MAZE_CELL_SIZE_PIXELS);
        final int mazeCol = (int) Math.floor(cord.getCol() / DimensionsC.MAZE_CELL_SIZE_PIXELS);
        return new MazeCell(mazeRow, mazeCol);
    }

    private static MazeCell toMazeCoordinateCeiling(Coordinate cord) {
        final int mazeRow = (int) Math.ceil(cord.getRow() / DimensionsC.MAZE_CELL_SIZE_PIXELS);
        final int mazeCol = (int) Math.ceil(cord.getCol() / DimensionsC.MAZE_CELL_SIZE_PIXELS);
        return new MazeCell(mazeRow, mazeCol);
    }

}
