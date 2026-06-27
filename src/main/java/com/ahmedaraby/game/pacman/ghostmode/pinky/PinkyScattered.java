package com.ahmedaraby.game.pacman.ghostmode.pinky;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.constant.SpriteFileNameC;
import com.ahmedaraby.game.pacman.ghostmode.Scattered;
import com.ahmedaraby.game.pacman.ghostmode.navigation.ShortestPathNavigator;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import com.ahmedaraby.jengine.animation.DistanceBasedAnimator;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.sprite.SpriteRegistry;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class PinkyScattered extends Scattered {

    public PinkyScattered(Ghost ghost, GameState gameState, ConfigsEx configs, SpriteRegistry<String, Image> spriteRegistry, int[] activePeriodsSec) {
        super(ghost, gameState, configs, spriteRegistry, activePeriodsSec);

        this.target = new Coordinate(0, 0); // top left corner
        final Image[] frames = loadSprites();
        this.animator = new DistanceBasedAnimator(new double[]{
                configs.GHOST_PINKY_FIRST_FRAME_DISTANCE(),
                configs.GHOST_PINKY_SECOND_FRAME_DISTANCE()
        }, frames);
        this.navigator = new ShortestPathNavigator();
    }

    @Override
    public void render(Canvas canvas) {
        GraphicsContext con = canvas.getGraphicsContext2D();
        con.drawImage(animator.getFrame(), ghost.getCol(), ghost.getRow());
    }

    private Image[] loadSprites() {
        final String frame1Path = String.format(SpriteFileNameC.GHOST_SPRITE_PATH_TEMPLATE, SpriteFileNameC.PINKY_FOLDER, SpriteFileNameC.PINKY_FRAME_1_FILE_NAME);
        final String frame2Path = String.format(SpriteFileNameC.GHOST_SPRITE_PATH_TEMPLATE, SpriteFileNameC.PINKY_FOLDER, SpriteFileNameC.PINKY_FRAME_2_FILE_NAME);
        final Image frame1 = spriteRegistry.get(frame1Path);
        final Image frame2 = spriteRegistry.get(frame2Path);
        return new Image[] {frame1, frame2};
    }
}
