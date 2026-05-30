package com.ahmedaraby.game.pacman.event.movement;

import com.ahmedaraby.game.pacman.entity.CanvasCoordinate;
import com.ahmedaraby.game.pacman.event.Event;
import com.ahmedaraby.game.pacman.event.EventType;
import lombok.Getter;
import com.ahmedaraby.game.pacman.constant.DirectionsE;

// [TODO] remove this event
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
