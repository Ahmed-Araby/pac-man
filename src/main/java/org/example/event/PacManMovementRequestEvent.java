package org.example.event;

import lombok.Getter;
import org.example.constant.DirectionsE;

@Getter
public class PacManMovementRequestEvent extends Event{
    private final DirectionsE directionsE;
    private final Object source;

    public PacManMovementRequestEvent(EventType type, DirectionsE directionsE, Object source) {
        super(type);
        this.directionsE = directionsE;
        this.source = source;
    }

    public PacManMovementRequestEvent(DirectionsE directionsE, Object source) {
        super(EventType.PAC_MAN_MOVEMENT_REQUEST);
        this.directionsE = directionsE;
        this.source = source;
    }
}
