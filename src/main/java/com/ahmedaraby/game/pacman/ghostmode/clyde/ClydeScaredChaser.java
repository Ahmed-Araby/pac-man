package com.ahmedaraby.game.pacman.ghostmode.clyde;

import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import com.ahmedaraby.jengine.sprite.SpriteRegistry;
import javafx.scene.image.Image;

public class ClydeScaredChaser extends ClydeScattered {
    public ClydeScaredChaser(Ghost ghost, GameState gameState, SpriteRegistry<String, Image> spriteRegistry) {
        super(ghost, gameState, spriteRegistry, null);
    }

    @Override
    public boolean ended() {
        return false;
    }

    @Override
    public void enter() {}

    @Override
    public void pause() {}

    @Override
    public void exit() {}
}
