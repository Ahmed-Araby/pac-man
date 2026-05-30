package com.ahmedaraby.game.pacman.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

// [TODO] rethink the Events structure
@AllArgsConstructor
public abstract class Event<T> {
    @Getter
    private T type;
}
