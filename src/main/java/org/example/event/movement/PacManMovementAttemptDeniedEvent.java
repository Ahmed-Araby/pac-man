package org.example.event.movement;

import lombok.Getter;
import org.example.constant.DirectionsE;
import org.example.entity.CanvasCoordinate;
import org.example.event.Event;
import org.example.event.EventType;

@Getter
public class PacManMovementAttemptDeniedEvent extends Event {
    private CanvasCoordinate requestedPacManCanvasRectTopLeftCorner;
    private DirectionsE requestedDirection;
    private Object movementAttemptSource;
    public PacManMovementAttemptDeniedEvent(CanvasCoordinate requestedPacManCanvasRectTopLeftCorner, DirectionsE requestedDirection, Object movementAttemptSource) {
        super(EventType.PAC_MAN_MOVEMENT_ATTEMPT_DENIED);
        this.requestedPacManCanvasRectTopLeftCorner = requestedPacManCanvasRectTopLeftCorner;
        this.requestedDirection = requestedDirection;
        this.movementAttemptSource = movementAttemptSource;
    }
}
