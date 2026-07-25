package com.ahmedaraby.game.pacman.model.event.collision;

import lombok.Getter;
import com.ahmedaraby.jengine.entity.Rectangle;
import com.ahmedaraby.game.pacman.model.event.Event;
import com.ahmedaraby.game.pacman.model.event.EventType;

public class PacMan2SugarCollisionEvent extends Event<EventType> {

    @Getter
    private final Rectangle sugarRect;

    public PacMan2SugarCollisionEvent(EventType type, Rectangle sugarRect) {
        super(type);
        this.sugarRect = sugarRect;
    }
}
