package com.ahmedaraby.game.pacman.config.intConfigs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class AnimationConfig {
    private double completeDist;
    private List<Double> percentages;
}
