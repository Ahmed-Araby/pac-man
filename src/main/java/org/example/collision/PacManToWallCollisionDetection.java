package org.example.collision;

import org.example.constant.Dimensions;
import org.example.constant.MazeCellContentE;
import org.example.maze.Coordinate;
import org.example.util.MazeCanvasCoordinateMapping;
import org.example.util.RectUtils;

import java.util.List;

public class PacManToWallCollisionDetection {
    private MazeCellContentE[][] maze;

    public PacManToWallCollisionDetection(MazeCellContentE[][] maze) {
        this.maze = maze;
    }

    public boolean isAboutToCollide(Coordinate pacManCanvasTopLeftCorner) {
        if (isPacManGoingOutOfCanvas(pacManCanvasTopLeftCorner)) {
            return true;
        }

        final List<Coordinate> pacManRect4Corners = RectUtils.get4Corners(pacManCanvasTopLeftCorner, Dimensions.PAC_MAN_DIAMETER_PIXELS, Dimensions.PAC_MAN_DIAMETER_PIXELS);
        return pacManRect4Corners.stream()
                .map(pacManCanvasRectCorner -> RectUtils.getTopLeftCornerOfRectContainingPoint(Dimensions.CANVAS_CELL_SIZE_PIXELS, Dimensions.CANVAS_CELL_SIZE_PIXELS, pacManCanvasRectCorner))
                .anyMatch(this::isPacManCollidingWithAWall);
    }

    private boolean isPacManGoingOutOfCanvas(Coordinate pacManCanvasTopLeftCorner) {
        return pacManCanvasTopLeftCorner.getRow() < 0 || pacManCanvasTopLeftCorner.getRow() >= Dimensions.CANVAS_HEIGHT_PIXELS - Dimensions.PAC_MAN_DIAMETER_PIXELS ||
                pacManCanvasTopLeftCorner.getCol() < 0 || pacManCanvasTopLeftCorner.getCol() >= Dimensions.CANVAS_WIDTH_PIXELS - Dimensions.PAC_MAN_DIAMETER_PIXELS;
    }

    private boolean isPacManCollidingWithAWall(Coordinate wallCanvasTopLeftCorner) {
        final Coordinate wallMazeCord = MazeCanvasCoordinateMapping.canvasCordToMazeCordFloored(wallCanvasTopLeftCorner);
        return maze[(int) wallMazeCord.getRow()][(int) wallMazeCord.getCol()] == MazeCellContentE.WALL;
    }
}
