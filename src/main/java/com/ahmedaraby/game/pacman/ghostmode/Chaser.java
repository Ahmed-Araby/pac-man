package com.ahmedaraby.game.pacman.ghostmode;

import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import com.ahmedaraby.jengine.sprite.SpriteRegistry;
import javafx.scene.image.Image;

public abstract class Chaser extends TemporalGhostMode{
    public Chaser(Ghost ghost, GameState gameState, SpriteRegistry<String, Image> spriteRegistry, int[] activePeriodsSec) {
        super(ghost, gameState, spriteRegistry, activePeriodsSec);
    }
}
