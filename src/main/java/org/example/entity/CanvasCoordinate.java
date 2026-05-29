package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class CanvasCoordinate {
    private final double row, col;

    public CanvasCoordinate(Vector v) {
        this.col = v.getX();
        this.row = v.getY();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof CanvasCoordinate)) {
            return false;
        }
        return row == ((CanvasCoordinate) o).getRow() && col == ((CanvasCoordinate) o).getCol();
    }

    public CanvasCoordinate add(double colOffset, double rowOffset) {
        return new CanvasCoordinate(row + rowOffset, col + colOffset);
    }

    public boolean within(CanvasRect rect) {
        return col >= rect.leftEdgeCol() && col <= rect.rightEdgeCol()
                && row >= rect.topEdgeRow() && row <= rect.bottomEdgeRow();
    }
}
