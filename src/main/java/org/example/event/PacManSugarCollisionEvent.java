package org.example.event;

import lombok.Getter;
import org.example.entity.Coordinate;

import java.util.List;

public class PacManSugarCollisionEvent extends Event {

    @Getter
    private final List<Coordinate> eatenSugarCanvasRect;

    public PacManSugarCollisionEvent(EventType type, List<Coordinate> eatenSugarCanvasRect) {
        super(type);
        this.eatenSugarCanvasRect = eatenSugarCanvasRect;
    }
}
