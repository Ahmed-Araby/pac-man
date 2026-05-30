package com.ahmedaraby.game.pacman.event.collision;

import lombok.Getter;
import com.ahmedaraby.game.pacman.entity.CanvasRect;
import com.ahmedaraby.game.pacman.event.Event;
import com.ahmedaraby.game.pacman.event.EventType;

public class PacMan2SugarCollisionEvent extends Event {

    @Getter
    private final CanvasRect sugarRect;

    public PacMan2SugarCollisionEvent(EventType type, CanvasRect sugarRect) {
        super(type);
        this.sugarRect = sugarRect;
    }
}
