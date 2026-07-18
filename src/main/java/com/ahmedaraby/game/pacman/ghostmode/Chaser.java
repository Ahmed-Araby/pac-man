package com.ahmedaraby.game.pacman.ghostmode;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.entity.MovementPlan;
import com.ahmedaraby.game.pacman.ghostmode.navigation.GhostNavigator;
import com.ahmedaraby.game.pacman.ghostmode.navigation.ShortestPathNavigator;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.Sprite;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import com.ahmedaraby.game.pacman.util.PlaygroundShortestPathNav;
import com.ahmedaraby.jengine.animation.DistanceBasedAnimator;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.entity.Vector;
import com.ahmedaraby.jengine.sprite.SpriteRegistry;
import javafx.scene.image.Image;

import java.util.List;
import java.util.Objects;

public abstract class Chaser extends TemporalGhostMode{
    protected final GhostNavigator navigator;
    protected DistanceBasedAnimator animator;

    public Chaser(Ghost ghost, GameState gameState, ConfigsEx configs, SpriteRegistry<String, Image> spriteRegistry, int[] activePeriodsSec) {
        super(ghost, gameState, configs, spriteRegistry, activePeriodsSec);

        PlaygroundShortestPathNav playgroundShortestPathNav = new PlaygroundShortestPathNav();
        navigator = new ShortestPathNavigator(configs, playgroundShortestPathNav);
    }

    // [TODO] resolve duplicates, the following 2 methods are almost duplicates
    protected void moveTo(Coordinate target) {
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

    protected void moveTo(Sprite target) {
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
}
