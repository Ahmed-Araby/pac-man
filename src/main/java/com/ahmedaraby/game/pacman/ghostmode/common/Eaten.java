package com.ahmedaraby.game.pacman.ghostmode.common;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.constant.SpriteFileNameC;
import com.ahmedaraby.game.pacman.ghostmode.navigation.TargetNavigator;
import com.ahmedaraby.game.pacman.util.PlaygroundShortestPathNav;
import com.ahmedaraby.jengine.animation.Animator;
import com.ahmedaraby.jengine.animation.DistanceBasedAnimator;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.entity.Vector;
import com.ahmedaraby.game.pacman.ghostmode.navigation.GhostNavigator;
import com.ahmedaraby.game.pacman.ghostmode.navigation.ShortestPathNavigator;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import com.ahmedaraby.jengine.sprite.SpriteRegistry;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import com.ahmedaraby.game.pacman.ghostmode.GhostMode;

import java.util.*;

public class Eaten extends GhostMode {

    private final GhostNavigator navigator;
    private final Animator animator;
    private Coordinate ghostHouseEmptyLoc;
    private TargetNavigator targetNavigator;

    public Eaten(Ghost ghost, GameState gameState, ConfigsEx configs, SpriteRegistry<String, Image> spriteRegistry) {
        super(ghost, gameState, configs, spriteRegistry);
        PlaygroundShortestPathNav playgroundShortestPathNav = new PlaygroundShortestPathNav();
        this.navigator = new ShortestPathNavigator(configs, playgroundShortestPathNav);

        final Map<Vector, Image[]> sprites = loadSprites();
        this.animator = new DistanceBasedAnimator(
                new double[]{configs.GHOST_ANIMATION_COMPLETE_DIST()},
                sprites.get(Vector.UP)
        );
        targetNavigator = new TargetNavigator(configs, navigator);
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
        final double stride = ghost.calcCalibratedStride(newDir);
        ghost.move(stride, newDir);
        this.animator.stride(stride);
    }

    private Map<Vector, Image[]> loadSprites() {
        // [TODO] move sprites loading into a class that provide caching

        final String eatenUpFramePath = String.format(SpriteFileNameC.GHOST_SPRITE_PATH_TEMPLATE, SpriteFileNameC.GHOST_EATEN_FOLDER, SpriteFileNameC.GHOST_EATEN_UP_FRAME_FILE_NAME);
        final String eatenRightFramePath = String.format(SpriteFileNameC.GHOST_SPRITE_PATH_TEMPLATE, SpriteFileNameC.GHOST_EATEN_FOLDER, SpriteFileNameC.GHOST_EATEN_RIGHT_FRAME_FILE_NAME);
        final String eatenDownFramePath = String.format(SpriteFileNameC.GHOST_SPRITE_PATH_TEMPLATE, SpriteFileNameC.GHOST_EATEN_FOLDER, SpriteFileNameC.GHOST_EATEN_DOWN_FRAME_FILE_NAME);
        final String eatenLeftFramePath = String.format(SpriteFileNameC.GHOST_SPRITE_PATH_TEMPLATE, SpriteFileNameC.GHOST_EATEN_FOLDER, SpriteFileNameC.GHOST_EATEN_LEFT_FRAME_FILE_NAME);

        final Image up = spriteRegistry.get(eatenUpFramePath);
        final Image right = spriteRegistry.get(eatenRightFramePath);
        final Image down = spriteRegistry.get(eatenDownFramePath);
        final Image left = spriteRegistry.get(eatenLeftFramePath);

        return Map.ofEntries(
                new AbstractMap.SimpleEntry<>(Vector.UP, new Image[]{up}),
                new AbstractMap.SimpleEntry<>(Vector.RIGHT, new Image[]{right}),
                new AbstractMap.SimpleEntry<>(Vector.DOWN, new Image[]{down}),
                new AbstractMap.SimpleEntry<>(Vector.LEFT, new Image[]{left})
                );
    }
}
