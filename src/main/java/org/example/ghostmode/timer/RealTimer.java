package org.example.ghostmode.timer;

import java.time.Instant;

public class RealTimer implements RealTimerI {
    private double start;
    private float period;

    @Override
    public void start(float period) {
        this.period = period;
        this.start = Instant.now().getEpochSecond();
    }

    @Override
    public boolean up() {
        final double now = Instant.now().getEpochSecond();
        if (now - start >= period) {
            return true;
        }
        return false;
    }
}
