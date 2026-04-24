package org.example.collision.tmp;

import org.example.event.collision.CollisionDetectionEvent;
import org.example.model.CollisionReport;

import java.util.Optional;

public interface CollisionDetector {

    Optional<CollisionReport> detect(CollisionDetectionEvent event);
}
