package com.ahmedaraby.game.pacman.event.movement;

import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.game.pacman.event.Event;
import com.ahmedaraby.game.pacman.event.EventType;
import com.ahmedaraby.jengine.entity.Vector;
import lombok.Getter;

// [TODO] remove this event
@Getter
public class PacManMovementAttemptDeniedEvent extends Event<EventType> {
    private Vector requestedDir;
    private Object movementAttemptSource;
    public PacManMovementAttemptDeniedEvent(Vector requestedDir, Object movementAttemptSource) {
        super(EventType.PAC_MAN_MOVEMENT_ATTEMPT_DENIED);
        this.requestedDir = requestedDir;
        this.movementAttemptSource = movementAttemptSource;
    }
}
