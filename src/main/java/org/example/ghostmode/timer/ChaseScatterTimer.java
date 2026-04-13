package org.example.ghostmode.timer;

import java.time.Instant;

public class ChaseScatterTimer implements Timer {
    private int phaseIndex = 0;

    private final int[] level1Periods = { 7, 20, 7, 20, 5, 20, 5, -1 };
    private final int[] level2To4Periods = { 7, 20, 7, 29, 5, 780, 1, -1 };
    private final int[] level5UpPeriods = { 5, 20, 5, 20, 5, 780, 1, -1 };

    private double currPhaseStartTimeStamp;

    public ChaseScatterTimer() {
        currPhaseStartTimeStamp = Instant.now().getEpochSecond();
    }


    @Override
    public boolean up() {
        if(phaseIndex == level1Periods.length - 1) {
            return false;
        }

        final double now = Instant.now().getEpochSecond();
        if(now - currPhaseStartTimeStamp >= level1Periods[phaseIndex]) {
            currPhaseStartTimeStamp = now;
            phaseIndex++;
            return true;
        }

        return false;
    }
}
