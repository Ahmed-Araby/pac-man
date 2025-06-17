package org.example.event.movement;

import lombok.Getter;
import org.example.constant.DirectionsE;
import org.example.entity.Coordinate;
import org.example.event.EventType;
import org.example.event.Event;

@Getter
public class PacManMovementAttemptEvent extends Event {
    private Coordinate currentPacManCanvasRectTopLeftCorner;
    private final Coordinate requestedPacManCanvasRectTopLeftCorner;
    private final DirectionsE requestedDirection;
    private final Object source;

    public PacManMovementAttemptEvent(Coordinate currentPacManCanvasRectTopLeftCorner, Coordinate requestedPacManCanvasRectTopLeftCorner, DirectionsE requestedDirection, Object source) {
        super(EventType.PAC_MAN_MOVEMENT_ATTEMPT);
        this.currentPacManCanvasRectTopLeftCorner = currentPacManCanvasRectTopLeftCorner;
        this.requestedPacManCanvasRectTopLeftCorner = requestedPacManCanvasRectTopLeftCorner;
        this.requestedDirection = requestedDirection;
        this.source = source;
    }
}
