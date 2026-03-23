package org.example.ghostmode;

import lombok.AllArgsConstructor;
import org.example.collision.GhostToWallCollisionDetection;
import org.example.constant.DirectionsE;
import org.example.constant.SpriteE;
import org.example.entity.CanvasCoordinate;
import org.example.entity.MazeCell;
import org.example.entity.MazeMove;
import org.example.event.ghost.GhostMovementAttemptEvent;
import org.example.util.BfsUtil;
import org.example.util.CanvasUtil;
import org.example.util.MazeUtil;

import java.util.List;


@AllArgsConstructor
public class ChaseShortestPathPac implements GhostMode {

    private final GhostToWallCollisionDetection ghostToWallCollisionDetection;

    public DirectionsE nextMoveDirection(CanvasCoordinate ghostCord, CanvasCoordinate pacCord, SpriteE[][] maze) {
        final List<MazeMove> possibleMoves = nextPositions(ghostCord, pacCord, maze);
        System.out.println("possible Moves = " + possibleMoves);
        return possibleMoves
                .stream()
                .filter(move -> move.getDist2Target() < Integer.MAX_VALUE)
                .map(move -> {
                    final CanvasCoordinate nextCord = MazeUtil.getCanvasCord(move.getNextCell());
                    final DirectionsE movementDir = CanvasUtil.getMovementDir(ghostCord, nextCord);
                    return new GhostMovementAttemptEvent(ghostCord, movementDir);
                })
                .filter(event -> !ghostToWallCollisionDetection.checkCollision(event))
                .map(GhostMovementAttemptEvent::getMovementDir)
                .findFirst()
                .orElse(DirectionsE.STILL);
    }

    private List<MazeMove> nextPositions(CanvasCoordinate ghostCord, CanvasCoordinate pacCord, SpriteE[][] maze) {
        // this work can be parallelized
        final MazeCell pacCell = CanvasUtil.toMazeCoordinate(pacCord, DirectionsE.STILL);
        final List<MazeCell> ghostInterestingMazeCells = CanvasUtil.getIntersectingMazeCells(ghostCord);
        return ghostInterestingMazeCells
                .stream()
                .map(interestingCell -> nextPosition(interestingCell, pacCell, maze))
                .sorted()
                .toList();
    }

    private MazeMove nextPosition(MazeCell sourceCell, MazeCell pacCell, SpriteE[][] maze) {
        if(sourceCell.equals(pacCell)) {
            return new MazeMove(sourceCell, Integer.MAX_VALUE);
        }

        final int[][] dist = BfsUtil.getDistMat(sourceCell, pacCell, maze);
        final List<MazeCell> path = BfsUtil.constructPath(sourceCell, pacCell, dist);

        if(path.size() < 2) {
            return new MazeMove(sourceCell, Integer.MAX_VALUE);
        } else {
            return new MazeMove(path.get(1), dist[pacCell.getRow()][pacCell.getCol()]);
        }
    }
}
