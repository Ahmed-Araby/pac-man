package org.example.ghostmode;

import org.example.constant.DirectionsE;
import org.example.constant.SpriteE;
import org.example.entity.MazeCoordinate;
import org.example.util.BfsUtil;
import org.example.util.CoordinateUtil;

import java.util.List;


public class ChaseShortestPathPac implements GhostMode {

    @Override
    public MazeCoordinate nextPosition(MazeCoordinate ghostCord, MazeCoordinate pacCord, SpriteE[][] maze) {
        if(ghostCord.equals(pacCord)) {
            return ghostCord;
        }

        final int[][] dist = BfsUtil.getDistMat(ghostCord, pacCord, maze);
        final List<MazeCoordinate> path = BfsUtil.constructPath(ghostCord, pacCord, dist);

        if(path.size() < 2) {
            return ghostCord;
        } else {
            return path.get(1);
        }
    }

    public DirectionsE nextMoveDirection(MazeCoordinate ghostCurCord, MazeCoordinate ghostNextCord) {
        return CoordinateUtil.getMovementDir(ghostCurCord, ghostNextCord);
    }
}
