package com.ahmedaraby.game.pacman.model.event.collision;

import com.ahmedaraby.game.pacman.model.event.Event;
import com.ahmedaraby.game.pacman.model.event.EventType;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import lombok.Getter;

@Getter
public class PacMan2GhostCollisionEvent extends Event<EventType> {
    private final Ghost ghost;

    public PacMan2GhostCollisionEvent(Ghost ghost) {
        super(EventType.PAC_MAN_GHOST_COLLISION);
        this.ghost = ghost;
    }
}
