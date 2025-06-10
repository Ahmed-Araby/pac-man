package org.example.collision;

import org.example.constant.Dimensions;
import org.example.constant.DirectionsE;
import org.example.constant.MazeCellContentE;
import org.example.maze.Coordinate;
import org.example.util.MazeCanvasCoordinateMapping;

public class PacManToWallCollisionDetection {
    private MazeCellContentE[][] maze;

    public PacManToWallCollisionDetection(MazeCellContentE[][] maze) {
        this.maze = maze;
    }

    public boolean isAboutToCollide(Coordinate pacManCanvasCord, DirectionsE direction) {

        return isPacManGoingOutOfCanvas(pacManCanvasCord) || isNextCellWall(pacManCanvasCord);
    }

    private boolean isPacManGoingOutOfCanvas(Coordinate pacManCanvasCord) {
        return pacManCanvasCord.getRow() < 0 || pacManCanvasCord.getRow() >= Dimensions.CANVAS_HEIGHT_PIXELS - Dimensions.PAC_MAN_DIAMETER_PIXELS ||
                pacManCanvasCord.getCol() < 0 || pacManCanvasCord.getCol() >= Dimensions.CANVAS_WIDTH_PIXELS - Dimensions.PAC_MAN_DIAMETER_PIXELS;
    }

    private boolean isNextCellWall(Coordinate pacManCanvasCord) {
        final Coordinate nextPacManCanvasCellCoordinates = getNextPacManCanvasCellCoordinates(pacManCanvasCord);
        final Coordinate nextPacManMazeCellCoordinates = MazeCanvasCoordinateMapping.canvasCordToMazeCordFloored(nextPacManCanvasCellCoordinates);
        return maze[(int) nextPacManMazeCellCoordinates.getRow()][(int) nextPacManMazeCellCoordinates.getCol()] == MazeCellContentE.WALL;
    }
    private Coordinate getNextPacManCanvasCellCoordinates(Coordinate pacManCanvasCord) {
        final double nextPacManCellCanvasRow = getNextCellCanvasRow(pacManCanvasCord.getRow());
        final double nextPacManCellCanvasCol = getNextCellCanvasCol(pacManCanvasCord.getCol());
        return new Coordinate(nextPacManCellCanvasRow, nextPacManCellCanvasCol);
    }
    private double getNextCellCanvasRow(double currentPacManCanvasRow) {
        if (currentPacManCanvasRow % Dimensions.CANVAS_CELL_SIZE_PIXELS != 0) {
            return currentPacManCanvasRow + Dimensions.CANVAS_CELL_SIZE_PIXELS;
        }
        return currentPacManCanvasRow;
    }

    private double getNextCellCanvasCol(double currentPacManCanvasCol) {
        if (currentPacManCanvasCol % Dimensions.CANVAS_CELL_SIZE_PIXELS != 0) {
            return currentPacManCanvasCol + Dimensions.CANVAS_CELL_SIZE_PIXELS;
        }
        return currentPacManCanvasCol;
    }
}
