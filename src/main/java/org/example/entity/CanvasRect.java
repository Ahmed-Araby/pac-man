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

    public double rightEdgeCol() {
        return topLeftCorner.getCol() + width - 1;
    }
    public double leftEdgeCol() {
        return topLeftCorner.getCol();
    }
    public double bottomEdgeRow() {
        return topLeftCorner.getRow() + height - 1;
    }
    public double topEdgeRow() {
        return topLeftCorner.getRow();
    }
}
