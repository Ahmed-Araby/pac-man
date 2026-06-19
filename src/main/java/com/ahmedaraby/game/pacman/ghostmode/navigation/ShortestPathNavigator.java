package com.ahmedaraby.game.pacman.ghostmode.navigation;

import com.ahmedaraby.game.pacman.collision.M2SSpriteCollisionDetector;
import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.entity.MazeCell;
import com.ahmedaraby.game.pacman.model.CollisionReport;
import com.ahmedaraby.game.pacman.util.SpriteUtil;
import com.ahmedaraby.game.pacman.util.canvas.CanvasUtil;
import lombok.AllArgsConstructor;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.entity.Rectangle;
import com.ahmedaraby.game.pacman.entity.MazeMove;
import com.ahmedaraby.game.pacman.util.BfsUtil;
import com.ahmedaraby.game.pacman.util.ghost.GhostUtil;
import com.ahmedaraby.game.pacman.util.MazeUtil;

import java.util.List;


@AllArgsConstructor
public class ShortestPathNavigator implements GhostNavigator {

    // [TODO] TODO take into account the movement direction of the sprites at source and target cord
    @Override
    public double calcDist(Coordinate sourceCord, Coordinate targetCord) {
        MazeCell sourceCell = CanvasUtil.toMazeCoordinate(sourceCord, DirectionsE.STILL);
        MazeCell targetCell = CanvasUtil.toMazeCoordinate(targetCord, DirectionsE.STILL);
        return calcDist(sourceCell, targetCell) * DimensionsC.MAZE_CELL_SIZE_PIXELS;
    }

    @Override
    public DirectionsE nextMoveDirection(Coordinate source, Coordinate target) {
        if(source.equals(target)) {
            return DirectionsE.STILL;
        }
        final List<MazeMove> possibleMoves = getCandidateMoves(source, target);
        return possibleMoves
                .stream()
                .sorted()
                .filter(move -> move.getDist2Target() < Integer.MAX_VALUE)
                .filter(move -> {
                    final Coordinate candidateNextCord = MazeUtil.getCanvasCord(move.getCell());
                    final Rectangle rect = SpriteUtil.toRect(candidateNextCord, SpriteE.GHOST);
                    final List<CollisionReport> collisionReportOpt = M2SSpriteCollisionDetector.detect(rect, List.of(SpriteE.WALL, SpriteE.GHOST_HOUSE_WALL));
                    return collisionReportOpt.isEmpty();
                })
                .map(move -> {
                    final Coordinate candidateNextCord = MazeUtil.getCanvasCord(move.getCell());
                    return CanvasUtil.getMovementDir(source, candidateNextCord);
                })
                .findFirst()
                .orElse(DirectionsE.STILL);
    }

    private List<MazeMove> getCandidateMoves(Coordinate ghostCord, Coordinate targetCord) {
        // this work can be parallelized
        final MazeCell targetCell = CanvasUtil.toMazeCoordinate(targetCord, DirectionsE.STILL);
        final List<MazeCell> candidateNextMazeCell = GhostUtil.getCandidateNextCells(ghostCord);
        return candidateNextMazeCell
                .stream()
                .map(interestingCell -> {
                    final int dist = calcDist(interestingCell, targetCell);
                    return new MazeMove(interestingCell, dist);
                })
                .toList();
    }

    private int calcDist(MazeCell source, MazeCell target) {
        final int[][] dist = BfsUtil.getDistMat(source, target);
        return dist[target.getRow()][target.getCol()];
    }
}
