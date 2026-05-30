package com.ahmedaraby.game.pacman.ghostmode.common;

import com.ahmedaraby.jengine.animation.Animator;
import com.ahmedaraby.jengine.animation.DistanceBasedAnimator;
import com.ahmedaraby.game.pacman.constant.GhostEatenSpritesFileNameC;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.entity.Vector;
import com.ahmedaraby.game.pacman.ghostmode.navigation.GhostNavigator;
import com.ahmedaraby.game.pacman.ghostmode.navigation.ShortestPathNavigator;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import com.ahmedaraby.game.pacman.config.Configs;
import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.ghostmode.GhostMode;
import com.ahmedaraby.game.pacman.util.ghost.GhostUtil;

import java.util.AbstractMap;
import java.util.Map;

public class Eaten extends GhostMode {

    private final GhostNavigator navigator;
    private final Animator animator;
    private Coordinate ghostHouseEmptyLoc;

    public Eaten(Ghost ghost, GameState gameState) {
        super(ghost, gameState);
        this.navigator = new ShortestPathNavigator();

        final Map<Vector, Image[]> sprites = loadSprites();
        this.animator = new DistanceBasedAnimator(
                new double[]{DimensionsC.GHOST_STRIDE_PIXELS},
                sprites.get(Vector.UP)
        );
    }

    @Override
    public void init() {
        final double ghostHERow = gameState.getGhostHouseS().getERow();;
        final double ghostHSCol = gameState.getGhostHouseS().getCol();
        ghostHouseEmptyLoc = new Coordinate(ghostHERow - DimensionsC.MAZE_CELL_SIZE_PIXELS, ghostHSCol + DimensionsC.MAZE_CELL_SIZE_PIXELS);
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
        final Coordinate currCord = new Coordinate(ghost.getRow(), ghost.getCol());
        final DirectionsE newDir = this.navigator.nextMoveDirection(currCord, ghostHouseEmptyLoc);
        final Coordinate newCord = GhostUtil.move(currCord, newDir);

        ghost.setDir(newDir);
        ghost.setRow(newCord.getRow());
        ghost.setCol(newCord.getCol());

        if (newDir != DirectionsE.STILL) {
            this.animator.stride(DimensionsC.GHOST_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_STRIDE);
        }
    }

    private Map<Vector, Image[]> loadSprites() {
        // [TODO] move sprites loading into a class that provide caching
        final String GHOST_EATEN_UP_FRAME_FILE_RESOURCE_ABSOLUTE_PATH = getClass().getResource(GhostEatenSpritesFileNameC.GHOST_EATEN_UP_FRAME_FILE_RESOURCE_RELATIVE_PATH).toString();
        final String GHOST_EATEN_RIGHT_FRAME_FILE_RESOURCE_ABSOLUTE_PATH = getClass().getResource(GhostEatenSpritesFileNameC.GHOST_EATEN_RIGHT_FRAME_FILE_RESOURCE_RELATIVE_PATH).toString();
        final String GHOST_EATEN_DOWN_FRAME_FILE_RESOURCE_ABSOLUTE_PATH = getClass().getResource(GhostEatenSpritesFileNameC.GHOST_EATEN_DOWN_FRAME_FILE_RESOURCE_RELATIVE_PATH).toString();
        final String GHOST_EATEN_LEFT_FRAME_FILE_RESOURCE_ABSOLUTE_PATH = getClass().getResource(GhostEatenSpritesFileNameC.GHOST_EATEN_LEFT_FRAME_FILE_RESOURCE_RELATIVE_PATH).toString();

        final Image up = new Image(GHOST_EATEN_UP_FRAME_FILE_RESOURCE_ABSOLUTE_PATH);
        final Image right = new Image(GHOST_EATEN_RIGHT_FRAME_FILE_RESOURCE_ABSOLUTE_PATH);
        final Image down = new Image(GHOST_EATEN_DOWN_FRAME_FILE_RESOURCE_ABSOLUTE_PATH);
        final Image left = new Image(GHOST_EATEN_LEFT_FRAME_FILE_RESOURCE_ABSOLUTE_PATH);

        return Map.ofEntries(
                new AbstractMap.SimpleEntry<>(Vector.UP, new Image[]{up}),
                new AbstractMap.SimpleEntry<>(Vector.RIGHT, new Image[]{right}),
                new AbstractMap.SimpleEntry<>(Vector.DOWN, new Image[]{down}),
                new AbstractMap.SimpleEntry<>(Vector.LEFT, new Image[]{left})
                );
    }
}
