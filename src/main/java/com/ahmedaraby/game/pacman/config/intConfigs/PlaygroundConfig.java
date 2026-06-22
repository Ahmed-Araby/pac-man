package com.ahmedaraby.game.pacman.config.intConfigs;

import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class PlaygroundConfig {
    private double cellSize;
    private Color wallColor;
    private Color backgroundColor;
}
