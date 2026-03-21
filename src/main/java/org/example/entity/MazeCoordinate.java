package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class MazeCoordinate {
    private int row, col;


    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof MazeCoordinate)) {
            return false;
        }
        return row == ((MazeCoordinate) o).getRow() && col == ((MazeCoordinate) o).getCol();
    }
}
