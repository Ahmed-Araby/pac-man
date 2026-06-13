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
import lombok.Setter;

public abstract class Scattered extends TemporalGhostMode {
    protected Coordinate target;

    public Scattered(Ghost ghost, GameState gameState, int[] activePeriodsSec) {
        super(ghost, gameState, activePeriodsSec);
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
}
