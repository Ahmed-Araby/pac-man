package org.example.sprite.ghost;

import org.example.constant.DirectionsE;
import org.example.constant.SpriteE;
import org.example.entity.CanvasCoordinate;
import org.example.event.Event;
import org.example.event.EventType;
import org.example.ghostmode.*;
import org.example.model.GameState;
import org.example.sprite.MovingSprite;

public abstract class Ghost extends MovingSprite {
    protected TemporalGhostMode scattered;
    protected TemporalGhostMode chaser;
    protected TemporalGhostMode frightened;
    protected GhostMode eaten;

    protected GhostMode activeMode;
    protected TemporalGhostMode previousMode;


    public Ghost(GameState gameState, SpriteE type, double col, double row, DirectionsE dir) {
        super(gameState, type, col, row, dir);
    }

    public abstract CanvasCoordinate getPacManCanvasCord();


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
        } else if (activeMode instanceof Scattered || activeMode instanceof Chaser) {
            ScatterOrChaserModeTransition(event);
        }
    }

    protected void ScatterOrChaserModeTransition(Event event) {
        TemporalGhostMode temporalActiveMode = (TemporalGhostMode) activeMode;
        if (event != null && EventType.PAC_MAN_SUPER_SUGAR_COLLISION.equals(event.getType())) {
            temporalActiveMode.pause();
            previousMode = temporalActiveMode;
            temporalActiveMode = frightened;
            temporalActiveMode.enter();
        } else if (temporalActiveMode.ended() && temporalActiveMode instanceof Chaser) {
            temporalActiveMode.exit();
            temporalActiveMode = scattered;
            temporalActiveMode.enter();
        } else if (temporalActiveMode.ended() && temporalActiveMode instanceof Scattered) {
            temporalActiveMode.exit();
            temporalActiveMode = chaser;
            temporalActiveMode.enter();
        }
        activeMode = temporalActiveMode;
    }

    protected void frightenedTransition(Event event) {
        if (event != null && EventType.PAC_MAN_GHOST_COLLISION.equals(event.getType())) {
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
}
