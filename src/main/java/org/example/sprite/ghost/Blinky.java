package org.example.sprite.ghost;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import org.example.constant.Configs;
import org.example.constant.Dimensions;
import org.example.constant.DirectionsE;
import org.example.constant.SpriteFileNameConstants;
import org.example.entity.Coordinate;
import org.example.entity.MazeCoordinate;
import org.example.event.Event;
import org.example.ghostmode.ChaseShortestPathPac;
import org.example.sprite.Maze;
import org.example.sprite.PacMan;
import org.example.sprite.Sprite;
import org.example.util.CoordinateUtil;


public class Blinky implements Sprite{

    private double canvasCol, canvasRow;
    private DirectionsE directionsE;
    private final Image blinky;
    private final ChaseShortestPathPac chaseMode;

    // [TODO] find a better way to pass this information
    private final PacMan pacMan;
    private final Maze maze;

    public Blinky(PacMan pacMan, Maze maze) {
        canvasCol = 0;
        canvasRow = 0;
        directionsE = DirectionsE.STILL;
        final String SPRITE_SHEET_FILE_RESOURCE_ABSOLUTE_PATH = getClass().getResource(SpriteFileNameConstants.SPRITE_SHEET_FILE_RESOURCE_RELATIVE_PATH).toString();
        blinky = new Image(SPRITE_SHEET_FILE_RESOURCE_ABSOLUTE_PATH);

        this.pacMan = pacMan;
        this.maze = maze;

        chaseMode = new ChaseShortestPathPac();
    }

    @Override
    public void render(Canvas canvas) {
        System.out.println("blinky: row = " + canvasRow + " , col = " + canvasCol + " , Dir = " + directionsE);
        switch (directionsE) {
            case RIGHT:
                canvasCol = canvasCol + Dimensions.BLINKY_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_BLINKY_STRIDE;
                break;
            case LEFT:
                canvasCol = canvasCol - Dimensions.BLINKY_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_BLINKY_STRIDE;
                break;
            case UP:
                canvasRow = canvasRow - Dimensions.BLINKY_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_BLINKY_STRIDE;
                break;
            case DOWN:
                canvasRow = canvasRow + Dimensions.BLINKY_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_BLINKY_STRIDE;
                break;
        }
        canvas.getGraphicsContext2D().drawImage(blinky, canvasCol, canvasRow);
    }

    @Override
    public void move(Event event) {
        final Coordinate ghostCurrCanvasCord = new Coordinate(canvasRow, canvasCol);
        final MazeCoordinate ghostCurrMazeCord = CoordinateUtil.toMazeCoordinate(ghostCurrCanvasCord, directionsE);
        final MazeCoordinate ghostNextMazeCord = chaseMode.nextPosition(ghostCurrMazeCord, pacMan.getCurrMazeCord(), maze.getGameMaze());
        directionsE = chaseMode.nextMoveDirection(ghostCurrMazeCord, ghostNextMazeCord);
    }
}
