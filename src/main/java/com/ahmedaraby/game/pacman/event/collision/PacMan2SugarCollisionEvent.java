package com.ahmedaraby.game.pacman.event.collision;

import lombok.Getter;
import com.ahmedaraby.jengine.entity.Rectangle;
import com.ahmedaraby.game.pacman.event.Event;
import com.ahmedaraby.game.pacman.event.EventType;

public class PacMan2SugarCollisionEvent extends Event {

    @Getter
    private final Rectangle sugarRect;

    public PacMan2SugarCollisionEvent(EventType type, Rectangle sugarRect) {
        super(type);
        this.sugarRect = sugarRect;
    }
}
