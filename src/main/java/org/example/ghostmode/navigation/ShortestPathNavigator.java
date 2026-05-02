package org.example.ghostmode.navigation;

import lombok.AllArgsConstructor;
import org.example.collision.sprite.M2SSpriteCollisionDetector;
import org.example.constant.DirectionsE;
import org.example.constant.SpriteE;
import org.example.entity.CanvasCoordinate;
import org.example.entity.CanvasRect;
import org.example.entity.MazeCell;
import org.example.entity.MazeMove;
import org.example.model.CollisionReport;
import org.example.util.BfsUtil;
import org.example.util.SpriteUtil;
import org.example.util.canvas.CanvasUtil;
import org.example.util.ghost.GhostUtil;
import org.example.util.MazeUtil;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
public class ShortestPathNavigator implements GhostNavigator {

    public DirectionsE nextMoveDirection(CanvasCoordinate ghostCord, CanvasCoordinate targetCord) {
        if(ghostCord.equals(targetCord)) {
            return DirectionsE.STILL;
        }
        final List<MazeMove> possibleMoves = getCandidateMoves(ghostCord, targetCord);
        return possibleMoves
                .stream()
                .sorted()
                .filter(move -> move.getDist2Target() < Integer.MAX_VALUE)
                .filter(move -> {
                    final CanvasCoordinate candidateNextCord = MazeUtil.getCanvasCord(move.getCell());
                    final CanvasRect rect = SpriteUtil.toRect(candidateNextCord, SpriteE.GHOST);
                    final Optional<CollisionReport> collisionReportOpt = M2SSpriteCollisionDetector.detect(rect, SpriteE.WALL);
                    return collisionReportOpt.isEmpty();
                })
                .map(move -> {
                    final CanvasCoordinate candidateNextCord = MazeUtil.getCanvasCord(move.getCell());
                    return CanvasUtil.getMovementDir(ghostCord, candidateNextCord);
                })
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
