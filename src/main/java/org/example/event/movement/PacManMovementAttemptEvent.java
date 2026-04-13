package org.example.event.movement;

import lombok.Getter;
import org.example.constant.DirectionsE;
import org.example.entity.CanvasCoordinate;
import org.example.event.EventType;
import org.example.event.Event;

@Getter
public class PacManMovementAttemptEvent extends Event {
    private CanvasCoordinate currentPacManCanvasRectTopLeftCorner;
    private final CanvasCoordinate requestedPacManCanvasRectTopLeftCorner;
    private final DirectionsE requestedDirection;
    private final Object source;

    public PacManMovementAttemptEvent(CanvasCoordinate currentPacManCanvasRectTopLeftCorner, CanvasCoordinate requestedPacManCanvasRectTopLeftCorner, DirectionsE requestedDirection, Object source) {
        super(EventType.PAC_MAN_MOVEMENT_ATTEMPT);
        this.currentPacManCanvasRectTopLeftCorner = currentPacManCanvasRectTopLeftCorner;
        this.requestedPacManCanvasRectTopLeftCorner = requestedPacManCanvasRectTopLeftCorner;
        this.requestedDirection = requestedDirection;
        this.source = source;
    }
}
