package com.ahmedaraby.game.pacman.sprite.ghost;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.game.pacman.event.collision.PacMan2GhostCollisionEvent;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.game.pacman.event.Event;
import com.ahmedaraby.game.pacman.event.EventType;
import com.ahmedaraby.game.pacman.ghostmode.Chaser;
import com.ahmedaraby.game.pacman.ghostmode.GhostMode;
import com.ahmedaraby.game.pacman.ghostmode.Scattered;
import com.ahmedaraby.game.pacman.ghostmode.TemporalGhostMode;
import com.ahmedaraby.game.pacman.ghostmode.common.Eaten;
import com.ahmedaraby.game.pacman.ghostmode.common.Frightened;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.MovingSprite;
import com.ahmedaraby.jengine.event.Subscriber;

public abstract class Ghost extends MovingSprite implements Subscriber<EventType> {
    protected TemporalGhostMode scattered;
    protected TemporalGhostMode chaser;
    protected TemporalGhostMode frightened;
    protected GhostMode eaten;

    protected GhostMode activeMode;
    protected TemporalGhostMode previousMode;


    public Ghost(GameState gameState, ConfigsEx configs, SpriteE type, double col, double row, DirectionsE dir) {
        super(gameState, configs, type, new Coordinate(row, col), configs.GHOST_WIDTH(), configs.GHOST_HEIGHT(), dir);
    }

    @Override
    public void init() {
        scattered.init();
        chaser.init();
        frightened.init();
        eaten.init();
    }

    protected void transitionMode(Event event) {
        if (activeMode instanceof Frightened) { // check for this first
            frightenedTransition(event);
        } else if (activeMode instanceof Eaten) {
            eatenTransition(activeMode);
        } else if (activeMode instanceof Scattered) {
            scatteredTransition(event);
        } else if (activeMode instanceof Chaser) {
            chaserTransition(event);
        }
    }

    protected void scatteredTransition(Event event) {
        TemporalGhostMode temporalActiveMode = (TemporalGhostMode) activeMode;
        if (event != null && EventType.PAC_MAN_SUPER_SUGAR_COLLISION.equals(event.getType())) {
            temporalActiveMode.pause();
            previousMode = temporalActiveMode;
            temporalActiveMode = frightened;
            temporalActiveMode.enter();
        } else if (temporalActiveMode.ended()) {
            temporalActiveMode.exit();
            temporalActiveMode = chaser;
            temporalActiveMode.enter();
        }
        activeMode = temporalActiveMode;
    }

    protected void chaserTransition(Event event) {
        TemporalGhostMode temporalActiveMode = (TemporalGhostMode) activeMode;
        if (event != null && EventType.PAC_MAN_SUPER_SUGAR_COLLISION.equals(event.getType())) {
            temporalActiveMode.pause();
            previousMode = temporalActiveMode;
            temporalActiveMode = frightened;
            temporalActiveMode.enter();
        } else if (temporalActiveMode.ended()) {
            temporalActiveMode.exit();
            temporalActiveMode = scattered;
            temporalActiveMode.enter();
        }
        activeMode = temporalActiveMode;
    }

    protected void frightenedTransition(Event event) {
        if (event != null && EventType.PAC_MAN_GHOST_COLLISION.equals(event.getType())) {
            // [TODO] exit from frightened mode
            eaten.enter();
            activeMode = eaten;
        } else if (frightened.ended()) {
            frightened.exit();
            previousMode.enter();
            activeMode = previousMode;
            previousMode = null;
        }
    }

    protected void eatenTransition(GhostMode eaten) {
        if (eaten.ended()) {
            activeMode = previousMode;
            activeMode.enter();

            previousMode = null;
        }
    }

    @Override
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
                throw new IllegalArgumentException("event of type : " + event.getType() + "is not supported by the Ghost" + this.getClass().getSimpleName());
        }
    }
}
