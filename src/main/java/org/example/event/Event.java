package org.example.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

// [TODO] rethink the Events structure
@AllArgsConstructor
public abstract class Event {
    @Getter
    private EventType type;
}
