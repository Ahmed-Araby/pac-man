package org.example.event;

public interface Publisher {

    void subscribe(EventType type, Subscriber subscriber);
    void unSubscribe(EventType type, Subscriber subscriber);
    void notifySubscribers(Event type);
}
