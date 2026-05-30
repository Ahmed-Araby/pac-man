package com.ahmedaraby.game.pacman.collision;

import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.entity.Rectangle;
import com.ahmedaraby.game.pacman.event.EventType;
import com.ahmedaraby.game.pacman.event.collision.PacMan2GhostCollisionEvent;
import com.ahmedaraby.game.pacman.event.collision.PacMan2SugarCollisionEvent;
import com.ahmedaraby.game.pacman.event.manager.EventManager;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import com.ahmedaraby.jengine.models.CollisionReport;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.util.SpriteUtil;

import java.util.Optional;

public class CollisionSystem {

    private final GameState gameState;
    private final EventManager asyncEventManager;

    public CollisionSystem(GameState gameState, EventManager asyncEventManager) {
        this.gameState = gameState;
        this.asyncEventManager = asyncEventManager;
    }


    public void detect() {
        // [TODO] optimize, collision detection in intersecting area, can happen in one pass regardless of the target sprite
        detectPacman2SugarCollision();
        detectPacman2SuperSugarCollision();
        detectPacman2GhostCollision();
    }

    private void detectPacman2SugarCollision() {
        final Coordinate pacmanTopLeftCorner = gameState.getPacMan().getTopLeftCorner();
        final Rectangle pacManRect = SpriteUtil.toRect(pacmanTopLeftCorner, SpriteE.PAC_MAN);

        final Optional<CollisionReport> reportOpt = M2SSpriteCollisionDetector.detect(pacManRect, SpriteE.SUGAR);
        reportOpt.ifPresent((report) -> {
            final Rectangle collidingRect = report.getCollidingObjects().getFirst();
            final PacMan2SugarCollisionEvent collisionEvent = new PacMan2SugarCollisionEvent(
                    EventType.PAC_MAN_SUGAR_COLLISION, collidingRect);
            asyncEventManager.notifySubscribers(collisionEvent);
        });
    }

    private void detectPacman2SuperSugarCollision() {
        final Coordinate pacmanTopLeftCorner = gameState.getPacMan().getTopLeftCorner();
        final Rectangle pacManRect = SpriteUtil.toRect(pacmanTopLeftCorner, SpriteE.PAC_MAN);

        final Optional<CollisionReport> reportOpt = M2SSpriteCollisionDetector.detect(pacManRect, SpriteE.SUPER_SUGAR);
        reportOpt.ifPresent((report) -> {
            final Rectangle collidingRect = report.getCollidingObjects().getFirst();
            final PacMan2SugarCollisionEvent collisionEvent = new PacMan2SugarCollisionEvent(
                    EventType.PAC_MAN_SUPER_SUGAR_COLLISION, collidingRect);
            asyncEventManager.notifySubscribers(collisionEvent);
        });
    }

    private void detectPacman2GhostCollision() {
        final Coordinate pacManTopLeftCorner = gameState.getPacMan().getTopLeftCorner();
        final Rectangle pacManRect = SpriteUtil.toRect(pacManTopLeftCorner, SpriteE.PAC_MAN);

        for(Ghost ghost: gameState.getGhosts()) {
            final Rectangle ghostRect = SpriteUtil.toRect(ghost.getTopLeftCorner(), SpriteE.GHOST);
            M2MSpriteCollisionDetector.detect(pacManRect, ghostRect).ifPresent((report)-> {
                final PacMan2GhostCollisionEvent collisionEvent = new PacMan2GhostCollisionEvent(ghost);
                asyncEventManager.notifySubscribers(collisionEvent);
            });
        }

    }

    // [TODO] implement pacman to ghosts collision detection

    public void analyzeMovementAttempt() {
    }

    private void analyzePacmanMovementAttempt() {
    }

    private void analyzeGhostMovementAttempt() {
    }
}
