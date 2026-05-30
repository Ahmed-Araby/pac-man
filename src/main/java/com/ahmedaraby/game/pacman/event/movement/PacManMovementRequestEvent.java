package com.ahmedaraby.game.pacman.event.movement;

import com.ahmedaraby.game.pacman.event.Event;
import com.ahmedaraby.game.pacman.event.EventType;
import lombok.Getter;
import com.ahmedaraby.game.pacman.constant.DirectionsE;

@Getter
public class PacManMovementRequestEvent extends Event {
    private final DirectionsE directionsE;
    private final Object source;


    public PacManMovementRequestEvent(DirectionsE directionsE, Object source) {
        super(EventType.PAC_MAN_MOVEMENT_REQUEST);
        this.directionsE = directionsE;
        this.source = source;
    }
}
