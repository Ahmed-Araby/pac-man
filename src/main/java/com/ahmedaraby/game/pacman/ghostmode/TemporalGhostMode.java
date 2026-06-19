package com.ahmedaraby.game.pacman.ghostmode;

import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import com.ahmedaraby.jengine.sprite.SpriteRegistry;
import javafx.scene.image.Image;

import java.time.Instant;


public abstract class TemporalGhostMode extends GhostMode {
    private Instant activeAt;
    private long skew = 0;
    private final int[] activePeriodsSec;
    private int activePeriodIndex;


    public TemporalGhostMode(Ghost ghost, GameState gameState, SpriteRegistry<String, Image> spriteRegistry, int[] activePeriodsSec) {
        super(ghost, gameState, spriteRegistry);
        this.activePeriodsSec = activePeriodsSec;
    }

    @Override
    public void enter() {
        activeAt = Instant.now();
    }
    public void pause() {
        final Instant now = Instant.now();
        final long sessionActiveTime = now.getEpochSecond() - activeAt.getEpochSecond();
        skew += sessionActiveTime;
    }

    @Override
    public boolean ended() {
        final int currActivePeriod = activePeriodsSec[activePeriodIndex];
        if (currActivePeriod == -1) {
            return false;
        }

        final Instant now = Instant.now();
        final long totalActiveTime = skew + now.getEpochSecond() - activeAt.getEpochSecond();
        if (totalActiveTime >= currActivePeriod) {
            return true;
        }
        return false;
    }
    public void exit() {
        skew = 0;
        activePeriodIndex++;
    }
}
