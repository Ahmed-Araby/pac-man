package org.example.event;

import lombok.Getter;
import org.example.constant.DirectionsE;
import org.example.entity.CanvasCoordinate;

@Getter
public class PacManCurrentLocationEvent extends Event{
    private final DirectionsE directionsE;
    private final CanvasCoordinate pacManCanvasRectTopLeftCorner;
    private final Object source;

    public PacManCurrentLocationEvent(CanvasCoordinate pacManCanvasRectTopLeftCorner, DirectionsE directionsE, Object source) {
        super(EventType.PAC_MAN_CURRENT_LOCATION);
        this.pacManCanvasRectTopLeftCorner = pacManCanvasRectTopLeftCorner;
        this.directionsE = directionsE;
        this.source = source;
    }
}
