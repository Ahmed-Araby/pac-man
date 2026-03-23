package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class MazeCell {
    private int row, col;


    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof MazeCell)) {
            return false;
        }
        return row == ((MazeCell) o).getRow() && col == ((MazeCell) o).getCol();
    }
}
