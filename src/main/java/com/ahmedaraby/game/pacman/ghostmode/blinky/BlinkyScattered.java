package com.ahmedaraby.game.pacman.ghostmode.blinky;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import com.ahmedaraby.jengine.sprite.SpriteRegistry;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import com.ahmedaraby.jengine.animation.DistanceBasedAnimator;
import com.ahmedaraby.game.pacman.constant.SpriteFileNameC;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.game.pacman.ghostmode.Scattered;
import com.ahmedaraby.game.pacman.ghostmode.navigation.ShortestPathNavigator;

public class BlinkyScattered extends Scattered {

    public BlinkyScattered(Ghost ghost, GameState gameState, ConfigsEx configs, SpriteRegistry<String, Image> spriteRegistry, int[] activePeriodsSec) {
        super(ghost, gameState, configs, spriteRegistry, activePeriodsSec);
        this.target = new Coordinate(
                0,
                configs.CANVAS_WIDTH() - 1
        );
        final Image[] frames = loadSprites();
        this.animator = new DistanceBasedAnimator(
                new double[]{configs.GHOST_BLINK_FIRST_FRAME_DISTANCE(), configs.GHOST_BLINK_SECOND_FRAME_DISTANCE()}, frames);
        this.navigator = new ShortestPathNavigator();
    }

    @Override
    public void render(Canvas canvas) {
        final GraphicsContext con = canvas.getGraphicsContext2D();
        con.drawImage(animator.getFrame(), ghost.getCol(), ghost.getRow());
    }


    private Image[] loadSprites() {
        final String frame1Path = String.format(SpriteFileNameC.GHOST_SPRITE_PATH_TEMPLATE, SpriteFileNameC.BLINKY_FOLDER, SpriteFileNameC.BLINKY_FRAME_1_FILE_NAME);
        final String frame2Path = String.format(SpriteFileNameC.GHOST_SPRITE_PATH_TEMPLATE, SpriteFileNameC.BLINKY_FOLDER, SpriteFileNameC.BLINKY_FRAME_2_FILE_NAME);
        final Image frame1 = spriteRegistry.get(frame1Path);
        final Image frame2 = spriteRegistry.get(frame2Path);
        return new Image[]{frame1, frame2};
    }
}
