package com.ahmedaraby.game.pacman.ghostmode;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.entity.MovementPlan;
import com.ahmedaraby.game.pacman.ghostmode.navigation.GhostNavigator;
import com.ahmedaraby.game.pacman.ghostmode.navigation.ShortestPathNavigator;
import com.ahmedaraby.game.pacman.model.GameState;
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
import java.util.Objects;

public abstract class Scattered extends TemporalGhostMode {
    protected final GhostNavigator navigator;
    protected Animator animator;
    protected Coordinate target;

    public Scattered(Ghost ghost, GameState gameState, ConfigsEx configs, SpriteRegistry<String, Image> spriteRegistry, int[] activePeriodsSec) {
        super(ghost, gameState, configs, spriteRegistry, activePeriodsSec);
        PlaygroundShortestPathNav playgroundShortestPathNav = new PlaygroundShortestPathNav();
        navigator = new ShortestPathNavigator(configs, playgroundShortestPathNav);
    }

    @Override
    public void render(Canvas canvas) {
        final GraphicsContext con = canvas.getGraphicsContext2D();
        con.drawImage(animator.getFrame(), ghost.getCol(), ghost.getRow());
    }

    @Override
    public void move() {
        List<Vector> possibleDirections = ghost.getPossibleDirections();
        final List<MovementPlan> movementPlanList = possibleDirections
                .stream()
                .map(ghost::buildNextSprite)
                .filter(Objects::nonNull)
                .map(nextGhost -> {
                    final double dist = navigator.calcDist(nextGhost, target);
                    if (dist < Integer.MAX_VALUE) {
                        return new MovementPlan(nextGhost.getDirV(), dist);
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .sorted()
                .toList();
        if (movementPlanList.isEmpty()) {
            ghost.setDirV(Vector.STILL);
            return;
        }

        final Vector newDir = movementPlanList.get(0).getDir();
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
