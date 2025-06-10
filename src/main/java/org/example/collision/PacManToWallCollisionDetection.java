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

    public boolean isAboutToCollide(Coordinate pacManTopLeftCorner, DirectionsE direction) {
        Coordinate nextCellTopLeftCorner;

        switch (direction) {
            case UP:
                nextCellTopLeftCorner = new Coordinate(pacManTopLeftCorner.getRow() - Dimensions.CANVAS_CELL_SIZE_PIXELS, pacManTopLeftCorner.getCol());
                break;
            case DOWN:
                nextCellTopLeftCorner = new Coordinate(pacManTopLeftCorner.getRow() + Dimensions.CANVAS_CELL_SIZE_PIXELS, pacManTopLeftCorner.getCol());
                break;
            case RIGHT:
                nextCellTopLeftCorner = new Coordinate(pacManTopLeftCorner.getRow(), pacManTopLeftCorner.getCol() + 1);
                break;
            case LEFT:
                nextCellTopLeftCorner = new Coordinate(pacManTopLeftCorner.getRow(), pacManTopLeftCorner.getCol() - 1);
                break;
            default:
                nextCellTopLeftCorner = pacManTopLeftCorner;
        }

        final int abstractMazeRow = (int) Math.ceil(nextCellTopLeftCorner.getRow() / Dimensions.CANVAS_CELL_SIZE_PIXELS);
        final int abstractMazeCol = (int) Math.ceil(nextCellTopLeftCorner.getCol() / Dimensions.CANVAS_CELL_SIZE_PIXELS);
        if (abstractMazeRow < 0 || abstractMazeCol > maze.length || abstractMazeCol < 0 || abstractMazeCol > maze[0].length) {
            return false;
        } else {
            return maze[abstractMazeRow][abstractMazeCol] == MazeCellContentE.WALL;
        }
    }
}
