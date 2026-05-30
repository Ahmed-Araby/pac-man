package com.ahmedaraby.game.pacman.sprite.ghost;

import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.game.pacman.event.EventType;
import com.ahmedaraby.game.pacman.event.collision.PacMan2GhostCollisionEvent;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.playground.GhostHouseS;
import javafx.scene.canvas.Canvas;
import com.ahmedaraby.game.pacman.config.GhostModeActivePeriodsConf;
import com.ahmedaraby.game.pacman.event.Event;
import com.ahmedaraby.jengine.event.Subscriber;
import com.ahmedaraby.game.pacman.ghostmode.blinky.BlinkyChaser;
import com.ahmedaraby.game.pacman.ghostmode.common.Eaten;
import com.ahmedaraby.game.pacman.ghostmode.common.Frightened;
import com.ahmedaraby.game.pacman.ghostmode.blinky.BlinkyScattered;

public class Blinky extends Ghost implements Subscriber<EventType> {

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
        super.init();
        final GhostHouseS ghostHouseS = gameState.getGhostHouseS();
        final double col = ghostHouseS.getCol() + DimensionsC.MAZE_CELL_SIZE_PIXELS;
        final double row = ghostHouseS.getERow() - DimensionsC.MAZE_CELL_SIZE_PIXELS;
        setCol(col);
        setRow(row);
    }

    @Override
    public void render(Canvas canvas) {
        activeMode.render(canvas);
    }

    @Override
    public void move(Event event) {
        transitionMode(event);
        activeMode.move();
    }

    public void update(Event<EventType> event) {
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
