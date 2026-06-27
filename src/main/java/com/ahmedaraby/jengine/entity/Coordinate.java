package com.ahmedaraby.jengine.entity;

import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.entity.MazeCell;
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

    public MazeCell toCell(Vector dir) {
        if (Vector.RIGHT.equals(dir) || Vector.DOWN.equals(dir)) {
            return toCellFlooring();
        } else if (Vector.LEFT.equals(dir) || Vector.UP.equals(dir)) {
            return toCellCeiling();
        } else {
            return toCellFlooring();
        }
    }

    private MazeCell toCellFlooring() {
        final int mazeRow = (int) Math.floor(row / DimensionsC.MAZE_CELL_SIZE_PIXELS);
        final int mazeCol = (int) Math.floor(col / DimensionsC.MAZE_CELL_SIZE_PIXELS);
        return new MazeCell(mazeRow, mazeCol);
    }

    private MazeCell toCellCeiling() {
        final int mazeRow = (int) Math.ceil(row / DimensionsC.MAZE_CELL_SIZE_PIXELS);
        final int mazeCol = (int) Math.ceil(col / DimensionsC.MAZE_CELL_SIZE_PIXELS);
        return new MazeCell(mazeRow, mazeCol);
    }

    public Vector getMovementDir(Coordinate to) {
        if(to.getRow() > row) {
            return Vector.DOWN;
        } else if(to.getRow() < row) {
            return Vector.UP;
        } else if(to.getCol() > col) {
            return Vector.RIGHT;
        } else if(to.getCol() < col) {
            return Vector.LEFT;
        } else {
            return Vector.STILL;
        }
    }
}
