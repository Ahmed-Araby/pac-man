package com.ahmedaraby.game.pacman.ghostmode.common;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.constant.SpriteFileNameC;
import com.ahmedaraby.game.pacman.ghostmode.navigation.TargetNavigator;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.entity.Vector;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import com.ahmedaraby.jengine.sprite.AssetRegistry;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import com.ahmedaraby.game.pacman.ghostmode.GhostMode;

public class Eaten extends GhostMode {
    private Coordinate ghostHouseEmptyLoc;
    private TargetNavigator targetNavigator;

    public Eaten(Ghost ghost, GameState gameState, ConfigsEx configs, AssetRegistry<String, Image> assetRegistry) {
        super(ghost, gameState, configs, assetRegistry,
                new double[]{configs.GHOST_ANIMATION_COMPLETE_DIST()});
        targetNavigator = new TargetNavigator(configs);
    }

    @Override
    public void init() {
        final double ghostHERow = gameState.getGhostHouseS().getERow();
        final double ghostHSCol = gameState.getGhostHouseS().getCol();
        ghostHouseEmptyLoc = new Coordinate(ghostHERow - configs.PLAYGROUND_CELL_SIZE(), ghostHSCol + configs.PLAYGROUND_CELL_SIZE());
    }

    @Override
    public void enter() {
        System.out.println("Eaten.enter() method need to do nothing");
    }


    @Override
    public boolean ended() {
        return ghost.getTopLeftCorner().equals(ghostHouseEmptyLoc);
    }

    @Override
    public void render(Canvas canvas) {
        canvas.getGraphicsContext2D().drawImage(animator.getFrame(), ghost.getCol(), ghost.getRow());
    }

    @Override
    public void move() {
        final Vector newDir = targetNavigator.calcDir(ghost, ghostHouseEmptyLoc);
        moveAt(newDir);
    }

    @Override
    protected Image[] loadSprites() {
        final String eatenUpFramePath = String.format(SpriteFileNameC.GHOST_SPRITE_PATH_TEMPLATE, SpriteFileNameC.GHOST_EATEN_FOLDER, SpriteFileNameC.GHOST_EATEN_UP_FRAME_FILE_NAME);
//        final String eatenRightFramePath = String.format(SpriteFileNameC.GHOST_SPRITE_PATH_TEMPLATE, SpriteFileNameC.GHOST_EATEN_FOLDER, SpriteFileNameC.GHOST_EATEN_RIGHT_FRAME_FILE_NAME);
//        final String eatenDownFramePath = String.format(SpriteFileNameC.GHOST_SPRITE_PATH_TEMPLATE, SpriteFileNameC.GHOST_EATEN_FOLDER, SpriteFileNameC.GHOST_EATEN_DOWN_FRAME_FILE_NAME);
//        final String eatenLeftFramePath = String.format(SpriteFileNameC.GHOST_SPRITE_PATH_TEMPLATE, SpriteFileNameC.GHOST_EATEN_FOLDER, SpriteFileNameC.GHOST_EATEN_LEFT_FRAME_FILE_NAME);

        final Image up = assetRegistry.get(eatenUpFramePath);
//        final Image right = assetRegistry.get(eatenRightFramePath);
//        final Image down = assetRegistry.get(eatenDownFramePath);
//        final Image left = assetRegistry.get(eatenLeftFramePath);

        return new Image[]{up};
    }
}
