package org.example.util;

import org.example.constant.DimensionsC;
import org.example.entity.CanvasCoordinate;
import org.example.entity.MazeCoordinate;

import java.util.ArrayList;
import java.util.List;

public class MazeUtil {

    private MazeUtil() {}

    private static final int[] dRow = {-1, 1, 0, 0}; // up, down, don't change, don't change
    private static final int[] dCol = {0, 0, 1, -1}; // don't change, don't change, right, left

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

    public static CanvasCoordinate getCanvasCord(MazeCoordinate cord) {
        return getCanvasCord(cord.getRow(), cord.getCol());
    }

    public static CanvasCoordinate getCanvasCord(int mazeRow, int mazeCol) {
        return new CanvasCoordinate(mazeRow * DimensionsC.MAZE_CELL_SIZE_PIXELS, mazeCol * DimensionsC.MAZE_CELL_SIZE_PIXELS);
    }
}
