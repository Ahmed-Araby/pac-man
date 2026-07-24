package com.ahmedaraby.game.pacman.ghostmode;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import com.ahmedaraby.jengine.animation.Animator;
import com.ahmedaraby.jengine.animation.DistanceBasedAnimator;
import com.ahmedaraby.jengine.entity.Vector;
import com.ahmedaraby.jengine.sprite.SpriteRegistry;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public abstract class GhostMode {
    protected final Ghost ghost;
    protected final GameState gameState;
    protected final ConfigsEx configs;
    protected final SpriteRegistry<String, Image> spriteRegistry;
    protected Animator animator;

    public GhostMode(Ghost ghost, GameState gameState, ConfigsEx configs, SpriteRegistry<String, Image> spriteRegistry, double[] frameDistance) {
        this.ghost = ghost;
        this.gameState = gameState;
        this.configs = configs;
        this.spriteRegistry = spriteRegistry;
        final Image[] frames = loadSprites();
        this.animator = new DistanceBasedAnimator(frameDistance, frames);
    }

    public abstract void render(Canvas canvas);
    public abstract void move();
    public abstract void enter();
    public abstract boolean ended();
    protected abstract Image[] loadSprites();

    public void init() {
        System.out.println("GhostMode.init() method is not implemented for sprite " + this.getClass().getSimpleName());
    }

    }
}
