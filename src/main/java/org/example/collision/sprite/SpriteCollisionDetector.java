package org.example.collision.sprite;

import org.example.event.collision.CollisionDetectionEvent;
import org.example.model.CollisionReport;

import java.util.Optional;

public interface SpriteCollisionDetector {

    Optional<CollisionReport> detect(CollisionDetectionEvent event);
}
