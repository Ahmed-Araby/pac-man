package com.ahmedaraby.game.pacman.event;

public interface Subscriber<T> {

    void update(Event<T> event);
}
