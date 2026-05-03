package org.example.event.collision;

import lombok.Getter;
import org.example.entity.CanvasRect;
import org.example.event.Event;
import org.example.event.EventType;

public class PacMan2SugarCollisionEvent extends Event {

    @Getter
    private final CanvasRect sugarRect;

    public PacMan2SugarCollisionEvent(EventType type, CanvasRect sugarRect) {
        super(type);
        this.sugarRect = sugarRect;
    }
}
