package com.ahmedaraby.game.pacman.ghostmode;

import com.ahmedaraby.game.pacman.config.Configs;
import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.ghostmode.navigation.GhostNavigator;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import com.ahmedaraby.game.pacman.util.ghost.GhostUtil;
import com.ahmedaraby.jengine.animation.Animator;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.sprite.SpriteRegistry;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public abstract class Scattered extends TemporalGhostMode {
    protected GhostNavigator navigator;
    protected Animator animator;
    protected Coordinate target;

    public Scattered(Ghost ghost, GameState gameState, SpriteRegistry<String, Image> spriteRegistry, int[] activePeriodsSec) {
        super(ghost, gameState, spriteRegistry, activePeriodsSec);
    }

    @Override
    public void render(Canvas canvas) {
        final GraphicsContext con = canvas.getGraphicsContext2D();
        con.drawImage(animator.getFrame(), ghost.getCol(), ghost.getRow());
    }

    @Override
    public void move() {
        final DirectionsE newDir = navigator.nextMoveDirection(ghost.getTopLeftCorner(), target);
        if (newDir != DirectionsE.STILL) {
            final Coordinate newCord = GhostUtil.move(ghost.getTopLeftCorner(), newDir);
            ghost.setTopLeftCorner(newCord);
            ghost.setDir(newDir);
            animator.stride(DimensionsC.GHOST_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_STRIDE);
        }
    }

    protected Image[] loadSprites(String[] frameRelativePaths) {
        final List<Image> spriteList = new ArrayList<>();
        for (String path : frameRelativePaths) {
            spriteList.add(spriteRegistry.get(path));
        }
        return spriteList.toArray(new Image[0]);
    }
}
