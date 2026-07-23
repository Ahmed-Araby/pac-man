package com.ahmedaraby.game.pacman.ghostmode;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.game.pacman.ghostmode.navigation.GhostNavigator;
import com.ahmedaraby.game.pacman.ghostmode.navigation.ShortestPathNavigator;
import com.ahmedaraby.game.pacman.ghostmode.navigation.TargetNavigator;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.Sprite;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import com.ahmedaraby.game.pacman.util.PlaygroundShortestPathNav;
import com.ahmedaraby.jengine.animation.DistanceBasedAnimator;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.entity.Vector;
import com.ahmedaraby.jengine.sprite.SpriteRegistry;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public abstract class Chaser extends TemporalGhostMode{
    protected final GhostNavigator navigator;
    protected DistanceBasedAnimator animator;
    private TargetNavigator targetNavigator;
    public Chaser(Ghost ghost, GameState gameState, ConfigsEx configs, SpriteRegistry<String, Image> spriteRegistry, int[] activePeriodsSec) {
        super(ghost, gameState, configs, spriteRegistry, activePeriodsSec);

        PlaygroundShortestPathNav playgroundShortestPathNav = new PlaygroundShortestPathNav();
        navigator = new ShortestPathNavigator(configs, playgroundShortestPathNav);
        targetNavigator = new TargetNavigator(configs, navigator);
    }

    protected void moveTo(Coordinate target) {
        final Sprite virtualSprite = new Sprite(gameState, configs, SpriteE.VIRTUAL, target, configs.PLAYGROUND_CELL_SIZE(), configs.PLAYGROUND_CELL_SIZE()) {
            @Override
            public void render(Canvas canvas) {
                throw new IllegalStateException("virtual target sprite is not supposed to be rendered");
            }
        };
        moveTo(virtualSprite);
    }

    protected void moveTo(Sprite target) {
        final Vector newDir = targetNavigator.calcDir(ghost, target);
        final double stride = ghost.calcStride(newDir);
        ghost.move(stride, newDir);
        animator.stride(stride);
    }
}
