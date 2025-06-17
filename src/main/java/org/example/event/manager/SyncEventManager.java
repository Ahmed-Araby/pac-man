package org.example.event.manager;

import org.example.event.Event;
import org.example.event.EventType;
import org.example.event.Publisher;
import org.example.event.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class SyncEventManager implements Publisher {
    List<Subscriber> pacManMovementRequestEventSubscribers;
    List<Subscriber> pacManMovementAttemptEventSubscribers;

    List<Subscriber> pacManMovementAttemptApprovedEventSubscribers;
    List<Subscriber> pacManMovementAttemptDeniedEventSubscribers;

    public SyncEventManager() {
        pacManMovementRequestEventSubscribers = new ArrayList<>();
        pacManMovementAttemptEventSubscribers = new ArrayList<>();

        pacManMovementAttemptApprovedEventSubscribers = new ArrayList<>();
        pacManMovementAttemptDeniedEventSubscribers = new ArrayList<>();
    }

    @Override
    public void subscribe(EventType type, Subscriber subscriber) {
        switch (type) {
            case PAC_MAN_MOVEMENT_REQUEST:
                pacManMovementRequestEventSubscribers.add(subscriber);
                break;
            case PAC_MAN_MOVEMENT_ATTEMPT:
                pacManMovementAttemptEventSubscribers.add(subscriber);
                break;
            case PAC_MAN_MOVEMENT_ATTEMPT_APPROVED:
                pacManMovementAttemptApprovedEventSubscribers.add(subscriber);
                break;
            case PAC_MAN_MOVEMENT_ATTEMPT_DENIED:
                pacManMovementAttemptDeniedEventSubscribers.add(subscriber);
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
            case PAC_MAN_MOVEMENT_ATTEMPT:
                pacManMovementAttemptEventSubscribers.remove(subscriber);
                break;
            case PAC_MAN_MOVEMENT_ATTEMPT_APPROVED:
                pacManMovementAttemptApprovedEventSubscribers.remove(subscriber);
                break;
            case PAC_MAN_MOVEMENT_ATTEMPT_DENIED:
                pacManMovementAttemptDeniedEventSubscribers.remove(subscriber);
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
            case PAC_MAN_MOVEMENT_ATTEMPT:
                pacManMovementAttemptEventSubscribers.forEach(sub -> sub.update(event));
                break;
            case PAC_MAN_MOVEMENT_ATTEMPT_APPROVED:
                pacManMovementAttemptApprovedEventSubscribers.forEach(sub -> sub.update(event));
                break;
            case PAC_MAN_MOVEMENT_ATTEMPT_DENIED:
                pacManMovementAttemptDeniedEventSubscribers.forEach(sub -> sub.update(event));
                break;
            default:
                throw new IllegalArgumentException();
        }
    }
}
