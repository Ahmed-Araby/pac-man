package com.ahmedaraby.game.pacman.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Coordinate {
    private final double row, col;

    public Coordinate(Vector v) {
        this.col = v.getX();
        this.row = v.getY();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Coordinate)) {
            return false;
        }
        return row == ((Coordinate) o).getRow() && col == ((Coordinate) o).getCol();
    }

    public Coordinate add(double colOffset, double rowOffset) {
        return new Coordinate(row + rowOffset, col + colOffset);
    }

    public boolean within(Rectangle rect) {
        return col >= rect.leftEdgeCol() && col <= rect.rightEdgeCol()
                && row >= rect.topEdgeRow() && row <= rect.bottomEdgeRow();
    }
}
