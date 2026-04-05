package org.example.ghostmode.blinky;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import org.example.animation.Animator;
import org.example.animation.DistanceBasedAnimator;
import org.example.config.Configs;
import org.example.constant.DimensionsC;
import org.example.constant.DirectionsE;
import org.example.constant.SpriteFileNameC;
import org.example.entity.CanvasCoordinate;
import org.example.entity.Vector;
import org.example.ghostmode.Ghost;
import org.example.ghostmode.GhostMode;
import org.example.util.EnrichedThreadLocalRandom;
import org.example.util.ghost.GhostUtil;
import org.example.util.VectorUtil;
import org.example.util.ghost.FrightenedGhostUtil;

import java.util.List;


public class BlinkyFrightened implements GhostMode {

    private final Animator animator;
    private final EnrichedThreadLocalRandom random;
    private final float activePeriodSeconds;

    public BlinkyFrightened() {
        Image[] frames = loadSprites();
        this.animator = new DistanceBasedAnimator(new double[]{DimensionsC.BLINKY_FIRST_LEG_MOVEMENT_DISTANCE_PIXELS, DimensionsC.BLINKY_SECOND_LEG_MOVEMENT_DISTANCE_PIXELS}, frames);
        this.random = new EnrichedThreadLocalRandom();
        this.activePeriodSeconds = 9;
    }

    @Override
    public float getActivePeriodSeconds() {
        return activePeriodSeconds;
    }

    @Override
    public void render(Canvas canvas, Ghost ghost) {
        canvas.getGraphicsContext2D().drawImage(animator.getFrame(), ghost.getCanvasCol(), ghost.getCanvasRow());
    }

    @Override
    public void move(Ghost ghost) {
        final CanvasCoordinate sCord = new CanvasCoordinate(ghost.getCanvasRow(), ghost.getCanvasCol());
        final Vector currDir = VectorUtil.toVector(ghost.getDirectionsE());

        final List<Vector> eligibleDirections = FrightenedGhostUtil.getEligibleDirections(sCord, currDir);
        Vector newDirV;
        if (eligibleDirections.isEmpty()) {
            newDirV = VectorUtil.getOpposite(currDir);
        } else {
            final int randIndex = random.nextIntStartInclEndExcl(0, eligibleDirections.size());
            newDirV = eligibleDirections.get(randIndex);
        }

        final DirectionsE newDirE = VectorUtil.toDirection(newDirV);
        final CanvasCoordinate nCord = GhostUtil.move(sCord, newDirE);

        ghost.setDirectionsE(newDirE);
        ghost.setCanvasRow(nCord.getRow());
        ghost.setCanvasCol(nCord.getCol());

        animator.stride(DimensionsC.BLINKY_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_BLINKY_STRIDE);
    }


    private Image[] loadSprites() {
        final String BLINKY_FRIGHTENED_FRAME_1_FILE_RESOURCE_ABSOLUTE_PATH = getClass().getResource(SpriteFileNameC.BLINKY_FRIGHTENED_FRAME_1_FILE_RESOURCE_RELATIVE_PATH).toString();
        final String BLINKY_FRIGHTENED_FRAME_2_FILE_RESOURCE_ABSOLUTE_PATH = getClass().getResource(SpriteFileNameC.BLINKY_FRIGHTENED_FRAME_2_FILE_RESOURCE_RELATIVE_PATH).toString();
        final Image frame1 = new Image(BLINKY_FRIGHTENED_FRAME_1_FILE_RESOURCE_ABSOLUTE_PATH);
        final Image frame2 = new Image(BLINKY_FRIGHTENED_FRAME_2_FILE_RESOURCE_ABSOLUTE_PATH);
        return new Image[]{frame1, frame2};
    }
}
