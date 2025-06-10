package org.example.util;

import org.example.constant.Dimensions;
import org.example.maze.Coordinate;

public class MazeCanvasCoordinateMapping {

    public static Coordinate mazeCordToCanvasCord(int mazeRow, int mazeCol) {
        return new Coordinate(mazeRow * Dimensions.CANVAS_CELL_SIZE_PIXELS, mazeCol * Dimensions.CANVAS_CELL_SIZE_PIXELS);
    }

    public static Coordinate mazeCordToCanvasCord(Coordinate mazeCord) {
        return new Coordinate(mazeCord.getRow() * Dimensions.CANVAS_CELL_SIZE_PIXELS, mazeCord.getCol() * Dimensions.CANVAS_CELL_SIZE_PIXELS);
    }

    public static Coordinate canvasCordToMazeCordCeiled(Coordinate canvasCord) {
        return new Coordinate(Math.ceil(canvasCord.getRow() / Dimensions.CANVAS_CELL_SIZE_PIXELS), Math.ceil(canvasCord.getCol() / Dimensions.CANVAS_CELL_SIZE_PIXELS));
    }

    public static Coordinate canvasCordToMazeCordFloored(Coordinate canvasCord) {
        return new Coordinate(Math.floor(canvasCord.getRow() / Dimensions.CANVAS_CELL_SIZE_PIXELS), Math.floor(canvasCord.getCol() / Dimensions.CANVAS_CELL_SIZE_PIXELS));
    }
}
