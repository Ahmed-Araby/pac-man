package org.example.collision.sprite;

import org.example.constant.SpriteE;
import org.example.entity.CanvasCoordinate;
import org.example.entity.CanvasRect;
import org.example.event.EventType;
import org.example.event.PacMan2GhostCollisionEvent;
import org.example.event.PacManSugarCollisionEvent;
import org.example.event.manager.EventManager;
import org.example.ghostmode.Ghost;
import org.example.model.CollisionReport;
import org.example.model.GameState;
import org.example.util.SpriteUtil;

import java.util.List;
import java.util.Optional;

public class CollisionSystem {

    private final M2SSpriteCollisionDetector m2SCollisionDetector;
    private final M2MSpriteCollisionDetector m2MCollisionDetector;
    private final GameState gameState;
    private final EventManager asyncEventManager;

    public CollisionSystem(GameState gameState, EventManager asyncEventManager) {
        this.m2SCollisionDetector = new M2SSpriteCollisionDetector();
        this.m2MCollisionDetector = new M2MSpriteCollisionDetector();

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

        final Optional<CollisionReport> reportOpt = this.m2SCollisionDetector.detect(pacManRect, SpriteE.SUGAR);
        reportOpt.ifPresent((report) -> {
            final CanvasRect collidingRect = report.getCollidingObjects().getFirst();
            final PacManSugarCollisionEvent collisionEvent = new PacManSugarCollisionEvent(
                    EventType.PAC_MAN_SUGAR_COLLISION, List.of(collidingRect.getTopLeftCorner()));
            asyncEventManager.notifySubscribers(collisionEvent);
        });
    }

    private void detectPacman2SuperSugarCollision() {
        final CanvasCoordinate pacmanTopLeftCorner = gameState.getPacMan().getCurrCanvasCord();
        final CanvasRect pacManRect = SpriteUtil.toRect(pacmanTopLeftCorner, SpriteE.PAC_MAN);

        final Optional<CollisionReport> reportOpt = this.m2SCollisionDetector.detect(pacManRect, SpriteE.SUPER_SUGAR);
        reportOpt.ifPresent((report) -> {
            final CanvasRect collidingRect = report.getCollidingObjects().getFirst();
            final PacManSugarCollisionEvent collisionEvent = new PacManSugarCollisionEvent(
                    EventType.PAC_MAN_SUPER_SUGAR_COLLISION, List.of(collidingRect.getTopLeftCorner()));
            asyncEventManager.notifySubscribers(collisionEvent);
        });
    }

    private void detectPacman2GhostCollision() {
        final CanvasCoordinate pacManTopLeftCorner = gameState.getPacMan().getCurrCanvasCord();
        final CanvasRect pacManRect = SpriteUtil.toRect(pacManTopLeftCorner, SpriteE.PAC_MAN);

        for(Ghost ghost: gameState.getGhosts()) {
            final CanvasRect ghostRect = SpriteUtil.toRect(ghost.getTopLeftCorner(), SpriteE.GHOST);
            m2MCollisionDetector.detect(pacManRect, ghostRect).ifPresent((report)-> {
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
