package org.example.ghostmode.Inky;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import org.example.animation.DistanceBasedAnimator;
import org.example.constant.DimensionsC;
import org.example.constant.SpriteFileNameC;
import org.example.entity.CanvasCoordinate;
import org.example.ghostmode.Chaser;
import org.example.ghostmode.navigation.GhostNavigator;
import org.example.ghostmode.navigation.ShortestPathNavigator;
import org.example.model.GameState;
import org.example.sprite.ghost.Ghost;

public class InkyChaser extends Chaser {
    private final GhostNavigator navigator;
    private final DistanceBasedAnimator animator;

    public InkyChaser(Ghost ghost, GameState gameState, int[] activePeriodsSec) {
        super(ghost, gameState, activePeriodsSec);
        final Image[] frames = loadSprites();
        navigator = new ShortestPathNavigator();
        animator = new DistanceBasedAnimator(
                new double[]{DimensionsC.GHOST_FIRST_LEG_MOVEMENT_DISTANCE_PIXELS, DimensionsC.GHOST_SECOND_LEG_MOVEMENT_DISTANCE_PIXELS}
                ,frames
        );
    }

    @Override
    public void render(Canvas canvas) {

    }

    @Override
    public void move() {
    private Image[] loadSprites() {
        final String INKY_FRAME_1_FILE_RESOURCE_ABSOLUTE_PATH = getClass().getResource(SpriteFileNameC.INKY_FRAME_1_FILE_RESOURCE_RELATIVE_PATH).toString();
        final String INKY_FRAME_2_FILE_RESOURCE_ABSOLUTE_PATH = getClass().getResource(SpriteFileNameC.INKY_FRAME_2_FILE_RESOURCE_RELATIVE_PATH).toString();

        final Image frame1 = new Image(INKY_FRAME_1_FILE_RESOURCE_ABSOLUTE_PATH);
        final Image frame2 = new Image(INKY_FRAME_2_FILE_RESOURCE_ABSOLUTE_PATH);
        return new Image[] {frame1, frame2};
    }
}
