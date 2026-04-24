package org.example.collision.tmp;

import org.example.event.manager.SyncEventManager;

public class CollisionSystem {

    private final M2SCollisionDetector m2SCollisionDetector;
    private final M2MCollisionDetector m2MCollisionDetector;
    private final SyncEventManager syncEventManager;

    public CollisionSystem(SyncEventManager syncEventManager) {
        this.m2SCollisionDetector = new M2SCollisionDetector();
        this.m2MCollisionDetector = new M2MCollisionDetector();
        this.syncEventManager = syncEventManager;
    }


    public void detect() {

    }

    public void analyzeMovementAttempt() {
    }

    private void analyzePacmanMovementAttempt() {
    }

    private void analyzeGhostMovementAttempt() {
    }
}
