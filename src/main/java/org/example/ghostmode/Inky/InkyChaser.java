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
import org.example.entity.CanvasRect;
import org.example.entity.Line;
import org.example.entity.Vector;
import org.example.ghostmode.Chaser;
import org.example.ghostmode.navigation.GhostNavigator;
import org.example.ghostmode.navigation.ShortestPathNavigator;
import org.example.model.GameState;
import org.example.sprite.ghost.Blinky;
import org.example.sprite.ghost.Ghost;
import org.example.util.ghost.GhostUtil;

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
        final GraphicsContext con = canvas.getGraphicsContext2D();
        con.drawImage(animator.getFrame(), ghost.getCol(), ghost.getRow());
    }

    @Override
    public void move() {
        // calculate the target coordinate
        final CanvasCoordinate interTile = calculateIntermediateTile();
        final Line blinky2InterTileLine = makeLineFromBlinkyToIntermediateTile(interTile);
        final CanvasCoordinate target = calculateTheTargetCoordinate(blinky2InterTileLine);

        // navigate the ghost to the target
        DirectionsE newDir = navigator.nextMoveDirection(ghost.getTopLeftCorner(), target);

        if (newDir != null) {
            animator.stride(DimensionsC.GHOST_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_STRIDE);

            final CanvasCoordinate newCord = GhostUtil.move(ghost.getTopLeftCorner(), newDir);

            ghost.setDir(newDir);
            ghost.setCol(newCord.getCol());
            ghost.setRow(newCord.getRow());
        }
    }

    private CanvasCoordinate calculateIntermediateTile() {
        final CanvasCoordinate pacManCord = gameState.getPacMan().getTopLeftCorner();
        final Vector pacManDir = gameState.getPacMan().getDir().toVector();
        return pacManCord.add(
                DimensionsC.PAC_MAN_STRIDE_PIXELS * 2 * pacManDir.getX(),
                DimensionsC.PAC_MAN_STRIDE_PIXELS * 2 * pacManDir.getY()
        );

    }

    private Line makeLineFromBlinkyToIntermediateTile(CanvasCoordinate interTile) {
        final CanvasCoordinate blinkyCord = gameState.getGhosts()
                .stream()
                .filter(ghost -> ghost instanceof Blinky)
                .findFirst()
                .get()
                .getTopLeftCorner();
        return new Line(blinkyCord, interTile);

    }

    private CanvasCoordinate calculateTheTargetCoordinate(Line blinky2InterTileLine) {
        final CanvasRect wholePLayGround = new CanvasRect(
                new CanvasCoordinate(0, 0), DimensionsC.CANVAS_WIDTH_PIXELS, DimensionsC.CANVAS_HEIGHT_PIXELS);
        return blinky2InterTileLine
                .scale(2) // double the line in the direction from blinky to the Intermediate tile
                .trim(wholePLayGround) // then trim the line such that the line endpoint are within the playground
                .getEnd();  // endpoint is the target coordinate for Inky
    }

    private Image[] loadSprites() {
        final String INKY_FRAME_1_FILE_RESOURCE_ABSOLUTE_PATH = getClass().getResource(SpriteFileNameC.INKY_FRAME_1_FILE_RESOURCE_RELATIVE_PATH).toString();
        final String INKY_FRAME_2_FILE_RESOURCE_ABSOLUTE_PATH = getClass().getResource(SpriteFileNameC.INKY_FRAME_2_FILE_RESOURCE_RELATIVE_PATH).toString();

        final Image frame1 = new Image(INKY_FRAME_1_FILE_RESOURCE_ABSOLUTE_PATH);
        final Image frame2 = new Image(INKY_FRAME_2_FILE_RESOURCE_ABSOLUTE_PATH);
        return new Image[] {frame1, frame2};
    }
}
