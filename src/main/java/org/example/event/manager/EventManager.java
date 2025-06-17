package org.example.event.manager;

import org.example.event.Event;
import org.example.event.EventType;
import org.example.event.Publisher;
import org.example.event.Subscriber;

import java.util.ArrayList;
import java.util.List;

// this EventManager is to be refactored to work in asynchronous manner using separate threads other than the JavaFX Main graphics threads
public class EventManager implements Publisher {
    List<Subscriber> pacManSugarCollisionEventSubscribers;
    List<Subscriber> pacManSuperSugarCollisionSubscribers;
    List<Subscriber> pacManCurrentLocationEventSubscribers;


    public EventManager() {
        pacManSugarCollisionEventSubscribers = new ArrayList<>();
        pacManSuperSugarCollisionSubscribers = new ArrayList<>();
        pacManCurrentLocationEventSubscribers = new ArrayList<>();
    }

    @Override
    public void subscribe(EventType type, Subscriber subscriber) {
        switch (type) {
            case PAC_MAN_SUGAR_COLLISION:
                pacManSugarCollisionEventSubscribers.add(subscriber);
                break;
            case PAC_MAN_SUPER_SUGAR_COLLISION:
                pacManSuperSugarCollisionSubscribers.add(subscriber);
                break;
            case PAC_MAN_CURRENT_LOCATION:
                pacManCurrentLocationEventSubscribers.add(subscriber);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void unSubscribe(EventType type, Subscriber subscriber) {
        switch (type) {
            case PAC_MAN_SUGAR_COLLISION:
                pacManSugarCollisionEventSubscribers.remove(subscriber);
                break;
            case PAC_MAN_SUPER_SUGAR_COLLISION:
                pacManSuperSugarCollisionSubscribers.remove(subscriber);
                break;
            case PAC_MAN_CURRENT_LOCATION:
                pacManCurrentLocationEventSubscribers.remove(subscriber);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void notifySubscribers(Event event) {
        switch (event.getType()) {
            case PAC_MAN_SUGAR_COLLISION:
                pacManSugarCollisionEventSubscribers.stream().forEach(sub -> sub.update(event));
                break;
            case PAC_MAN_SUPER_SUGAR_COLLISION:
                pacManSuperSugarCollisionSubscribers.stream().forEach(sub -> sub.update(event));
                break;
            case PAC_MAN_CURRENT_LOCATION:
                pacManCurrentLocationEventSubscribers.forEach(sub -> sub.update(event));
                break;
            default:
                throw new IllegalArgumentException();
        }
    }
}
