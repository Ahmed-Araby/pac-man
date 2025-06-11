package org.example.constant;

import javafx.scene.input.KeyCode;

public enum DirectionsE {
    RIGHT,
    UP,
    LEFT,
    DOWN,
    STILL;

    public static DirectionsE from(KeyCode code) {
        switch (code) {
            case RIGHT:
                return RIGHT;
            case UP:
                return UP;
            case DOWN:
                return DOWN;
            case LEFT:
                return LEFT;
            case SPACE:
                return STILL;
            default:
                throw new IllegalStateException();
        }
    }
}
