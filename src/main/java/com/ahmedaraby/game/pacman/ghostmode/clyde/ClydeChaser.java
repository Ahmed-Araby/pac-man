package com.ahmedaraby.game.pacman.ghostmode.clyde;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.constant.SpriteFileNameC;
import com.ahmedaraby.game.pacman.ghostmode.Chaser;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import com.ahmedaraby.jengine.sprite.SpriteRegistry;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ClydeChaser extends Chaser {


    public ClydeChaser(Ghost ghost, GameState gameState, ConfigsEx configs, SpriteRegistry<String, Image> spriteRegistry, int[] activePeriodsSec) {
        super(ghost, gameState, configs, spriteRegistry, activePeriodsSec,
                new double[]{configs.GHOST_CLYDE_FIRST_FRAME_DISTANCE(), configs.GHOST_CLYDE_SECOND_FRAME_DISTANCE()});
    }

    @Override
    public void render(Canvas canvas) {
        final GraphicsContext con = canvas.getGraphicsContext2D();
        con.drawImage(animator.getFrame(), ghost.getCol(), ghost.getRow());
    }

    @Override
    public void move() {
        moveTo(gameState.getPacMan());
    }

    @Override
    protected Image[] loadSprites() {
        final String frame1Path = String.format(SpriteFileNameC.GHOST_SPRITE_PATH_TEMPLATE, SpriteFileNameC.CLYDE_FOLDER, SpriteFileNameC.CLYDE_FRAME_1_FILE_NAME);
        final String frame2Path = String.format(SpriteFileNameC.GHOST_SPRITE_PATH_TEMPLATE, SpriteFileNameC.CLYDE_FOLDER, SpriteFileNameC.CLYDE_FRAME_2_FILE_NAME);
        final Image frame1Img = spriteRegistry.get(frame1Path);
        final Image frame2Img = spriteRegistry.get(frame2Path);
        return new Image[]{frame1Img, frame2Img};
    }
}
