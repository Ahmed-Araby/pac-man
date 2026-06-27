package com.ahmedaraby.game.pacman.entity;

import com.ahmedaraby.jengine.entity.Coordinate;
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
public class MazeCell {
    private int row, col;

    private static final int[] dRow = {-1, 1, 0, 0}; // up, down, don't change, don't change
    private static final int[] dCol = {0, 0, 1, -1}; // don't change, don't change, right, left

    public List<MazeCell> getAdjCells(int playgroundWidth, int playgroundHeight) {
        final List<MazeCell> ninetyDegMoves = new ArrayList<>();
        int nRow, nCol;
        for(int i=0; i<4; i++) {
            nRow = row + dRow[i];
            nCol = col + dCol[i];
            if(nRow == -1 || nCol == -1 || nCol == playgroundWidth || nRow == playgroundHeight) {
                continue;
            }
            ninetyDegMoves.add(new MazeCell(nRow, nCol));
        }


        return ninetyDegMoves;
    }

    public Coordinate toCord(double cellWidth, double cellHeight) {
        return new Coordinate(row * cellHeight, col * cellWidth);
    }
}
