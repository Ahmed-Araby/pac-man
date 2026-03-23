package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class CanvasRect {
    private final CanvasCoordinate topLeftCorner;
    private final double width;
    private final double height;
}
