package org.example.collision.sprite;

import org.example.event.collision.CollisionDetectionEvent;
import org.example.model.CollisionReport;

import java.util.Optional;

public interface SpriteCollisionDetector {

    // [TODO] replace event argument, events should  represent something that already happened
    Optional<CollisionReport> detect(CollisionDetectionEvent event);
}
