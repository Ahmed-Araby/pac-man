package org.example.event;

import lombok.Setter;
import org.example.entity.Coordinate;

import java.util.List;

@Setter
public class PacManSugarCollisionEvent extends Event {

    private final List<Coordinate> eatenSugarCanvasRect;

    public PacManSugarCollisionEvent(EventType type, List<Coordinate> eatenSugarCanvasRect) {
        super(type);
        this.eatenSugarCanvasRect = eatenSugarCanvasRect;
    }
}
