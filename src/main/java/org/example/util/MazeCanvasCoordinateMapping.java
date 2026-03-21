package org.example.util;

import org.example.constant.Dimensions;
import org.example.entity.Coordinate;
import org.example.entity.MazeCoordinate;

public class MazeCanvasCoordinateMapping {

    public static Coordinate mazeCordToCanvasCord(int mazeRow, int mazeCol) {
        return new Coordinate(mazeRow * Dimensions.CANVAS_CELL_SIZE_PIXELS, mazeCol * Dimensions.CANVAS_CELL_SIZE_PIXELS);
    }

    public static MazeCoordinate canvasCordToMazeCordFloored(Coordinate canvasCord) {
        return new MazeCoordinate((int) Math.floor(canvasCord.getRow() / Dimensions.CANVAS_CELL_SIZE_PIXELS), (int) Math.floor(canvasCord.getCol() / Dimensions.CANVAS_CELL_SIZE_PIXELS));
    }

}
