package com.ahmedaraby.game.pacman.util;
import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.entity.Cell;
import com.ahmedaraby.game.pacman.playground.Playground;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PlaygroundShortestPathNav {


    public int calcDist(Cell source, Cell target) {
        final int[][] dist = calcBfsBasedDistMatrix(source, target);
        return dist[target.getRow()][target.getCol()];
    }

    private int[][] calcBfsBasedDistMatrix(Cell source, Cell target) {
        // init
        final int[][] dist = new int[Playground.height()][Playground.width()];
        for(int r=0; r < dist.length; r++) {
            for(int c=0; c<dist[0].length; c++) {
                dist[r][c] = Integer.MAX_VALUE;
            }
        }

        // BFS
        final Queue<Cell> cords = new LinkedList<>();
        cords.add(source);
        dist[source.getRow()][source.getCol()] = 0;
        boolean targetReached = false;
        while(!cords.isEmpty() && !targetReached) {
            final Cell cCell = cords.poll();

            final List<Cell> ninetyDegMoves = cCell.getAdjCells(DimensionsC.MAZE_WIDTH, DimensionsC.MAZE_HEIGHT);
            for (Cell nextCell: ninetyDegMoves) {
                if(nextCell.equals(target)) {
                    dist[nextCell.getRow()][nextCell.getCol()] = Math.min(dist[nextCell.getRow()][nextCell.getCol()],
                            1 + dist[cCell.getRow()][cCell.getCol()]);
                    targetReached = true;
                    break;
                }
                else if(!Playground.isWall(nextCell) && !Playground.isGhostHWall(nextCell)) {
                    if (dist[nextCell.getRow()][nextCell.getCol()] > 1 + dist[cCell.getRow()][cCell.getCol()]) {
                        dist[nextCell.getRow()][nextCell.getCol()] = 1 + dist[cCell.getRow()][cCell.getCol()];
                        cords.add(nextCell);
                    }
                }
            }
        }
        return dist;
    }


    public static List<Cell> constructPath(Cell sCord, Cell tCord, int[][] dist) {
        if (sCord.equals(tCord)) {
            return List.of(sCord);
        }

        Cell cord = tCord;
        List<Cell> path = new ArrayList<>();
        path.add(tCord);
        while(!cord.equals(sCord)) {
            final List<Cell> ninetyDegMoves = cord.getAdjCells(DimensionsC.MAZE_WIDTH, DimensionsC.MAZE_HEIGHT);
            int minDist = dist[cord.getRow()][cord.getCol()];

            for(Cell nextCord : ninetyDegMoves) {
                if(dist[nextCord.getRow()][nextCord.getCol()] < minDist) {
                    minDist = dist[nextCord.getRow()][nextCord.getCol()];
                    cord = nextCord;
                }
            }

            path.add(cord);
        }

        return path.reversed();
    }
}
