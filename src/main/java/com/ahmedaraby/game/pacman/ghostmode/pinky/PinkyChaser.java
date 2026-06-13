package com.ahmedaraby.game.pacman.ghostmode.pinky;

import com.ahmedaraby.game.pacman.config.Configs;
import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.constant.SpriteFileNameC;
import com.ahmedaraby.game.pacman.ghostmode.Chaser;
import com.ahmedaraby.game.pacman.ghostmode.Scattered;
import com.ahmedaraby.game.pacman.ghostmode.navigation.GhostNavigator;
import com.ahmedaraby.game.pacman.ghostmode.navigation.ShortestPathNavigator;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import com.ahmedaraby.game.pacman.util.ghost.GhostUtil;
import com.ahmedaraby.jengine.animation.Animator;
import com.ahmedaraby.jengine.animation.DistanceBasedAnimator;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.entity.Line;
import com.ahmedaraby.jengine.entity.Vector;
import com.ahmedaraby.jengine.sprite.SpriteRegistry;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.net.URL;

public class PinkyChaser extends Chaser {
    private final Animator animator;
    private final GhostNavigator navigator;

    public PinkyChaser(Ghost ghost, GameState gameState, SpriteRegistry<String, Image> spriteRegistry, int[] activePeriodsSec) {
        super(ghost, gameState, spriteRegistry, activePeriodsSec);

        final Image[] frames = loadSprites();
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
        final Coordinate pacManCord = gameState.getPacMan().getTopLeftCorner();
        final Vector pacManDir = gameState.getPacMan().getDir().toVector();

        // calculate the tile 4 steps ahead of pacman, and force it to be within the playground
        final Vector pacManDirScaled = pacManDir.scale(4);
        final Coordinate lookAheadCord = pacManCord.add(pacManDirScaled.getX(), pacManDirScaled.getY());
        final Line lookAheadLine = new Line(pacManCord, lookAheadCord).trim(gameState.getMaze().getRect());
        final Coordinate target = lookAheadLine.getEnd();

        final DirectionsE newDir = navigator.nextMoveDirection(ghost.getTopLeftCorner(), target);
        if (newDir != DirectionsE.STILL) {
            final Coordinate newCord = GhostUtil.move(ghost.getTopLeftCorner(), newDir);
            ghost.setTopLeftCorner(newCord);
            ghost.setDir(newDir);
            animator.stride(DimensionsC.GHOST_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_STRIDE);
        }
    }

    private Image[] loadSprites() {
        final URL frame1Url = getClass().getResource(String.format(SpriteFileNameC.GHOST_SPRITE_PATH_TEMPLATE, SpriteFileNameC.PINKY_FOLDER, SpriteFileNameC.PINKY_FRAME_1_FILE_NAME));
        final URL frame2Url = getClass().getResource(String.format(SpriteFileNameC.GHOST_SPRITE_PATH_TEMPLATE, SpriteFileNameC.PINKY_FOLDER, SpriteFileNameC.PINKY_FRAME_2_FILE_NAME));
        final Image frame1 = new Image(frame1Url.toString());
        final Image frame2 = new Image(frame2Url.toString());
        return new Image[] {frame1, frame2};
    }
}
