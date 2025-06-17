package org.example.event;

public enum EventType {
    PAC_MAN_SUGAR_COLLISION,
    PAC_MAN_SUPER_SUGAR_COLLISION,
    PAC_MAN_MOVEMENT_REQUEST,  // an event represent a request to move pac man in a specific direction, which can be ignored due to collision, going out of bounds, or expiry of the turn buffer.
    PAC_MAN_MOVEMENT_ATTEMPT,
    PAC_MAN_MOVEMENT_ATTEMPT_APPROVED,
    PAC_MAN_MOVEMENT_ATTEMPT_DENIED,
    PAC_MAN_CURRENT_LOCATION; // an event represent the current situation of pac man, i.e. the current coordinates and direction
}
