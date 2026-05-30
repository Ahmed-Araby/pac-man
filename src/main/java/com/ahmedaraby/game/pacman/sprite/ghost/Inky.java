package com.ahmedaraby.game.pacman.sprite.ghost;

import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.game.pacman.event.Event;
import com.ahmedaraby.game.pacman.event.EventType;
import com.ahmedaraby.jengine.event.Subscriber;
import com.ahmedaraby.game.pacman.event.collision.PacMan2GhostCollisionEvent;
import com.ahmedaraby.game.pacman.ghostmode.common.Eaten;
import com.ahmedaraby.game.pacman.ghostmode.common.Frightened;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.playground.GhostHouseS;
import javafx.scene.canvas.Canvas;
import com.ahmedaraby.game.pacman.config.GhostModeActivePeriodsConf;
import com.ahmedaraby.game.pacman.ghostmode.Inky.InkyChaser;
import com.ahmedaraby.game.pacman.ghostmode.Inky.InkyScattered;

public class Inky extends Ghost implements Subscriber<EventType> {


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
        super.init();
        final GhostHouseS ghostHouseS = gameState.getGhostHouseS();
        final double col = ghostHouseS.getCol() + 2 * DimensionsC.MAZE_CELL_SIZE_PIXELS;
        final double row = ghostHouseS.getERow() - DimensionsC.MAZE_CELL_SIZE_PIXELS;
        setCol(col);
        setRow(row);
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
    public void update(Event<EventType> event) {
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
