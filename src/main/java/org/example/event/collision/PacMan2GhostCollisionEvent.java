package org.example.event.collision;

import lombok.Getter;
import org.example.event.Event;
import org.example.event.EventType;
import org.example.ghostmode.Ghost;

@Getter
public class PacMan2GhostCollisionEvent extends Event {
    private final Ghost ghost;

    public PacMan2GhostCollisionEvent(Ghost ghost) {
        super(EventType.PAC_MAN_GHOST_COLLISION);
        this.ghost = ghost;
    }
}
