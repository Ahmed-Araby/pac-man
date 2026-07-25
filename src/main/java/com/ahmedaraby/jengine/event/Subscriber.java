package com.ahmedaraby.jengine.event;

import com.ahmedaraby.game.pacman.model.event.Event;

public interface Subscriber<T> {

    void update(Event<T> event);
}
