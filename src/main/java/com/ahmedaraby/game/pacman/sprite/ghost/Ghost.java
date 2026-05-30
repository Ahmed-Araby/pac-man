package com.ahmedaraby.game.pacman.sprite.ghost;

import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.game.pacman.entity.CanvasCoordinate;
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

public abstract class Ghost extends MovingSprite {
    protected TemporalGhostMode scattered;
    protected TemporalGhostMode chaser;
    protected TemporalGhostMode frightened;
    protected GhostMode eaten;

    protected GhostMode activeMode;
    protected TemporalGhostMode previousMode;


    public Ghost(GameState gameState, SpriteE type, double col, double row, DirectionsE dir) {
        super(gameState, type, new CanvasCoordinate(row, col), DimensionsC.GHOST_WIDTH_PIXELS, DimensionsC.GHOST_HEIGHT_PIXELS, dir);
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
