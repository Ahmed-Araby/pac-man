package org.example.ghostmode;

import lombok.AllArgsConstructor;
import org.example.collision.GhostToWallCollisionDetection;
import org.example.constant.DirectionsE;
import org.example.constant.SpriteE;
import org.example.entity.CanvasCoordinate;
import org.example.entity.MazeCoordinate;
import org.example.entity.MazeMove;
import org.example.event.ghost.GhostMovementAttemptEvent;
import org.example.util.BfsUtil;
import org.example.util.CanvasUtil;

import java.util.List;


@AllArgsConstructor
public class ChaseShortestPathPac implements GhostMode {

    private final GhostToWallCollisionDetection ghostToWallCollisionDetection;

    public DirectionsE nextMoveDirection(CanvasCoordinate ghostCord, MazeCoordinate pacCord, SpriteE[][] maze) {
        final List<MazeMove> possibleMoves = nextPositions(ghostCord, pacCord, maze);
        System.out.println("possible Moves = " + possibleMoves);
        return possibleMoves
                .stream()
                .filter(move -> move.getDist2Target() < Integer.MAX_VALUE)
                .map(move -> {
                    final DirectionsE movementDir = CanvasUtil.getMovementDir(ghostCord, move.getNextCell());
                    return new GhostMovementAttemptEvent(ghostCord, movementDir);
                })
                .filter(event -> !ghostToWallCollisionDetection.checkCollision(event))
                .map(GhostMovementAttemptEvent::getMovementDir)
                .findFirst()
                .orElse(DirectionsE.STILL);
    }

    private List<MazeMove> nextPositions(CanvasCoordinate ghostCord, MazeCoordinate pacCord, SpriteE[][] maze) {
        // this work can be parallelized
        return CanvasUtil.getIntersectingMazeCells(ghostCord)
                .stream()
                .map(cord -> nextPosition(cord, pacCord, maze))
                .sorted()
                .toList();
    }

    private MazeMove nextPosition(MazeCoordinate sourceCord, MazeCoordinate pacCord, SpriteE[][] maze) {
        if(sourceCord.equals(pacCord)) {
            return new MazeMove(sourceCord, Integer.MAX_VALUE);
        }

        final int[][] dist = BfsUtil.getDistMat(sourceCord, pacCord, maze);
        final List<MazeCoordinate> path = BfsUtil.constructPath(sourceCord, pacCord, dist);

        if(path.size() < 2) {
            return new MazeMove(sourceCord, Integer.MAX_VALUE);
        } else {
            return new MazeMove(path.get(1), dist[pacCord.getRow()][pacCord.getCol()]);
        }
    }
}
