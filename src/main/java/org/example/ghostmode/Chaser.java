package org.example.ghostmode;

import org.example.sprite.ghost.Ghost;

public abstract class Chaser extends TemporalGhostMode{
    public Chaser(Ghost ghost, int[] activePeriodsSec) {
        super(ghost, activePeriodsSec);
    }
}
