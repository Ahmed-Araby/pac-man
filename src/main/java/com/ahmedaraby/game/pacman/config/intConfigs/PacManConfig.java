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
    private Mouth mouth;

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Mouth {
        private AnimationConfig animation;
        private State open;
        private State closed;

        @NoArgsConstructor
        @Getter
        @Setter
        public static class State {

            private double arcExtentDeg;
            private double rightStartAngle;
            private double upStartAngle;
            private double leftStartAngle;
            private double downStartAngle;
        }
    }

}


