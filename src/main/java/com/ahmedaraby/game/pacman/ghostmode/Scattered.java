package com.ahmedaraby.game.pacman.ghostmode;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.ghostmode.navigation.GhostNavigator;
import com.ahmedaraby.game.pacman.ghostmode.navigation.ShortestPathNavigator;
import com.ahmedaraby.game.pacman.ghostmode.navigation.TargetNavigator;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.Sprite;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import com.ahmedaraby.game.pacman.util.PlaygroundShortestPathNav;
import com.ahmedaraby.jengine.animation.Animator;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.entity.Vector;
import com.ahmedaraby.jengine.sprite.SpriteRegistry;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public abstract class Scattered extends TemporalGhostMode {
    protected final GhostNavigator navigator;
    protected Animator animator;
    protected Coordinate target;
    private TargetNavigator targetNavigator;

    public Scattered(Ghost ghost, GameState gameState, ConfigsEx configs, SpriteRegistry<String, Image> spriteRegistry, int[] activePeriodsSec) {
        super(ghost, gameState, configs, spriteRegistry, activePeriodsSec);
        PlaygroundShortestPathNav playgroundShortestPathNav = new PlaygroundShortestPathNav();
        navigator = new ShortestPathNavigator(configs, playgroundShortestPathNav);
        targetNavigator = new TargetNavigator(configs, navigator);
    }

    @Override
    public void render(Canvas canvas) {
        final GraphicsContext con = canvas.getGraphicsContext2D();
        con.drawImage(animator.getFrame(), ghost.getCol(), ghost.getRow());
    }

    @Override
    public void move() {
        final Sprite virtualSprite = Sprite.buildVirtualSprite(target, configs.PLAYGROUND_CELL_SIZE(), configs.PLAYGROUND_CELL_SIZE(), configs);
        final Vector newDir = targetNavigator.calcDir(ghost, virtualSprite);
        final double stride = ghost.calcStride(newDir);
        ghost.move(stride, newDir);
        animator.stride(stride);
    }

    protected Image[] loadSprites(String[] frameRelativePaths) {
        final List<Image> spriteList = new ArrayList<>();
        for (String path : frameRelativePaths) {
            spriteList.add(spriteRegistry.get(path));
        }
        return spriteList.toArray(new Image[0]);
    }
}
