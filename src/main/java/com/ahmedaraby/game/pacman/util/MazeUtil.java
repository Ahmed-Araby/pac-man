package com.ahmedaraby.game.pacman.util;

import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.game.pacman.entity.MazeCell;

import java.util.ArrayList;
import java.util.List;

public class MazeUtil {

    private MazeUtil() {}

    public static Coordinate getCanvasCord(MazeCell cord) {
        return getCanvasCord(cord.getRow(), cord.getCol());
    }

    public static Coordinate getCanvasCord(int mazeRow, int mazeCol) {
        return new Coordinate(mazeRow * DimensionsC.MAZE_CELL_SIZE_PIXELS, mazeCol * DimensionsC.MAZE_CELL_SIZE_PIXELS);
    }
}
