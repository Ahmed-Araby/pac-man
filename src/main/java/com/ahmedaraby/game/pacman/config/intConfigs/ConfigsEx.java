package com.ahmedaraby.game.pacman.config.intConfigs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ConfigsEx {
    private CanvasConfig canvas;
    private PlaygroundConfig playground;
    private PacManConfig pacman;
    private GhostConfig ghost;
    private GhostHouseConfig ghostHouse;
    private SugarConfig sugar;
    private SuperSugarConfig superSugar;
    private SoundConfig sound;
}
