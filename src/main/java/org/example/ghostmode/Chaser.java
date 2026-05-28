package org.example.ghostmode;

import org.example.model.GameState;
import org.example.sprite.ghost.Ghost;

public abstract class Chaser extends TemporalGhostMode{
    public Chaser(Ghost ghost, GameState gameState, int[] activePeriodsSec) {
        super(ghost, gameState, activePeriodsSec);
    }
}
