package com.ahmedaraby.game.pacman.event.movement;

import com.ahmedaraby.game.pacman.event.Event;
import com.ahmedaraby.game.pacman.event.EventType;
import com.ahmedaraby.jengine.entity.Vector;
import lombok.Getter;

@Getter
public class PacManMovementRequestEvent extends Event<EventType> {
    private final Vector dir;
    private final Object source;


    public PacManMovementRequestEvent(Vector dir, Object source) {
        super(EventType.PAC_MAN_MOVEMENT_REQUEST);
        this.dir = dir;
        this.source = source;
    }
}
