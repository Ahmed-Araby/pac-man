package org.example.event;

import lombok.Getter;
import org.example.constant.DirectionsE;

@Getter
public class PacManMovementEvent extends Event{
    private final DirectionsE directionsE;
    private final Object source;

    public PacManMovementEvent(EventType type, DirectionsE directionsE, Object source) {
        super(type);
        this.directionsE = directionsE;
        this.source = source;
    }

    public PacManMovementEvent(DirectionsE directionsE, Object source) {
        super(EventType.PAC_MAN_MOVEMENT);
        this.directionsE = directionsE;
        this.source = source;
    }
}
