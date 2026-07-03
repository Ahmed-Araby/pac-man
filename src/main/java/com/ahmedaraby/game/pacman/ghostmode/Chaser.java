package com.ahmedaraby.game.pacman.ghostmode;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.ghostmode.navigation.GhostNavigator;
import com.ahmedaraby.game.pacman.ghostmode.navigation.ShortestPathNavigator;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import com.ahmedaraby.game.pacman.util.PlaygroundShortestPathNav;
import com.ahmedaraby.jengine.sprite.SpriteRegistry;
import javafx.scene.image.Image;

public abstract class Chaser extends TemporalGhostMode{
    protected final GhostNavigator navigator;
    public Chaser(Ghost ghost, GameState gameState, ConfigsEx configs, SpriteRegistry<String, Image> spriteRegistry, int[] activePeriodsSec) {
        super(ghost, gameState, configs, spriteRegistry, activePeriodsSec);

        PlaygroundShortestPathNav playgroundShortestPathNav = new PlaygroundShortestPathNav();
        navigator = new ShortestPathNavigator(configs, playgroundShortestPathNav);
    }
}
