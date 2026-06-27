package com.ahmedaraby.game.pacman.util;
import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.entity.MazeCell;
import com.ahmedaraby.game.pacman.playground.Playground;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BfsUtil {


    public static int[][] getDistMat(MazeCell source, MazeCell target) {
        // init
        final int[][] dist = new int[Playground.height()][Playground.width()];
        for(int r=0; r < dist.length; r++) {
            for(int c=0; c<dist[0].length; c++) {
                dist[r][c] = Integer.MAX_VALUE;
            }
        }

        final Queue<MazeCell> cords = new LinkedList<>();
        cords.add(source);
        dist[source.getRow()][source.getCol()] = 0;
        boolean targetReached = false;
        while(!cords.isEmpty() && !targetReached) {
            final MazeCell cCell = cords.poll();

            final List<MazeCell> ninetyDegMoves = cCell.getAdjCells(DimensionsC.MAZE_WIDTH, DimensionsC.MAZE_HEIGHT);
            for (MazeCell nextCell: ninetyDegMoves) {
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


    public static List<MazeCell> constructPath(MazeCell sCord, MazeCell tCord, int[][] dist) {
        if (sCord.equals(tCord)) {
            return List.of(sCord);
        }

        MazeCell cord = tCord;
        List<MazeCell> path = new ArrayList<>();
        path.add(tCord);
        while(!cord.equals(sCord)) {
            final List<MazeCell> ninetyDegMoves = cord.getAdjCells(DimensionsC.MAZE_WIDTH, DimensionsC.MAZE_HEIGHT);
            int minDist = dist[cord.getRow()][cord.getCol()];

            for(MazeCell nextCord : ninetyDegMoves) {
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
