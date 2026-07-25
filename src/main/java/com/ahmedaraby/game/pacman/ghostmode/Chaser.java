package com.ahmedaraby.game.pacman.ghostmode;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.ghostmode.navigation.TargetNavigator;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import com.ahmedaraby.jengine.sprite.SpriteRegistry;
import javafx.scene.image.Image;

public abstract class Chaser extends TemporalGhostMode{
    protected TargetNavigator targetNavigator;
    public Chaser(Ghost ghost, GameState gameState, ConfigsEx configs, SpriteRegistry<String, Image> spriteRegistry, int[] activePeriodsSec, double[] frameDistance) {
        super(ghost, gameState, configs, spriteRegistry, activePeriodsSec, frameDistance);
        targetNavigator = new TargetNavigator(configs);
    }
}
