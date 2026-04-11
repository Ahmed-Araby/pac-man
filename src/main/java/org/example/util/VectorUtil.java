package org.example.util;

import org.example.constant.DirectionsE;
import org.example.entity.Vector;

public class VectorUtil {

    private VectorUtil() {}

    public static Vector toVector(DirectionsE dir) {
        return switch (dir) {
            case RIGHT -> Vector.RIGHT;
            case UP -> Vector.UP;
            case DOWN -> Vector.DOWN;
            case LEFT -> Vector.LEFT;
            case STILL -> Vector.STILL;
        };
    }

    public static DirectionsE toDirection(Vector dir) {
        if (Vector.RIGHT.equals(dir)) {
            return DirectionsE.RIGHT;
        } else if (Vector.UP.equals(dir)) {
            return DirectionsE.UP;
        } else if (Vector.DOWN.equals(dir)) {
            return DirectionsE.DOWN;
        } else if (Vector.LEFT.equals(dir)) {
            return DirectionsE.LEFT;
        } else if (Vector.STILL.equals(dir)) {
            return DirectionsE.STILL;
        }

        throw new IllegalStateException("there is no direction enum for the corresponding vector");
    }

    public static Vector flip180(Vector v) {
        final double newX = v.getX() == 0 ? 0 : -v.getX();
        final double newY = v.getY() == 0 ? 0 : -v.getY();
        return new Vector(newX, newY);
    }
    public static boolean isOpposite(Vector v1, Vector v2) {
        return flip180(v1).equals(v2);
    }




}
