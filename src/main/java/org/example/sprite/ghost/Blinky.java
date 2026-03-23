package org.example.sprite.ghost;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import org.example.constant.DirectionsE;
import org.example.constant.SpriteFileNameConstants;
import org.example.entity.Coordinate;
import org.example.event.Event;
import org.example.ghostmode.ChaseShortestPathPac;
import org.example.sprite.Maze;
import org.example.sprite.PacMan;
import org.example.sprite.Sprite;
import org.example.util.GhostUtil;


public class Blinky implements Sprite{

    private double canvasCol, canvasRow;
    private DirectionsE directionsE;
    private final Image blinky;
    private final ChaseShortestPathPac chaseMode;

    // [TODO] find a better way to pass this information
    private final PacMan pacMan;
    private final Maze maze;

    public Blinky(PacMan pacMan, Maze maze, ChaseShortestPathPac chaseMode) {
        canvasCol = 0;
        canvasRow = 0;
        directionsE = DirectionsE.STILL;
        final String SPRITE_SHEET_FILE_RESOURCE_ABSOLUTE_PATH = getClass().getResource(SpriteFileNameConstants.SPRITE_SHEET_FILE_RESOURCE_RELATIVE_PATH).toString();
        blinky = new Image(SPRITE_SHEET_FILE_RESOURCE_ABSOLUTE_PATH);

        this.pacMan = pacMan;
        this.maze = maze;
        this.chaseMode = chaseMode;
    }

    @Override
    public void render(Canvas canvas) {
        System.out.println("blinky: row = " + canvasRow + " , col = " + canvasCol + " , Dir = " + directionsE);
        canvas.getGraphicsContext2D().drawImage(blinky, canvasCol, canvasRow);
    }

    @Override
    public void move(Event event) {
        final Coordinate ghostCurrCord = new Coordinate(canvasRow, canvasCol);
        directionsE = chaseMode.nextMoveDirection(ghostCurrCord, pacMan.getCurrMazeCord(), maze.getGameMaze());
        final Coordinate ghostNewCord = GhostUtil.move(ghostCurrCord, directionsE);
        canvasRow = ghostNewCord.getRow();
        canvasCol = ghostNewCord.getCol();
    }
}
