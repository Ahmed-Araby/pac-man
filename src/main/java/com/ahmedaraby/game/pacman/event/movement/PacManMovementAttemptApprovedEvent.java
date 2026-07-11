package com.ahmedaraby.game.pacman.event.movement;

import com.ahmedaraby.game.pacman.event.Event;
import com.ahmedaraby.game.pacman.event.EventType;
import com.ahmedaraby.jengine.entity.Vector;
import lombok.Getter;

// [TODO] remove this event
@Getter
public class PacManMovementAttemptApprovedEvent extends Event<EventType> {
    private Vector requestedDir;
    private Object movementAttemptSource;

    public PacManMovementAttemptApprovedEvent(Vector requestedDir, Object movementAttemptSource) {
        super(EventType.PAC_MAN_MOVEMENT_ATTEMPT_APPROVED);
        this.requestedDir = requestedDir;
        this.movementAttemptSource = movementAttemptSource;
    }
}
