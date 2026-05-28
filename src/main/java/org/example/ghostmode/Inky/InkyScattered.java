package org.example.ghostmode.Inky;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.example.animation.DistanceBasedAnimator;
import org.example.config.Configs;
import org.example.constant.DimensionsC;
import org.example.constant.DirectionsE;
import org.example.constant.SpriteFileNameC;
import org.example.entity.CanvasCoordinate;
import org.example.ghostmode.Scattered;
import org.example.ghostmode.navigation.GhostNavigator;
import org.example.ghostmode.navigation.ShortestPathNavigator;
import org.example.model.GameState;
import org.example.sprite.ghost.Ghost;
import org.example.util.ghost.GhostUtil;

public class InkyScattered extends Scattered {

    private final GhostNavigator navigator;
    private final DistanceBasedAnimator animator;
    private final CanvasCoordinate target = new CanvasCoordinate(  // bottom right corner
            DimensionsC.CANVAS_HEIGHT_PIXELS - DimensionsC.MAZE_CELL_SIZE_PIXELS,
            DimensionsC.CANVAS_WIDTH_PIXELS - DimensionsC.MAZE_CELL_SIZE_PIXELS
    );
    public InkyScattered(Ghost ghost, GameState gameState, int[] activePeriodsSec) {
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
        final GraphicsContext con = canvas.getGraphicsContext2D();
        con.drawImage(animator.getFrame(), ghost.getCol(), ghost.getRow());
    }

    @Override
    public void move() {
        DirectionsE nextDir = this.navigator.nextMoveDirection(ghost.getTopLeftCorner(), target);
        if (nextDir != DirectionsE.STILL) {
            animator.stride(DimensionsC.GHOST_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_STRIDE);

            final CanvasCoordinate nextCord = GhostUtil.move(ghost.getTopLeftCorner(), nextDir);

            ghost.setDir(nextDir);
            ghost.setCol(nextCord.getCol());
            ghost.setRow(nextCord.getRow());
        }
    }


    private Image[] loadSprites() {
        final String INKY_FRAME_1_FILE_RESOURCE_ABSOLUTE_PATH = getClass().getResource(SpriteFileNameC.INKY_FRAME_1_FILE_RESOURCE_RELATIVE_PATH).toString();
        final String INKY_FRAME_2_FILE_RESOURCE_ABSOLUTE_PATH = getClass().getResource(SpriteFileNameC.INKY_FRAME_2_FILE_RESOURCE_RELATIVE_PATH).toString();

        final Image frame1 = new Image(INKY_FRAME_1_FILE_RESOURCE_ABSOLUTE_PATH);
        final Image frame2 = new Image(INKY_FRAME_2_FILE_RESOURCE_ABSOLUTE_PATH);
        return new Image[] {frame1, frame2};
    }
}
