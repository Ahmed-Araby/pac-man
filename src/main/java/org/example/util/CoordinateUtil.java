package org.example.util;

import org.example.constant.Dimensions;
import org.example.constant.DirectionsE;
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
}
