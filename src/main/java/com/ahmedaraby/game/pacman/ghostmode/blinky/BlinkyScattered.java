package com.ahmedaraby.game.pacman.ghostmode.blinky;

import com.ahmedaraby.game.pacman.ghostmode.navigation.GhostNavigator;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import com.ahmedaraby.jengine.animation.Animator;
import com.ahmedaraby.jengine.animation.DistanceBasedAnimator;
import com.ahmedaraby.game.pacman.config.Configs;
import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.constant.SpriteFileNameC;
import com.ahmedaraby.game.pacman.entity.Coordinate;
import com.ahmedaraby.game.pacman.ghostmode.Scattered;
import com.ahmedaraby.game.pacman.ghostmode.navigation.ShortestPathNavigator;
import com.ahmedaraby.game.pacman.util.ghost.GhostUtil;

public class BlinkyScattered extends Scattered {

    private final Animator animator;
    private final GhostNavigator navigator;
    private final Coordinate topRightCorner = new Coordinate(
            0,
            DimensionsC.CANVAS_WIDTH_PIXELS - 1
    );

    public BlinkyScattered(Ghost ghost, GameState gameState, int[] activePeridosSec) {
        super(ghost, gameState, activePeridosSec);
        final Image[] frames = loadSprites();
        this.animator = new DistanceBasedAnimator(
                new double[]{DimensionsC.GHOST_FIRST_LEG_MOVEMENT_DISTANCE_PIXELS, DimensionsC.GHOST_SECOND_LEG_MOVEMENT_DISTANCE_PIXELS}, frames);
        this.navigator = new ShortestPathNavigator();
    }

    @Override
    public void render(Canvas canvas) {
        canvas.getGraphicsContext2D().drawImage(animator.getFrame(), ghost.getCol(), ghost.getRow());
    }

    @Override
    public void move() {
        final Coordinate ghostCurrCord = new Coordinate(ghost.getRow(), ghost.getCol());
        final DirectionsE directionsE = navigator.nextMoveDirection(ghostCurrCord, topRightCorner);
        final Coordinate ghostNewCord = GhostUtil.move(ghostCurrCord, directionsE);

        ghost.setDir(directionsE);
        ghost.setRow(ghostNewCord.getRow());
        ghost.setCol(ghostNewCord.getCol());

        if(directionsE != DirectionsE.STILL) {
            animator.stride(DimensionsC.GHOST_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_STRIDE);
        }
    }


    private Image[] loadSprites() {
        // [TODO] move sprites loading into a class that provide caching
        final String BLINKY_FRAME_1_FILE_RESOURCE_ABSOLUTE_PATH = getClass().getResource(SpriteFileNameC.BLINKY_FRAME_1_FILE_RESOURCE_RELATIVE_PATH).toString();
        final String BLINKY_FRAME_2_FILE_RESOURCE_ABSOLUTE_PATH = getClass().getResource(SpriteFileNameC.BLINKY_FRAME_2_FILE_RESOURCE_RELATIVE_PATH).toString();
        final Image frame1 = new Image(BLINKY_FRAME_1_FILE_RESOURCE_ABSOLUTE_PATH);
        final Image frame2 = new Image(BLINKY_FRAME_2_FILE_RESOURCE_ABSOLUTE_PATH);
        return new Image[]{frame1, frame2};
    }
}
