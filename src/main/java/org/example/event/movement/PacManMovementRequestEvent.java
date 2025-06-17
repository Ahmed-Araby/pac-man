package org.example.event.movement;

import lombok.Getter;
import org.example.constant.DirectionsE;
import org.example.event.Event;
import org.example.event.EventType;

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
