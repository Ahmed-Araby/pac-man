package org.example.ghostmode;

import org.example.constant.GhostModeE;

import java.time.Instant;

public class ChaseScatterTimer {
    private int transitionCnt = 0;

    private final int[] level1Periods = { 7, 20, 7, 20, 5, 20, 5, -1 };
    private final int[] level2To4Periods = { 7, 20, 7, 29, 5, 780, 1, -1 };
    private final int[] level5UpPeriods = { 5, 20, 5, 20, 5, 780, 1, -1 };

    private double currModeStartTimestamp;

    public ChaseScatterTimer() {
        currModeStartTimestamp = Instant.now().getEpochSecond();
    }


    public GhostModeE getMode() {
        final double now = Instant.now().getEpochSecond();
        if(transitionCnt < level1Periods.length - 1 && now - currModeStartTimestamp > level1Periods[transitionCnt]) {
            transitionCnt++;
            currModeStartTimestamp = now;
            return (transitionCnt & 1) == 1 ? GhostModeE.SCATTERED : GhostModeE.CHASE;
        }

        return (transitionCnt & 1) == 1 ? GhostModeE.CHASE : GhostModeE.SCATTERED;
    }
}
