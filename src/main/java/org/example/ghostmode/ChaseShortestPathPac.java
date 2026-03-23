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
import org.example.util.GhostUtil;
import org.example.util.MazeUtil;

import java.util.List;


@AllArgsConstructor
public class ChaseShortestPathPac implements GhostMode {

    private final GhostToWallCollisionDetection ghostToWallCollisionDetection;

    public DirectionsE nextMoveDirection(CanvasCoordinate ghostCord, CanvasCoordinate pacCord, SpriteE[][] maze) {
        if(ghostCord.equals(pacCord)) {
            return DirectionsE.STILL;
        }
        final List<MazeMove> possibleMoves = getCandidateMoves(ghostCord, pacCord, maze);
        System.out.println("possible Moves = " + possibleMoves);
        return possibleMoves
                .stream()
                .filter(move -> move.getDist2Target() < Integer.MAX_VALUE)
                .map(move -> {
                    final CanvasCoordinate nextCord = MazeUtil.getCanvasCord(move.getSource());
                    final DirectionsE movementDir = CanvasUtil.getMovementDir(ghostCord, nextCord);
                    return new GhostMovementAttemptEvent(ghostCord, movementDir);
                })
                .filter(event -> !ghostToWallCollisionDetection.checkCollision(event))
                .map(GhostMovementAttemptEvent::getMovementDir)
                .findFirst()
                .orElse(DirectionsE.STILL);
    }

    private List<MazeMove> getCandidateMoves(CanvasCoordinate ghostCord, CanvasCoordinate pacCord, SpriteE[][] maze) {
        // this work can be parallelized
        final MazeCell pacCell = CanvasUtil.toMazeCoordinate(pacCord, DirectionsE.STILL);
        final List<MazeCell> candidateNextMazeCell = GhostUtil.getCandidateNextCells(ghostCord, maze);
        return candidateNextMazeCell
                .stream()
                .map(interestingCell -> {
                    final int dist = getDistToPacCell(interestingCell, pacCell, maze);
                    return new MazeMove(interestingCell, dist);
                })
                .sorted()
                .toList();
    }

    private int getDistToPacCell(MazeCell sourceCell, MazeCell pacCell, SpriteE[][] maze) {
        final int[][] dist = BfsUtil.getDistMat(sourceCell, pacCell, maze);
        return dist[pacCell.getRow()][pacCell.getCol()];
    }
}
