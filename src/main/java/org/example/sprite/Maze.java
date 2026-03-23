package org.example.sprite;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Getter;
import org.example.config.GameConfig;
import org.example.constant.ColorC;
import org.example.constant.DimensionsC;
import org.example.constant.SpriteE;
import org.example.entity.CanvasCoordinate;
import org.example.event.Event;
import org.example.maze.MazeGenerator;
import org.example.maze.MazeMatrix;
import org.example.maze.RecursiveDivisionMazeGenerator;
import org.example.util.MazeUtil;
import org.example.util.debug.DebugUtil;

public class Maze implements Sprite {


    @Override
    public void render(Canvas canvas) {
        final GraphicsContext con = canvas.getGraphicsContext2D();

        // set the maze background
        con.setFill(ColorC.CANVAS_COLOR);
        con.fillRect(0, 0, DimensionsC.CANVAS_WIDTH_PIXELS, DimensionsC.CANVAS_HEIGHT_PIXELS);

        // set the maze walls
        con.setFill(ColorC.CANVAS_WALL_COLOR);
        for (int mazeRow = 0; mazeRow< MazeMatrix.height(); mazeRow++) {
            for(int mazeCol = 0; mazeCol< MazeMatrix.width(); mazeCol++) {
                final CanvasCoordinate canvasCord = MazeUtil.getCanvasCord(mazeRow, mazeCol);
                if (MazeMatrix.get(mazeRow, mazeCol) == SpriteE.WALL) {
                    // map from the abstract maze scale to the graphical maze scale
                    con.setFill(ColorC.CANVAS_WALL_COLOR);
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
}
