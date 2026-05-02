package org.example.event.manager;

import org.example.event.Event;
import org.example.event.EventType;
import org.example.event.Publisher;
import org.example.event.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class SyncEventManager implements Publisher {
    List<Subscriber> pacManMovementRequestEventSubscribers;


    public SyncEventManager() {
        pacManMovementRequestEventSubscribers = new ArrayList<>();
    }

    @Override
    public void subscribe(EventType type, Subscriber subscriber) {
        switch (type) {
            case PAC_MAN_MOVEMENT_REQUEST:
                pacManMovementRequestEventSubscribers.add(subscriber);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void unSubscribe(EventType type, Subscriber subscriber) {
        switch (type) {
            case PAC_MAN_MOVEMENT_REQUEST:
                pacManMovementRequestEventSubscribers.remove(subscriber);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void notifySubscribers(Event event) {
        switch (event.getType()) {
            case PAC_MAN_MOVEMENT_REQUEST:
                pacManMovementRequestEventSubscribers.forEach(sub -> sub.update(event));
                break;
            default:
                throw new IllegalArgumentException();
        }
    }
}
