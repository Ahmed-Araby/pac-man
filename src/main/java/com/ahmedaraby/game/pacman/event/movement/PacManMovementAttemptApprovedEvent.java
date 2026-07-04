package com.ahmedaraby.game.pacman.event.movement;

import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.game.pacman.event.Event;
import com.ahmedaraby.game.pacman.event.EventType;
import com.ahmedaraby.jengine.entity.Vector;
import lombok.Getter;

// [TODO] remove this event
@Getter
public class PacManMovementAttemptApprovedEvent extends Event<EventType> {
    private Coordinate currentPacManCanvasRectTopLeftCorner;
    private final double calibratedStride;
    private Coordinate requestedPacManCanvasRectTopLeftCorner;
    private Vector requestedDir;
    private Object movementAttemptSource;

    public PacManMovementAttemptApprovedEvent(Coordinate currentPacManCanvasRectTopLeftCorner, double calibratedStride, Coordinate requestedPacManCanvasRectTopLeftCorner, Vector requestedDir, Object movementAttemptSource) {
        super(EventType.PAC_MAN_MOVEMENT_ATTEMPT_APPROVED);
        this.currentPacManCanvasRectTopLeftCorner = currentPacManCanvasRectTopLeftCorner;
        this.calibratedStride = calibratedStride;
        this.requestedPacManCanvasRectTopLeftCorner = requestedPacManCanvasRectTopLeftCorner;
        this.requestedDir = requestedDir;
        this.movementAttemptSource = movementAttemptSource;
    }
}
