package org.example.util;

import org.example.constant.Dimensions;
import org.example.constant.DirectionsE;
import org.example.entity.Coordinate;
import org.example.entity.MazeCoordinate;

import java.util.ArrayList;
import java.util.List;

public class CoordinateUtil {

    private static final int[] dRow = {-1, 1, 0, 0}; // up, down, don't change, don't change
    private static final int[] dCol = {0, 0, 1, -1}; // don't change, don't change, right, left

    public static List<MazeCoordinate> get90DegMoves(MazeCoordinate cord) {
        final List<MazeCoordinate> ninetyDegMoves = new ArrayList<>();
        int nRow, nCol;
        for(int i=0; i<4; i++) {
            nRow = cord.getRow() + dRow[i];
            nCol = cord.getCol() + dCol[i];
            if(nRow == -1 || nCol == -1 || nRow == Dimensions.MAZE_HEIGHT || nCol == Dimensions.MAZE_WIDTH) {
                continue;
            }
            ninetyDegMoves.add(new MazeCoordinate(nRow, nCol));
        }


        return ninetyDegMoves;
    }


    public static DirectionsE getMovementDir(MazeCoordinate from, MazeCoordinate to) {
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

    public static MazeCoordinate toMazeCoordinate(Coordinate cord, DirectionsE dir) {
        switch (dir) {
            case RIGHT, DOWN:
                return toMazeCoordinateFlooring(cord);
            case LEFT, UP:
                return toMazeCoordinateCeiling(cord);
            default:
                return toMazeCoordinateFlooring(cord);
        }
    }

    private static MazeCoordinate toMazeCoordinateFlooring(Coordinate cord) {
        final int mazeRow = (int) Math.floor(cord.getRow() / Dimensions.CANVAS_CELL_SIZE_PIXELS);
        final int mazeCol = (int) Math.floor(cord.getCol() / Dimensions.CANVAS_CELL_SIZE_PIXELS);
        return new MazeCoordinate(mazeRow, mazeCol);
    }

    private static MazeCoordinate toMazeCoordinateCeiling(Coordinate cord) {
        final int mazeRow = (int) Math.ceil(cord.getRow() / Dimensions.CANVAS_CELL_SIZE_PIXELS);
        final int mazeCol = (int) Math.ceil(cord.getCol() / Dimensions.CANVAS_CELL_SIZE_PIXELS);
        return new MazeCoordinate(mazeRow, mazeCol);
    }
}
