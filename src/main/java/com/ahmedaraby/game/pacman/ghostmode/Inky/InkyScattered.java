package com.ahmedaraby.game.pacman.ghostmode.Inky;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.constant.SpriteFileNameC;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.game.pacman.ghostmode.Scattered;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import com.ahmedaraby.jengine.sprite.AssetRegistry;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class InkyScattered extends Scattered {

    public InkyScattered(Ghost ghost, GameState gameState, ConfigsEx configs, AssetRegistry<String, Image> assetRegistry, int[] activePeriodsSec) {
        super(ghost, gameState, configs, assetRegistry, activePeriodsSec,
                new double[]{configs.GHOST_INKY_FIRST_FRAME_DISTANCE(), configs.GHOST_INKY_SECOND_FRAME_DISTANCE()});

        this.target = new Coordinate(  // top left corner of the bottom right cell
                configs.CANVAS_HEIGHT() - configs.PLAYGROUND_CELL_SIZE(),
                configs.CANVAS_WIDTH() - configs.PLAYGROUND_CELL_SIZE()
        );


    }
    @Override
    public void render(Canvas canvas) {
        final GraphicsContext con = canvas.getGraphicsContext2D();
        con.drawImage(animator.getFrame(), ghost.getCol(), ghost.getRow());
    }

    @Override
    protected Image[] loadSprites() {
        final String frame1Path = String.format(SpriteFileNameC.GHOST_SPRITE_PATH_TEMPLATE, SpriteFileNameC.INKY_FOLDER, SpriteFileNameC.INKY_FRAME_1_FILE_NAME);
        final String frame2Path = String.format(SpriteFileNameC.GHOST_SPRITE_PATH_TEMPLATE, SpriteFileNameC.INKY_FOLDER, SpriteFileNameC.INKY_FRAME_2_FILE_NAME);
        final Image frame1 = assetRegistry.get(frame1Path);
        final Image frame2 = assetRegistry.get(frame2Path);
        return new Image[] {frame1, frame2};
    }
}
