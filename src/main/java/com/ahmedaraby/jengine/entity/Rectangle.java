package com.ahmedaraby.jengine.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Rectangle {
    private final Coordinate topLeftCorner;
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

    // corners
    public Coordinate topLeftCorner() {
        return topLeftCorner;
    }

    public Coordinate topRightCorner() {
       return new Coordinate(0, width - 1);
    }

    public Coordinate bottomRightCorner() {
        return new Coordinate(height - 1, width -1);
    }

    public Coordinate bottomLeftCorner() {
        return new Coordinate(height - 1, 0);
    }


    // sides
    public Line topSide() {
        return new Line(topLeftCorner, topRightCorner());
    }

    public Line rightSide() {
        return new Line(topRightCorner(), bottomRightCorner());
    }

    public Line bottomSide() {
        return new Line(bottomRightCorner(), bottomLeftCorner());
    }
    public Line leftSide() {
        final Coordinate bottomLeftCorner = new Coordinate(height - 1, 0);
        return new Line(bottomLeftCorner, topLeftCorner);
    }

    public boolean within(Rectangle rect) {
        return topLeftCorner.within(rect)
                && topRightCorner().within(rect)
                && bottomRightCorner().within(rect)
                && bottomLeftCorner().within(rect);
    }
}
