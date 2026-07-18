package com.ahmedaraby.game.pacman.entity;

import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.entity.Vector;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Cell {
    private int row, col;

    private static final int[] dRow = {-1, 1, 0, 0}; // up, down, don't change, don't change
    private static final int[] dCol = {0, 0, 1, -1}; // don't change, don't change, right, left

    private static final Vector[] directions = {Vector.RIGHT, Vector.UP, Vector.DOWN, Vector.LEFT};

    public List<MazeMove> getMoves(int playgroundWidth, int playgroundHeight) {
        final List<MazeMove> moves = new ArrayList<>();
        int nRow, nCol;
        for(int i=0; i<4; i++) {
            nRow = (int) (row + directions[i].getY());
            nCol = (int) (col + directions[i].getX());
            if(nRow == -1 || nCol == -1 || nCol == playgroundWidth || nRow == playgroundHeight) {
                continue;
            }
            moves.add(new MazeMove(this, new Cell(nRow, nCol), directions[i]));
        }
        return moves;
    }

    public List<Cell> getAdjCells(int playgroundWidth, int playgroundHeight) {
        final List<Cell> cells = new ArrayList<>();
        int nRow, nCol;
        for(int i=0; i<4; i++) {
            nRow = (int) (row + directions[i].getY());
            nCol = (int) (col + directions[i].getX());
            if(nRow == -1 || nCol == -1 || nCol == playgroundWidth || nRow == playgroundHeight) {
                continue;
            }
            cells.add(new Cell(nRow, nCol));
        }
        return cells;
    }

    public Coordinate toCord(double cellWidth, double cellHeight) {
        return new Coordinate(row * cellHeight, col * cellWidth);
    }
}
