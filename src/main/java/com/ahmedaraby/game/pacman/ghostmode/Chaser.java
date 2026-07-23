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

import java.util.Objects;
import java.util.Optional;

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
        final Optional<MovementPlan> movementPlan = ghost.getPossibleMazeMoves()
                .stream()
                .map(move -> {
                    final double dist = navigator.calcDist(move.getTo(), target.toCell());
                    if (dist < Integer.MAX_VALUE) {
                        return new MovementPlan(move.getDir(), dist);
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .sorted()
                .findFirst();

        if (movementPlan.isEmpty()) {
            ghost.setDirV(Vector.STILL);
            return;
        }

        final Vector newDir = movementPlan.get().getDir();
        final double stride = ghost.calcStride(newDir);
        ghost.move(stride, newDir);
        animator.stride(stride);
    }

    protected void moveTo(Sprite target) {
        final Optional<MovementPlan> movementPlan = ghost.getPossibleMazeMoves()
                .stream()
                .map(move -> {
                    final double dist = navigator.calcDist(move.getTo(), target.calcCell());
                    if (dist < Integer.MAX_VALUE) {
                        return new MovementPlan(move.getDir(), dist);
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .sorted()
                .findFirst();
        if (movementPlan.isEmpty()) {
            ghost.setDirV(Vector.STILL);
            return;
        }

        final Vector newDir = movementPlan.get().getDir();
        final double stride = ghost.calcStride(newDir);
        ghost.move(stride, newDir);
        animator.stride(stride);
    }
}
