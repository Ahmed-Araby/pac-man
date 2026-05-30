package com.ahmedaraby.game.pacman.ghostmode;

import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;

public abstract class Chaser extends TemporalGhostMode{
    public Chaser(Ghost ghost, GameState gameState, int[] activePeriodsSec) {
        super(ghost, gameState, activePeriodsSec);
    }
}
