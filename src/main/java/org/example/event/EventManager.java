package org.example.event;

import java.util.ArrayList;
import java.util.List;

public class EventManager implements Publisher{
    List<Subscriber> pacManSugarCollisionEventSubscribers;
    List<Subscriber> pacManSuperSugarCollisionSubscribers;
    List<Subscriber> pacManMovementAttemptEventSubscribers;
    public EventManager() {
        pacManSugarCollisionEventSubscribers = new ArrayList<>();
        pacManSuperSugarCollisionSubscribers = new ArrayList<>();
        pacManMovementAttemptEventSubscribers = new ArrayList<>();
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
            case PAC_MAN_MOVEMENT_ATTEMPT:
                pacManMovementAttemptEventSubscribers.add(subscriber);
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
            case PAC_MAN_MOVEMENT_ATTEMPT:
                pacManMovementAttemptEventSubscribers.remove(subscriber);
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
            case PAC_MAN_MOVEMENT_ATTEMPT:
                pacManMovementAttemptEventSubscribers.forEach(sub -> sub.update(event));
                break;
            default:
                throw new IllegalArgumentException();
        }
    }
}
