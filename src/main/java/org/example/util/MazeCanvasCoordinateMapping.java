package org.example.util;

import org.example.constant.Dimensions;
import org.example.entity.Coordinate;

public class MazeCanvasCoordinateMapping {

    public static Coordinate mazeCordToCanvasCord(int mazeRow, int mazeCol) {
        return new Coordinate(mazeRow * Dimensions.CANVAS_CELL_SIZE_PIXELS, mazeCol * Dimensions.CANVAS_CELL_SIZE_PIXELS);
    }
}
