package org.example.sprite.ghost;

import javafx.scene.canvas.Canvas;
import org.example.config.GhostModeActivePeriodsConf;
import org.example.constant.*;
import org.example.entity.CanvasCoordinate;
import org.example.event.Event;
import org.example.event.Subscriber;
import org.example.event.collision.PacMan2GhostCollisionEvent;
import org.example.ghostmode.blinky.BlinkyChaser;
import org.example.ghostmode.common.Eaten;
import org.example.ghostmode.common.Frightened;
import org.example.ghostmode.blinky.BlinkyScattered;
import org.example.model.GameState;
import org.example.sprite.playground.GhostHouseS;

public class Blinky extends Ghost implements Subscriber {
    // [TODO] rename modes
    // ghost modes


    @Override
    public CanvasCoordinate getPacManCanvasCord() {
        return gameState.getPacMan().getCurrCanvasCord();
    }

    public Blinky(GameState gameState) {
        super(gameState, SpriteE.GHOST, 0, 0, DirectionsE.STILL);

        // ghost modes
        this.chaser = new BlinkyChaser(this, gameState, GhostModeActivePeriodsConf.LEVEL_1_CHASE_ACTIVE_PERIODS);
        this.scattered = new BlinkyScattered(this, gameState, GhostModeActivePeriodsConf.LEVEL_1_SCATTER_ACTIVE_PERIODS);
        this.frightened = new Frightened(this, gameState, GhostModeActivePeriodsConf.ALL_LEVELS_FRIGHTENED_MODE_ACTIVE_PERIODS);
        this.eaten = new Eaten(this, gameState);

        scattered.enter();
        this.activeMode = scattered;
    }

    @Override
    public void init() {
        final GhostHouseS ghostHouseS = gameState.getGhostHouseS();
        final double col = ghostHouseS.getCol() + DimensionsC.MAZE_CELL_SIZE_PIXELS;
        final double row = ghostHouseS.getERow() - DimensionsC.MAZE_CELL_SIZE_PIXELS;
        setCol(col);
        setRow(row);
    }

    @Override
    public void render(Canvas canvas) {
        System.out.println("blinky: row = " + row + " , col = " + col + " , Dir = " + dir);
        activeMode.render(canvas);
    }

    @Override
    public void move(Event event) {
        transitionMode(event);
        activeMode.move();
    }

    public void update(Event event) {
        switch (event.getType()) {
            case PAC_MAN_SUPER_SUGAR_COLLISION:
                transitionMode(event);
                break;
            case PAC_MAN_GHOST_COLLISION:
                final Ghost collidedGhost = ((PacMan2GhostCollisionEvent) event).getGhost();
                if (this == collidedGhost) {
                    transitionMode(event);
                }
                break;
            default:
                throw new IllegalArgumentException("event of type : " + event.getType() + "is not supported by the Ghost Blinky");
        }
    }
}
