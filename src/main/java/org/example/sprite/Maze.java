package org.example.sprite;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Getter;
import org.example.config.GameConfig;
import org.example.constant.ColorConstants;
import org.example.constant.DimensionsC;
import org.example.constant.SpriteE;
import org.example.entity.CanvasCoordinate;
import org.example.event.Event;
import org.example.maze.MazeGenerator;
import org.example.maze.RecursiveDivisionMazeGenerator;
import org.example.util.MazeUtil;
import org.example.util.debug.DebugUtil;

public class Maze implements Sprite {
    @Getter
    private SpriteE[][] gameMaze;

    public Maze() {
        final MazeGenerator recursiveDivisionMazeGen = new RecursiveDivisionMazeGenerator();
        final boolean[][] booleanMaze = recursiveDivisionMazeGen.generateMaze(DimensionsC.MAZE_HEIGHT, DimensionsC.MAZE_WIDTH, DimensionsC.MAZE_CHAMBER_MIN_WIDTH, DimensionsC.MAZE_CHAMBER_MIN_HEIGHT);
        gameMaze = new SpriteE[DimensionsC.MAZE_HEIGHT][DimensionsC.MAZE_WIDTH];
        for(int mazeRow = 0; mazeRow< DimensionsC.MAZE_HEIGHT; mazeRow++) {
            for(int mazeCol = 0; mazeCol< DimensionsC.MAZE_WIDTH; mazeCol++) {
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
        con.fillRect(0, 0, DimensionsC.CANVAS_WIDTH_PIXELS, DimensionsC.CANVAS_HEIGHT_PIXELS);

        // set the maze walls
        con.setFill(ColorConstants.CANVAS_WALL_COLOR);
        for (int mazeRow = 0; mazeRow< gameMaze.length; mazeRow++) {
            for(int mazeCol = 0; mazeCol< gameMaze[0].length; mazeCol++) {
                final CanvasCoordinate canvasCord = MazeUtil.mazeCordToCanvasCord(mazeRow, mazeCol);
                if (gameMaze[mazeRow][mazeCol] == SpriteE.WALL) {
                    // map from the abstract maze scale to the graphical maze scale
                    con.setFill(ColorConstants.CANVAS_WALL_COLOR);
                    con.fillRect(canvasCord.getCol(), canvasCord.getRow(), DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS);
                    if (GameConfig.isDebugModeOn()) {
                        DebugUtil.drawVirtualRect(con, canvasCord.getCol(), canvasCord.getRow(), DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS, Color.RED);

                    }
                } else {
                    if(GameConfig.isDebugModeOn()) {
                        DebugUtil.drawVirtualRect(con, canvasCord.getCol(), canvasCord.getRow(), DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS, Color.YELLOW);
                    }
                }
            }
        }
    }

    @Override
    public void move(Event event) {
        throw new UnsupportedOperationException();
    }

    public CanvasCoordinate getEmptyMazePosition() {
        for (int mazeRow = 0; mazeRow< gameMaze.length; mazeRow++) {
            for (int mazeCol = 0; mazeCol< gameMaze[0].length; mazeCol++) {
                if (gameMaze[mazeRow][mazeCol] == SpriteE.EMPTY) {
                    gameMaze[mazeRow][mazeCol] = SpriteE.PAC_MAN;
                    return new CanvasCoordinate(mazeRow, mazeCol);
                }
            }
        }
        return null;
    }


    public SpriteE[][] getGameMaze() {
        return this.gameMaze;
    }
}
