package org.example.collision;

import org.example.constant.Dimensions;
import org.example.constant.DirectionsE;
import org.example.constant.MazeCellContentE;
import org.example.maze.Coordinate;

public class PacManToWallCollisionDetection {
    private MazeCellContentE[][] maze;

    public PacManToWallCollisionDetection(MazeCellContentE[][] maze) {
        this.maze = maze;
    }

    public boolean isAboutToCollide(Coordinate pacManCanvasCord, DirectionsE direction) {
        return isPacManGoingOutOfCanvas(pacManCanvasCord);
    }

    private boolean isPacManGoingOutOfCanvas(Coordinate pacManCanvasCord) {
        return pacManCanvasCord.getRow() < 0 || pacManCanvasCord.getRow() >= Dimensions.CANVAS_HEIGHT_PIXELS - Dimensions.PAC_MAN_DIAMETER ||
                pacManCanvasCord.getCol() < 0 || pacManCanvasCord.getCol() >= Dimensions.CANVAS_WIDTH_PIXELS - Dimensions.PAC_MAN_DIAMETER;
    }
}
