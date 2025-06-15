package org.example.event;

import lombok.Getter;
import org.example.constant.DirectionsE;

@Getter
public class PacManMovementAttemptEvent extends Event{
    private final DirectionsE directionsE;
    private final Object source;

    public PacManMovementAttemptEvent(EventType type, DirectionsE directionsE, Object source) {
        super(type);
        this.directionsE = directionsE;
        this.source = source;
    }

    public PacManMovementAttemptEvent(DirectionsE directionsE, Object source) {
        super(EventType.PAC_MAN_MOVEMENT_ATTEMPT);
        this.directionsE = directionsE;
        this.source = source;
    }
}
