package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class CanvasCoordinate {
    private double row, col;


    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof CanvasCoordinate)) {
            return false;
        }
        return row == ((CanvasCoordinate) o).getRow() && col == ((CanvasCoordinate) o).getCol();
    }
}
