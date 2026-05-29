package org.example.sprite.ghost;

import javafx.scene.canvas.Canvas;
import org.example.config.GhostModeActivePeriodsConf;
import org.example.constant.DimensionsC;
import org.example.constant.DirectionsE;
import org.example.constant.SpriteE;
import org.example.entity.CanvasCoordinate;
import org.example.event.Event;
import org.example.event.Subscriber;
import org.example.event.collision.PacMan2GhostCollisionEvent;
import org.example.ghostmode.common.Eaten;
import org.example.ghostmode.common.Frightened;
import org.example.ghostmode.Inky.InkyChaser;
import org.example.ghostmode.Inky.InkyScattered;
import org.example.model.GameState;
import org.example.sprite.GhostHouseS;

public class Inky extends Ghost implements Subscriber {


    public Inky(GameState gameState) {
        super(gameState, SpriteE.GHOST , 0, 0, DirectionsE.STILL);
        scattered = new InkyScattered(this, gameState, GhostModeActivePeriodsConf.LEVEL_1_SCATTER_ACTIVE_PERIODS);
        // [TODO] use InkyChaser
        chaser = new InkyChaser(this, gameState, GhostModeActivePeriodsConf.LEVEL_1_CHASE_ACTIVE_PERIODS);
        frightened = new Frightened(this, gameState, GhostModeActivePeriodsConf.ALL_LEVELS_FRIGHTENED_MODE_ACTIVE_PERIODS);
        eaten = new Eaten(this, gameState);

        this.activeMode = scattered;
        scattered.enter();
    }

    @Override
    public void init() {
        final GhostHouseS ghostHouseS = gameState.getGhostHouseS();
        final double col = ghostHouseS.getCol() + 2 * DimensionsC.MAZE_CELL_SIZE_PIXELS;
        final double row = ghostHouseS.getERow() - DimensionsC.MAZE_CELL_SIZE_PIXELS;
        setCol(col);
        setRow(row);
    }



    @Override
    public CanvasCoordinate getPacManCanvasCord() {
        // [TODO] remove this method, and instead the ghost mode should be able to access the game state and retrieve the info it needs
        return this.gameState.getPacMan().getTopLeftCorner();
    }

    @Override
    public void move(Event event) {
        transitionMode(event);
        activeMode.move();
    }

    @Override
    public void render(Canvas canvas) {
        activeMode.render(canvas);
    }

    @Override
    public void update(Event event) {
        switch (event.getType()) {
            case PAC_MAN_SUPER_SUGAR_COLLISION:
                transitionMode(event);
                break;
            case PAC_MAN_GHOST_COLLISION:
                Ghost collidedGhost = ((PacMan2GhostCollisionEvent) event).getGhost();
                if (this == collidedGhost) {
                    transitionMode(event);
                }
                break;
            default:
                throw new IllegalArgumentException("event of type : " + event.getType() + "is not supported by The Ghost Inky");
        }
    }
}
