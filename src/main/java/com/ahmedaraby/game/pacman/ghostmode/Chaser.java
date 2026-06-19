package com.ahmedaraby.game.pacman.ghostmode;

import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import com.ahmedaraby.jengine.sprite.SpriteRegistry;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public abstract class Chaser extends TemporalGhostMode{
    public Chaser(Ghost ghost, GameState gameState, SpriteRegistry<String, Image> spriteRegistry, int[] activePeriodsSec) {
        super(ghost, gameState, spriteRegistry, activePeriodsSec);
    }

    protected Image[] loadSprites(String[] frameRelativePaths) {
        final List<Image> spriteList = new ArrayList<>();
        for (String path : frameRelativePaths) {
            spriteList.add(spriteRegistry.get(path));
        }
        return spriteList.toArray(new Image[0]);
    }
}
