package org.example.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.ghostmode.Ghost;

@Getter
public class PacMan2GhostCollisionEvent extends Event {
    private final Ghost ghost;

    public PacMan2GhostCollisionEvent(Ghost ghost) {
        super(EventType.PAC_MAN_GHOST_COLLISION);
        this.ghost = ghost;
    }
}
