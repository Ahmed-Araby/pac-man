package org.example.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public abstract class Event {
    @Getter
    private EventType type;
}
