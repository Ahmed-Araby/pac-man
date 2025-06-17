package org.example.sprite;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Getter;
import org.example.constant.ColorConstants;
import org.example.constant.Dimensions;
import org.example.constant.SpriteE;
import org.example.entity.Coordinate;
import org.example.event.Event;
import org.example.maze.MazeGenerator;
import org.example.maze.RecursiveDivisionMazeGenerator;
import org.example.util.MazeCanvasCoordinateMapping;

public class Maze implements Sprite {
    @Getter
    private SpriteE[][] gameMaze;

    public Maze() {
        final MazeGenerator recursiveDivisionMazeGen = new RecursiveDivisionMazeGenerator();
        final boolean[][] booleanMaze = recursiveDivisionMazeGen.generateMaze(Dimensions.MAZE_HEIGHT, Dimensions.MAZE_WIDTH, Dimensions.MAZE_CHAMBER_MIN_WIDTH, Dimensions.MAZE_CHAMBER_MIN_HEIGHT);
        gameMaze = new SpriteE[Dimensions.MAZE_HEIGHT][Dimensions.MAZE_WIDTH];
        for(int mazeRow=0; mazeRow<Dimensions.MAZE_HEIGHT; mazeRow++) {
            for(int mazeCol=0; mazeCol<Dimensions.MAZE_WIDTH; mazeCol++) {
                if (booleanMaze[mazeRow][mazeCol]) {
                    gameMaze[mazeRow][mazeCol] = SpriteE.WALL;
                } else {
                    gameMaze[mazeRow][mazeCol] = SpriteE.EMPTY;
                }
            }
        }
    }

    @Override
    public void render(Canvas canvas) {
        final GraphicsContext con = canvas.getGraphicsContext2D();

        // set the maze background
        con.setFill(ColorConstants.CANVAS_COLOR);
        con.fillRect(0, 0, Dimensions.CANVAS_WIDTH_PIXELS, Dimensions.CANVAS_HEIGHT_PIXELS);

        // set the maze walls
        con.setFill(ColorConstants.CANVAS_WALL_COLOR);
        for (int mazeRow = 0; mazeRow< gameMaze.length; mazeRow++) {
            for(int mazeCol = 0; mazeCol< gameMaze[0].length; mazeCol++) {
                if (gameMaze[mazeRow][mazeCol] == SpriteE.WALL) {
                    // map from the abstract maze scale to the graphical maze scale
                    final Coordinate canvasCord = MazeCanvasCoordinateMapping.mazeCordToCanvasCord(mazeRow, mazeCol);
                    con.setFill(ColorConstants.CANVAS_WALL_COLOR);
                    con.fillRect(canvasCord.getCol(), canvasCord.getRow(), Dimensions.CANVAS_CELL_SIZE_PIXELS, Dimensions.CANVAS_CELL_SIZE_PIXELS);
                    con.setStroke(Color.RED);
                    con.strokeRect(canvasCord.getCol(), canvasCord.getRow(), Dimensions.CANVAS_CELL_SIZE_PIXELS, Dimensions.CANVAS_CELL_SIZE_PIXELS);
                } else {
                    // debug mode
                    final Coordinate canvasCord = MazeCanvasCoordinateMapping.mazeCordToCanvasCord(mazeRow, mazeCol);
                    con.setStroke(Color.YELLOW);
                    con.strokeRect(canvasCord.getCol(), canvasCord.getRow(), Dimensions.CANVAS_CELL_SIZE_PIXELS, Dimensions.CANVAS_CELL_SIZE_PIXELS);
                }
            }
        }
    }

    @Override
    public void move(Event event) {
        throw new UnsupportedOperationException();
    }

    public Coordinate getEmptyMazePosition() {
        for (int mazeRow = 0; mazeRow< gameMaze.length; mazeRow++) {
            for (int mazeCol = 0; mazeCol< gameMaze[0].length; mazeCol++) {
                if (gameMaze[mazeRow][mazeCol] == SpriteE.EMPTY) {
                    gameMaze[mazeRow][mazeCol] = SpriteE.PAC_MAN;
                    return new Coordinate(mazeRow, mazeCol);
                }
            }
        }
        return null;
    }
}
