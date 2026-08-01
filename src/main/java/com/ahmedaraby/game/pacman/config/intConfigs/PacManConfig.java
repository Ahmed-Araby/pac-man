package com.ahmedaraby.game.pacman.config.intConfigs;

import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PacManConfig {
    private double speed;
    private Color color;
    private double diameter;
    private int burstingArcExtentDeg;
    private int burstingArcCount;
    private int deathPeriodSec;
    private Mouth mouth;

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Mouth {
        private AnimationConfig animation;
        private OpenState open;
        private CloseState closed;

        @NoArgsConstructor
        @Getter
        @Setter
        public static class OpenState {

            private int arcExtentDeg;
            private int rightStartAngle;
            private int upStartAngle;
            private int leftStartAngle;
            private int downStartAngle;
        }

        @NoArgsConstructor
        @Getter
        @Setter
        public static class CloseState {
            private int arcExtentDeg;
            private int arcStartAngleDeg;
        }
    }

}


