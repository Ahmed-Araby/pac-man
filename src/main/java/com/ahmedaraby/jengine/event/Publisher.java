package com.ahmedaraby.jengine.event;

public interface Publisher<T, E> {

    void subscribe(T type, Subscriber subscriber);
    void unSubscribe(T type, Subscriber subscriber);
    void notify(E type);
}
