package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Rect {
    private final Coordinate topLeftCorner;
    private final double width;
    private final double height;
}
