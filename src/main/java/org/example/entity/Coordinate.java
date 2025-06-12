package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Coordinate {
    private double row, col;


    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Coordinate)) {
            return false;
        }
        return row == ((Coordinate) o).getRow() && col == ((Coordinate) o).getCol();
    }
}
