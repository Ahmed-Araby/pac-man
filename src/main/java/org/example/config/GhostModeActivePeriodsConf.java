package org.example.config;

public class GhostModeActivePeriodsConf {
    // all the giving periods are in seconds

    // scatter mode active periods
    public static final int[] LEVEL_1_SCATTER_ACTIVE_PERIODS = {
            7, 7, 5, 5
    };
    public static final int[] LEVEL_2_TO_4_SCATTER_ACTIVE_PERIODS = {
            7, 7, 5, 1
    };
    public static final int[] LEVEL_5_UP_SCATTER_ACTIVE_PERIODS = {
            5, 5, 5, 1
    };

    // chase mode active periods
    public static final int[] LEVEL_1_CHASE_ACTIVE_PERIODS = {
            20, 20, 20, -1
    };
    public static final int[] LEVEL_2_TO_4_CHASE_ACTIVE_PERIODS = {
            20, 29, 780, -1
    };
    public static final int[] LEVEL_5_UP_CHASE_ACTIVE_PERIODS = {
            20, 20, 780, -1
    };

    // frightened mode active periods
    public static final int[] ALL_LEVELS_FRIGHTENED_MODE_ACTIVE_PERIODS = {9};
}
