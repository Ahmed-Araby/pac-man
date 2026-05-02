package org.example.event;

import lombok.Getter;
import org.example.entity.CanvasRect;

public class PacMan2SugarCollisionEvent extends Event {

    @Getter
    private final CanvasRect sugarRect;

    public PacMan2SugarCollisionEvent(EventType type, CanvasRect sugarRect) {
        super(type);
        this.sugarRect = sugarRect;
    }
}
