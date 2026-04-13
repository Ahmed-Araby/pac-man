package org.example.event;

import lombok.Getter;
import org.example.entity.CanvasCoordinate;

import java.util.List;

public class PacManSugarCollisionEvent extends Event {

    @Getter
    private final List<CanvasCoordinate> eatenSugarCanvasRect;

    public PacManSugarCollisionEvent(EventType type, List<CanvasCoordinate> eatenSugarCanvasRect) {
        super(type);
        this.eatenSugarCanvasRect = eatenSugarCanvasRect;
    }
}
