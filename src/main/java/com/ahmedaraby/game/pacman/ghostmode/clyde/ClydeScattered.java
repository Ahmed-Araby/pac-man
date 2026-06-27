package com.ahmedaraby.game.pacman.ghostmode.clyde;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.constant.SpriteFileNameC;
import com.ahmedaraby.game.pacman.ghostmode.Scattered;
import com.ahmedaraby.game.pacman.ghostmode.navigation.ShortestPathNavigator;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import com.ahmedaraby.jengine.animation.DistanceBasedAnimator;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.sprite.SpriteRegistry;
import javafx.scene.image.Image;

public class ClydeScattered extends Scattered {

    public ClydeScattered(Ghost ghost, GameState gameState, ConfigsEx configs, SpriteRegistry<String, Image> spriteRegistry, int[] activePeriodsSec) {
        super(ghost, gameState, configs, spriteRegistry, activePeriodsSec);

        final String frame1Path = String.format(SpriteFileNameC.GHOST_SPRITE_PATH_TEMPLATE, SpriteFileNameC.CLYDE_FOLDER, SpriteFileNameC.CLYDE_FRAME_1_FILE_NAME);
        final String frame2Path = String.format(SpriteFileNameC.GHOST_SPRITE_PATH_TEMPLATE, SpriteFileNameC.CLYDE_FOLDER, SpriteFileNameC.CLYDE_FRAME_2_FILE_NAME);
        final Image[] frames = loadSprites(new String[]{frame1Path, frame2Path});

        super.navigator = new ShortestPathNavigator();
        super.animator = new DistanceBasedAnimator(new double[]{
                configs.GHOST_CLYDE_FIRST_FRAME_DISTANCE(),
                configs.GHOST_CLYDE_SECOND_FRAME_DISTANCE()
        }, frames);

        target = new Coordinate(configs.CANVAS_HEIGHT() - configs.PLAYGROUND_CELL_SIZE(), 0);
    }
}
