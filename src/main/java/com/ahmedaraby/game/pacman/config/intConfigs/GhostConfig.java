package com.ahmedaraby.game.pacman.config.intConfigs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GhostConfig {
    private double width;
    private double height;
    private InternalGhost blinky;
    private InternalGhost inky;
    private InternalGhost pinky;
    private InternalGhost clyde;

    @NoArgsConstructor
    @Getter
    @Setter
    public static class InternalGhost {
        private double speed;
        private AnimationConfig animation;
    }
}