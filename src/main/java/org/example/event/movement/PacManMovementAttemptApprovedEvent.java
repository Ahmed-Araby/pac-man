package org.example.event.movement;

import lombok.Getter;
import org.example.constant.DirectionsE;
import org.example.entity.CanvasCoordinate;
import org.example.event.EventType;
import org.example.event.Event;

@Getter
public class PacManMovementAttemptApprovedEvent extends Event {
    private CanvasCoordinate currentPacManCanvasRectTopLeftCorner;
    private CanvasCoordinate requestedPacManCanvasRectTopLeftCorner;
    private DirectionsE requestedDirection;
    private Object movementAttemptSource;

    public PacManMovementAttemptApprovedEvent(CanvasCoordinate currentPacManCanvasRectTopLeftCorner, CanvasCoordinate requestedPacManCanvasRectTopLeftCorner, DirectionsE requestedDirection, Object movementAttemptSource) {
        super(EventType.PAC_MAN_MOVEMENT_ATTEMPT_APPROVED);
        this.currentPacManCanvasRectTopLeftCorner = currentPacManCanvasRectTopLeftCorner;
        this.requestedPacManCanvasRectTopLeftCorner = requestedPacManCanvasRectTopLeftCorner;
        this.requestedDirection = requestedDirection;
        this.movementAttemptSource = movementAttemptSource;
    }
}
