package org.example.sprite.ghost;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import org.example.animation.Animator;
import org.example.animation.DistanceBasedAnimator;
import org.example.config.Configs;
import org.example.constant.*;
import org.example.entity.CanvasCoordinate;
import org.example.event.Event;
import org.example.ghostmode.ShortestPathMode;
import org.example.sprite.PacMan;
import org.example.sprite.Sprite;
import org.example.util.GhostUtil;

import java.util.HashMap;
import java.util.Map;


public class Blinky implements Sprite{

    private double canvasCol = 0, canvasRow = 0;
    private DirectionsE directionsE = DirectionsE.STILL;
    private GhostModeE mode;

    private final ShortestPathMode chaseMode;
    private final Map<GhostModeE, Image[]> ghostSprites;
    private final ChaseScatterTimer chaseScatterTimer;
    private final Animator animator;

    // [TODO] find a better way to pass this information
    private final PacMan pacMan;

    public Blinky(PacMan pacMan, Maze maze, ChaseShortestPathPac chaseMode) {
        canvasCol = 0;
        canvasRow = 0;
        directionsE = DirectionsE.STILL;

        loadSprites();
    public Blinky(PacMan pacMan, ShortestPathMode chaseMode) {
        ghostSprites = loadSprites();

        this.pacMan = pacMan;
        this.chaseMode = chaseMode;
        this.chaseScatterTimer = new ChaseScatterTimer();
        this.mode = chaseScatterTimer.getMode();
        this.animator = new DistanceBasedAnimator(
                new double[]{DimensionsC.BLINKY_FIRST_LEG_MOVEMENT_DISTANCE_PIXELS, DimensionsC.BLINKY_SECOND_LEG_MOVEMENT_DISTANCE_PIXELS},
                ghostSprites.get(mode)
        );
        System.out.println("blinky initialized");
    }

    @Override
    public void render(Canvas canvas) {
        System.out.println("blinky: row = " + canvasRow + " , col = " + canvasCol + " , Dir = " + directionsE);
        canvas.getGraphicsContext2D().drawImage(animator.getFrame(), canvasCol, canvasRow);
    }

    @Override
    public void move(Event event) {
        final CanvasCoordinate ghostCurrCord = new CanvasCoordinate(canvasRow, canvasCol);
        final CanvasCoordinate ghostNewCord = GhostUtil.move(ghostCurrCord, directionsE);
        canvasRow = ghostNewCord.getRow();
        canvasCol = ghostNewCord.getCol();

        if(directionsE != DirectionsE.STILL) {
            animator.stride(DimensionsC.BLINKY_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_BLINKY_STRIDE);
        }
    }



    private Map<GhostModeE, Image[]> loadSprites() {
        final Map<GhostModeE, Image[]> ghostSprites = new HashMap<>();

        final String BLINKY_FRAME_1_FILE_RESOURCE_ABSOLUTE_PATH = getClass().getResource(SpriteFileNameC.BLINKY_FRAME_1_FILE_RESOURCE_RELATIVE_PATH).toString();


        final String BLINKY_FRAME_2_FILE_RESOURCE_ABSOLUTE_PATH = getClass().getResource(SpriteFileNameC.BLINKY_FRAME_2_FILE_RESOURCE_RELATIVE_PATH).toString();
        ghostSprites.put(GhostModeE.CHASE, new Image[]{
                new Image(BLINKY_FRAME_1_FILE_RESOURCE_ABSOLUTE_PATH),
                new Image(BLINKY_FRAME_2_FILE_RESOURCE_ABSOLUTE_PATH)
        });
        ghostSprites.put(GhostModeE.SCATTERED, new Image[]{
                new Image(BLINKY_FRAME_1_FILE_RESOURCE_ABSOLUTE_PATH),
                new Image(BLINKY_FRAME_2_FILE_RESOURCE_ABSOLUTE_PATH)
        });
        return ghostSprites;
    }
}
