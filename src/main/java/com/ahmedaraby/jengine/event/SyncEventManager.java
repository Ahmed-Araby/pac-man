package com.ahmedaraby.jengine.event;

import com.ahmedaraby.game.pacman.event.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyncEventManager<T, E extends Event<T>> implements Publisher<T, E> {
    private final Map<T, List<Subscriber>> subscribers;


    public SyncEventManager() {
        subscribers = new HashMap<>();
    }

    @Override
    public void subscribe(T type, Subscriber subscriber) {
        subscribers
                .computeIfAbsent(type, key -> new ArrayList<>())
                .add(subscriber);
    }

    @Override
    public void unSubscribe(T type, Subscriber subscriber) {
        subscribers.get(type).remove(subscriber);

    }

    @Override
    public void notifySubscribers(E event) {
        subscribers
                .get(event.getType())
                .forEach(sub -> sub.update(event));
    }
}
