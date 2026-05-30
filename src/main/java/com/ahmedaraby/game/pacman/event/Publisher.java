package com.ahmedaraby.game.pacman.event;

public interface Publisher<T, E> {

    void subscribe(T type, Subscriber subscriber);
    void unSubscribe(T type, Subscriber subscriber);
    void notifySubscribers(E type);
}
