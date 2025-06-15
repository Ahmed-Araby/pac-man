package org.example.event;

import lombok.Getter;
import org.example.constant.DirectionsE;
import org.example.entity.Coordinate;

@Getter
public class PacManMovementEvent extends Event{
    private final DirectionsE directionsE;
    private final Coordinate pacManCanvasRectTopLeftCorner;
    private final Object source;

    public PacManMovementEvent(EventType type, Coordinate pacManCanvasRectTopLeftCorner, DirectionsE directionsE, Object source) {
        super(type);
        this.pacManCanvasRectTopLeftCorner = pacManCanvasRectTopLeftCorner;
        this.directionsE = directionsE;
        this.source = source;
    }

    public PacManMovementEvent(Coordinate pacManCanvasRectTopLeftCorner, DirectionsE directionsE, Object source) {
        super(EventType.PAC_MAN_MOVEMENT);
        this.pacManCanvasRectTopLeftCorner = pacManCanvasRectTopLeftCorner;
        this.directionsE = directionsE;
        this.source = source;
    }
}
