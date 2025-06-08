package org.example.entity;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import org.example.constant.Dimensions;
import org.example.constant.MazeCellContentE;
import org.example.maze.MazeGenerator;
import org.example.maze.RecursiveDivisionMazeGenerator;

public class Maze implements Sprite {
    private MazeCellContentE[][] maze;

    public Maze() {
        final int mazeGridHeight = (int) (Dimensions.CANVAS_HEIGHT / Dimensions.MAZE_CELL_SIZE);
        final int mazeGridWidth = (int) (Dimensions.CANVAS_WIDTH / Dimensions.MAZE_CELL_SIZE);

        final int mazeGridMinHeight = (int) (Dimensions.MAZE_CHAMBER_MIN_HEIGHT / Dimensions.MAZE_CELL_SIZE);
        final int mazeGridMinWidth = (int) (Dimensions.MAZE_CHAMBER_MIN_WIDTH / Dimensions.MAZE_CELL_SIZE);

        final MazeGenerator recursiveDivisionMazeGen = new RecursiveDivisionMazeGenerator();
        final boolean[][] booleanMaze = recursiveDivisionMazeGen.generateMaze(mazeGridHeight, mazeGridWidth, mazeGridMinWidth, mazeGridMinHeight);
        maze = new MazeCellContentE[mazeGridHeight][mazeGridWidth];
        for(int i=0; i<mazeGridHeight; i++) {
            for(int j=0; j<mazeGridWidth; j++) {
                if (booleanMaze[i][j]) {
                    maze[i][j] = MazeCellContentE.WALL;
                } else {
                    maze[i][j] = MazeCellContentE.EMPTY;
                }
            }
        }
    }

    @Override
    public void render(Canvas canvas) {
        final GraphicsContext con = canvas.getGraphicsContext2D();
        con.setFill(Color.BLUE);

        for (int i=0; i<maze.length; i++) {
            for(int j=0; j<maze[0].length; j++) {
                if (maze[i][j] == MazeCellContentE.WALL) {
                    int canvasX = (int) (i * Dimensions.MAZE_CELL_SIZE);
                    int canvasY = (int) (j * Dimensions.MAZE_CELL_SIZE);
                    con.fillRect(canvasX, canvasY, Dimensions.MAZE_CELL_SIZE, Dimensions.MAZE_CELL_SIZE);
                }
            }
        }
    }

    @Override
    public void move(KeyEvent event) {
        throw new UnsupportedOperationException();
    }
}
