package org.example.collision.sprite;

import org.example.constant.SpriteE;
import org.example.entity.CanvasCoordinate;
import org.example.entity.CanvasRect;
import org.example.event.EventType;
import org.example.event.collision.PacMan2GhostCollisionEvent;
import org.example.event.collision.PacMan2SugarCollisionEvent;
import org.example.event.manager.EventManager;
import org.example.ghostmode.Ghost;
import org.example.model.CollisionReport;
import org.example.model.GameState;
import org.example.util.SpriteUtil;

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
        final CanvasCoordinate pacmanTopLeftCorner = gameState.getPacMan().getCurrCanvasCord();
        final CanvasRect pacManRect = SpriteUtil.toRect(pacmanTopLeftCorner, SpriteE.PAC_MAN);

        final Optional<CollisionReport> reportOpt = M2SSpriteCollisionDetector.detect(pacManRect, SpriteE.SUGAR);
        reportOpt.ifPresent((report) -> {
            final CanvasRect collidingRect = report.getCollidingObjects().getFirst();
            final PacMan2SugarCollisionEvent collisionEvent = new PacMan2SugarCollisionEvent(
                    EventType.PAC_MAN_SUGAR_COLLISION, collidingRect);
            asyncEventManager.notifySubscribers(collisionEvent);
        });
    }

    private void detectPacman2SuperSugarCollision() {
        final CanvasCoordinate pacmanTopLeftCorner = gameState.getPacMan().getCurrCanvasCord();
        final CanvasRect pacManRect = SpriteUtil.toRect(pacmanTopLeftCorner, SpriteE.PAC_MAN);

        final Optional<CollisionReport> reportOpt = M2SSpriteCollisionDetector.detect(pacManRect, SpriteE.SUPER_SUGAR);
        reportOpt.ifPresent((report) -> {
            final CanvasRect collidingRect = report.getCollidingObjects().getFirst();
            final PacMan2SugarCollisionEvent collisionEvent = new PacMan2SugarCollisionEvent(
                    EventType.PAC_MAN_SUPER_SUGAR_COLLISION, collidingRect);
            asyncEventManager.notifySubscribers(collisionEvent);
        });
    }

    private void detectPacman2GhostCollision() {
        final CanvasCoordinate pacManTopLeftCorner = gameState.getPacMan().getCurrCanvasCord();
        final CanvasRect pacManRect = SpriteUtil.toRect(pacManTopLeftCorner, SpriteE.PAC_MAN);

        for(Ghost ghost: gameState.getGhosts()) {
            final CanvasRect ghostRect = SpriteUtil.toRect(ghost.getTopLeftCorner(), SpriteE.GHOST);
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
