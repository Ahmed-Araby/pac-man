package org.example.entity;

import lombok.*;

import java.util.List;

@ToString
@EqualsAndHashCode
@Getter
@Setter
public class Vector {
    public static final Vector RIGHT = new Vector(1, 0);
    public static final Vector UP = new Vector(0, -1);
    public static final Vector LEFT = new Vector(-1, 0);
    public static final Vector DOWN = new Vector(0, 1);
    public static final Vector STILL = new Vector(0, 0);
    public static final List<Vector> fourD = List.of(UP, RIGHT, DOWN, LEFT);

    private double x, y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
