package org.example.sprite.ghost;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import org.example.animation.BlinkyStrideTracker;
import org.example.constant.DirectionsE;
import org.example.constant.GhostSpriteFrameE;
import org.example.constant.SpriteFileNameC;
import org.example.entity.CanvasCoordinate;
import org.example.event.Event;
import org.example.ghostmode.ChaseShortestPathPac;
import org.example.sprite.Maze;
import org.example.sprite.PacMan;
import org.example.sprite.Sprite;
import org.example.util.GhostUtil;


public class Blinky implements Sprite{

    private double canvasCol, canvasRow;
    private DirectionsE directionsE;
    private Image blinkyFrame1;
    private Image blinkyFrame2;
    private final ChaseShortestPathPac chaseMode;
    private final BlinkyStrideTracker blinkyStrideTracker;

    // [TODO] find a better way to pass this information
    private final PacMan pacMan;
    private final Maze maze;

    public Blinky(PacMan pacMan, Maze maze, ChaseShortestPathPac chaseMode) {
        canvasCol = 0;
        canvasRow = 0;
        directionsE = DirectionsE.STILL;

        loadSprites();

        this.pacMan = pacMan;
        this.maze = maze;
        this.chaseMode = chaseMode;
        this.blinkyStrideTracker = new BlinkyStrideTracker();
    }

    @Override
    public void render(Canvas canvas) {
        System.out.println("blinky: row = " + canvasRow + " , col = " + canvasCol + " , Dir = " + directionsE);
        final Image activeFrame = getActiveFrame();
        canvas.getGraphicsContext2D().drawImage(activeFrame, canvasCol, canvasRow);
    }

    @Override
    public void move(Event event) {
        final CanvasCoordinate ghostCurrCord = new CanvasCoordinate(canvasRow, canvasCol);
        directionsE = chaseMode.nextMoveDirection(ghostCurrCord, pacMan.getCurrCanvasCord(), maze.getGameMaze());
        final CanvasCoordinate ghostNewCord = GhostUtil.move(ghostCurrCord, directionsE);
        canvasRow = ghostNewCord.getRow();
        canvasCol = ghostNewCord.getCol();

        if(directionsE != DirectionsE.STILL) {
            blinkyStrideTracker.stride();
        }
    }


    private void loadSprites() {
        final String BLINKY_FRAME_1_FILE_RESOURCE_ABSOLUTE_PATH = getClass().getResource(SpriteFileNameC.BLINKY_FRAME_1_FILE_RESOURCE_RELATIVE_PATH).toString();
        blinkyFrame1 = new Image(BLINKY_FRAME_1_FILE_RESOURCE_ABSOLUTE_PATH);

        final String BLINKY_FRAME_2_FILE_RESOURCE_ABSOLUTE_PATH = getClass().getResource(SpriteFileNameC.BLINKY_FRAME_2_FILE_RESOURCE_RELATIVE_PATH).toString();
        blinkyFrame2 = new Image(BLINKY_FRAME_2_FILE_RESOURCE_ABSOLUTE_PATH);
    }

    private Image getActiveFrame() {
        final GhostSpriteFrameE frame = (GhostSpriteFrameE) blinkyStrideTracker.getState();
        switch (frame) {
            case FIRST:
                return blinkyFrame1;
            case SECOND:
                return blinkyFrame2;
        }
        return blinkyFrame1;
    }
}
