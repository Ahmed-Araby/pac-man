package com.ahmedaraby.game.pacman.constant;

import com.ahmedaraby.jengine.entity.Vector;
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

    public static DirectionsE fromVector(Vector dir) {
        if (Vector.RIGHT.equals(dir)) {
            return DirectionsE.RIGHT;
        } else if (Vector.UP.equals(dir)) {
            return DirectionsE.UP;
        } else if (Vector.LEFT.equals(dir)) {
            return DirectionsE.LEFT;
        } else if (Vector.DOWN.equals(dir)) {
            return DirectionsE.DOWN;
        } else if (Vector.STILL.equals(dir)) {
            return DirectionsE.STILL;
        } else {
            throw new IllegalStateException("unhandled Vector : " + dir);
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
