package com.ahmedaraby.game.pacman.ghostmode.pinky;

import com.ahmedaraby.game.pacman.config.Configs;
import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.ghostmode.Scattered;
import com.ahmedaraby.game.pacman.ghostmode.TemporalGhostMode;
import com.ahmedaraby.game.pacman.ghostmode.navigation.GhostNavigator;
import com.ahmedaraby.game.pacman.ghostmode.navigation.ShortestPathNavigator;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import com.ahmedaraby.game.pacman.util.ghost.GhostUtil;
import com.ahmedaraby.jengine.animation.Animator;
import com.ahmedaraby.jengine.animation.DistanceBasedAnimator;
import com.ahmedaraby.jengine.entity.Coordinate;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class PinkyScattered extends Scattered {
    private final Animator animator;
    private final GhostNavigator navigator;

    private final Coordinate target = new Coordinate(0, 0); // top left corner

    public PinkyScattered(Ghost ghost, GameState gameState, int[] activePeriodsSec) {
        super(ghost, gameState, activePeriodsSec);

        final Image[] frames = loadSprites(this);
        animator = new DistanceBasedAnimator(new double[]{
                DimensionsC.GHOST_FIRST_LEG_MOVEMENT_DISTANCE_PIXELS,
                DimensionsC.GHOST_SECOND_LEG_MOVEMENT_DISTANCE_PIXELS
        }, frames);
        navigator = new ShortestPathNavigator();
    }

    @Override
    public void render(Canvas canvas) {
        GraphicsContext con = canvas.getGraphicsContext2D();
        con.drawImage(animator.getFrame(), ghost.getCol(), ghost.getRow());
    }

    @Override
    public void move() {
        final DirectionsE newDir = navigator.nextMoveDirection(ghost.getTopLeftCorner(), target);
        if (newDir != DirectionsE.STILL) {
            final Coordinate newCord = GhostUtil.move(ghost.getTopLeftCorner(), newDir);
            ghost.setTopLeftCorner(newCord);
            ghost.setDir(newDir);
            animator.stride(DimensionsC.GHOST_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_STRIDE);
        }
    }
}
