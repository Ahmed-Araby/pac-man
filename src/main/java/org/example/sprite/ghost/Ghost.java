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


    protected void transitionMode(Event event) {
        if (activeMode instanceof Chaser) {
            chaserTransition(event);
        } else if(activeMode instanceof Scattered) {
            scatteredTransition(event);
        } else if (activeMode instanceof Frightened) {
            frightenedTransition(event);
        } else if (activeMode instanceof Eaten) {
            eatenTransition(activeMode);
        }
    }

    protected void chaserTransition(Event event) {
        if (event != null && EventType.PAC_MAN_SUPER_SUGAR_COLLISION.equals(event.getType())) {
            chaser.pause();
            frightened.enter(this);
            frightened.enter();

            previousMode = chaser;
            activeMode = frightened;
        } else if (chaser.ended()) {
            chaser.exit();
            scattered.enter();

            activeMode = scattered;
        }
    }
    protected void scatteredTransition(Event event) {
        if (event != null && EventType.PAC_MAN_SUPER_SUGAR_COLLISION.equals(event.getType())) {
            scattered.pause();
            frightened.enter(this);
            frightened.enter();

            previousMode = scattered;
            activeMode = frightened;
        } else if (scattered.ended()) {
            scattered.exit();
            chaser.enter();

            activeMode = chaser;
        }
    }

    protected void frightenedTransition(Event event) {
        if (event != null && EventType.PAC_MAN_GHOST_COLLISION.equals(event.getType())) {
            eaten.enter(this);
            activeMode = eaten;
        } else if (frightened.ended()) {
            frightened.exit();
            previousMode.enter();
            activeMode = previousMode;
            previousMode = null;
        }
    }

    protected void eatenTransition(GhostMode eaten) {
        if (eaten.end(this)) {
            activeMode = previousMode;
            activeMode.enter(this);

            previousMode = null;
        }
    }
}
