package org.example.util;
import org.example.constant.SpriteE;
import org.example.entity.MazeCell;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BfsUtil {


    public static int[][] getDistMat(MazeCell sCord, MazeCell tCord, SpriteE[][] maze) {
        // init
        final int[][] dist = new int[maze.length][maze[0].length];
        for(int r=0; r < dist.length; r++) {
            for(int c=0; c<dist[0].length; c++) {
                dist[r][c] = Integer.MAX_VALUE;
            }
        }

        final Queue<MazeCell> cords = new LinkedList<>();
        cords.add(sCord);
        dist[sCord.getRow()][sCord.getCol()] = 0;
        boolean targetReached = false;
        while(!cords.isEmpty() && !targetReached) {
            final MazeCell cCord = cords.poll();

            final List<MazeCell> ninetyDegMoves = MazeUtil.get90DegMoves(cCord);
            for (MazeCell nextCord: ninetyDegMoves) {
                if(nextCord.equals(tCord)) {
                    dist[nextCord.getRow()][nextCord.getCol()] = Math.min(dist[nextCord.getRow()][nextCord.getCol()],
                            1 + dist[cCord.getRow()][cCord.getCol()]);
                    targetReached = true;
                    break;
                }
                else if(maze[nextCord.getRow()][nextCord.getCol()] != SpriteE.WALL) {
                    dist[nextCord.getRow()][nextCord.getCol()] = Math.min(dist[nextCord.getRow()][nextCord.getCol()],
                            1 + dist[cCord.getRow()][cCord.getCol()]);
                    cords.add(nextCord);
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
            final List<MazeCell> ninetyDegMoves = MazeUtil.get90DegMoves(cord);
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
