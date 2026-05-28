package org.example.ghostmode;

import org.example.model.GameState;
import org.example.sprite.ghost.Ghost;

public abstract class Scattered extends TemporalGhostMode {
    public Scattered(Ghost ghost, GameState gameState, int[] activePeriodsSec) {
        super(ghost, gameState, activePeriodsSec);
    }
}
