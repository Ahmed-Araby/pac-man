package org.example.constant;

import javafx.scene.input.KeyCode;
import org.example.entity.Vector;

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

    public KeyCode toKeyCode() {
        switch (this) {
            case RIGHT:
                return KeyCode.RIGHT;
            case UP:
                return KeyCode.UP;
            case DOWN:
                return KeyCode.DOWN;
            case LEFT:
                return KeyCode.LEFT;
            case STILL:
                return KeyCode.SPACE;
            default:
                throw new IllegalStateException();
        }
    }

    public Vector toVector() {
        switch (this) {
            case RIGHT:
                return Vector.RIGHT;
            case UP:
                return Vector.UP;
            case LEFT:
                return Vector.LEFT;
            case DOWN:
                return Vector.DOWN;
            case STILL:
                return Vector.STILL;
            default:
                throw new IllegalStateException("unhandled DirectionE constant : " + this);
        }
    }
}
