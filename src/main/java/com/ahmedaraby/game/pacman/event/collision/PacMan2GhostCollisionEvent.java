package com.ahmedaraby.game.pacman.event.collision;

import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import lombok.Getter;
import com.ahmedaraby.game.pacman.event.Event;
import com.ahmedaraby.game.pacman.event.EventType;

@Getter
public class PacMan2GhostCollisionEvent extends Event<EventType> {
    private final Ghost ghost;

    public PacMan2GhostCollisionEvent(Ghost ghost) {
        super(EventType.PAC_MAN_GHOST_COLLISION);
        this.ghost = ghost;
    }
}
