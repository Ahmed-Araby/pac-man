package com.ahmedaraby.game.pacman.sprite.playground;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.model.GameState;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import com.ahmedaraby.game.pacman.config.GameConfig;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.game.pacman.playground.Playground;
import com.ahmedaraby.game.pacman.sprite.Sprite;
import com.ahmedaraby.game.pacman.util.MazeUtil;
import com.ahmedaraby.game.pacman.util.debug.DebugUtil;

public class Maze extends Sprite {


    public Maze(GameState gameState, ConfigsEx configs) {
        super(gameState, configs, SpriteE.MAZE, new Coordinate(0, 0), configs.CANVAS_WIDTH(), configs.CANVAS_HEIGHT());
    }

    @Override
    public void render(Canvas canvas) {
        final GraphicsContext con = canvas.getGraphicsContext2D();

        // set the maze background
        con.setFill(configs.PLAYGROUND_BACKGROUND_COLOR());
        con.fillRect(0, 0, configs.CANVAS_WIDTH(), configs.CANVAS_HEIGHT());

        // set the maze walls
        con.setFill(configs.PLAYGROUND_WALL_COLOR());
        final double MAZE_CELL_SIZE = configs.PLAYGROUND_CELL_SIZE();
        for (int mazeRow = 0; mazeRow< Playground.height(); mazeRow++) {
            for(int mazeCol = 0; mazeCol< Playground.width(); mazeCol++) {
                final Coordinate canvasCord = MazeUtil.getCanvasCord(mazeRow, mazeCol);
                if (Playground.get(mazeRow, mazeCol) == SpriteE.WALL) {
                    // map from the abstract maze scale to the graphical maze scale

                    con.setFill(configs.PLAYGROUND_WALL_COLOR());
                    con.fillRect(canvasCord.getCol(), canvasCord.getRow(), MAZE_CELL_SIZE, MAZE_CELL_SIZE);
                    if (GameConfig.isDebugModeOn()) {
                        DebugUtil.drawVirtualRect(con, canvasCord.getCol(), canvasCord.getRow(), MAZE_CELL_SIZE, MAZE_CELL_SIZE, Color.RED);

                    }
                } else {
                    if(GameConfig.isDebugModeOn()) {
                        DebugUtil.drawVirtualRect(con, canvasCord.getCol(), canvasCord.getRow(), MAZE_CELL_SIZE, MAZE_CELL_SIZE, Color.YELLOW);
                    }
                }
            }
        }
    }
}
