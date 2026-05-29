package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.example.constant.DimensionsC;

import java.util.List;

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


    public Line topSide() {
        final CanvasCoordinate topRightCorner = new CanvasCoordinate(0, width - 1);
        return new Line(topLeftCorner, topRightCorner);
    }

    public Line rightSide() {
        final CanvasCoordinate topRightCorner = new CanvasCoordinate(0, width - 1);
        final CanvasCoordinate bottomRightCorner = new CanvasCoordinate(height - 1, width -1);
        return new Line(topRightCorner, bottomRightCorner);
    }

    public Line bottomSide() {
        final CanvasCoordinate bottomLeftCorner = new CanvasCoordinate(height - 1, 0);
        final CanvasCoordinate bottomRightCorner = new CanvasCoordinate(height - 1, width -1);
        return new Line(bottomRightCorner, bottomLeftCorner);
    }
    public Line leftSide() {
        final CanvasCoordinate bottomLeftCorner = new CanvasCoordinate(height - 1, 0);
        return new Line(bottomLeftCorner, topLeftCorner);
    }
}
