package org.example.ghostmode;

import org.example.sprite.ghost.Ghost;

public abstract class Scattered extends TemporalGhostMode {
    public Scattered(Ghost ghost, int[] activePeriodsSec) {
        super(ghost, activePeriodsSec);
    }
}
