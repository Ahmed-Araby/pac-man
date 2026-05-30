package com.ahmedaraby.game.pacman.sprite.playground;

import com.ahmedaraby.game.pacman.model.GameState;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import com.ahmedaraby.game.pacman.config.GameConfig;
import com.ahmedaraby.game.pacman.constant.ColorC;
import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.game.pacman.entity.Coordinate;
import com.ahmedaraby.game.pacman.maze.Playground;
import com.ahmedaraby.game.pacman.sprite.Sprite;
import com.ahmedaraby.game.pacman.util.MazeUtil;
import com.ahmedaraby.game.pacman.util.debug.DebugUtil;

public class Maze extends Sprite {


    public Maze(GameState gameState) {
        super(gameState, SpriteE.MAZE, new Coordinate(0, 0), DimensionsC.MAZE_WIDTH, DimensionsC.MAZE_HEIGHT);
    }

    @Override
    public void render(Canvas canvas) {
        final GraphicsContext con = canvas.getGraphicsContext2D();

        // set the maze background
        con.setFill(ColorC.CANVAS_COLOR);
        con.fillRect(0, 0, DimensionsC.CANVAS_WIDTH_PIXELS, DimensionsC.CANVAS_HEIGHT_PIXELS);

        // set the maze walls
        con.setFill(ColorC.CANVAS_WALL_COLOR);
        for (int mazeRow = 0; mazeRow< Playground.height(); mazeRow++) {
            for(int mazeCol = 0; mazeCol< Playground.width(); mazeCol++) {
                final Coordinate canvasCord = MazeUtil.getCanvasCord(mazeRow, mazeCol);
                if (Playground.get(mazeRow, mazeCol) == SpriteE.WALL) {
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
}
