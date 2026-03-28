package org.example.ghostmode;

import lombok.AllArgsConstructor;
import org.example.collision.GhostToWallCollisionDetection;
import org.example.constant.DirectionsE;
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
public class ShortestPathMode implements GhostMode {

    public DirectionsE nextMoveDirection(CanvasCoordinate ghostCord, CanvasCoordinate targetCord) {
        if(ghostCord.equals(targetCord)) {
            return DirectionsE.STILL;
        }
        final List<MazeMove> possibleMoves = getCandidateMoves(ghostCord, targetCord);
        return possibleMoves
                .stream()
                .sorted()
                .filter(move -> move.getDist2Target() < Integer.MAX_VALUE)
                .map(move -> {
                    final CanvasCoordinate nextCord = MazeUtil.getCanvasCord(move.getSource());
                    final DirectionsE movementDir = CanvasUtil.getMovementDir(ghostCord, nextCord);
                    return new GhostMovementAttemptEvent(ghostCord, movementDir);
                })
                .filter(event -> !GhostToWallCollisionDetection.checkCollision(event))
                .map(GhostMovementAttemptEvent::getMovementDir)
                .findFirst()
                .orElse(DirectionsE.STILL);
    }

    private List<MazeMove> getCandidateMoves(CanvasCoordinate ghostCord, CanvasCoordinate targetCord) {
        // this work can be parallelized
        final MazeCell targetCell = CanvasUtil.toMazeCoordinate(targetCord, DirectionsE.STILL);
        final List<MazeCell> candidateNextMazeCell = GhostUtil.getCandidateNextCells(ghostCord);
        return candidateNextMazeCell
                .stream()
                .map(interestingCell -> {
                    final int dist = getDistToPacCell(interestingCell, targetCell);
                    return new MazeMove(interestingCell, dist);
                })
                .toList();
    }

    private int getDistToPacCell(MazeCell sourceCell, MazeCell targetCell) {
        final int[][] dist = BfsUtil.getDistMat(sourceCell, targetCell);
        return dist[targetCell.getRow()][targetCell.getCol()];
    }
}
