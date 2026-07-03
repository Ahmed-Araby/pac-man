package com.ahmedaraby.game.pacman.ghostmode.navigation;

import com.ahmedaraby.game.pacman.collision.M2SSpriteCollisionDetector;
import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.entity.MazeCell;
import com.ahmedaraby.game.pacman.model.CollisionReport;
import com.ahmedaraby.game.pacman.playground.Playground;
import com.ahmedaraby.game.pacman.sprite.MovingSprite;
import com.ahmedaraby.game.pacman.util.SpriteUtil;
import com.ahmedaraby.jengine.entity.Vector;
import lombok.AllArgsConstructor;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.entity.Rectangle;
import com.ahmedaraby.game.pacman.entity.MazeMove;
import com.ahmedaraby.game.pacman.util.BfsUtil;

import java.util.List;


@AllArgsConstructor
public class ShortestPathNavigator implements GhostNavigator {

    // [TODO] TODO take into account the movement direction of the sprites at source and target cord
    @Override
    public double calcDist(MovingSprite sprite, Coordinate targetCord) {
        MazeCell sourceCell = sprite.getTopLeftCorner().toCell(DirectionsE.STILL.toVector());
        MazeCell targetCell = targetCord.toCell(DirectionsE.STILL.toVector());
        return calcDist(sourceCell, targetCell) * DimensionsC.MAZE_CELL_SIZE_PIXELS;
    }


    @Override
    public DirectionsE calcDir(MovingSprite sprite, Coordinate target) {
        final Coordinate source = sprite.getTopLeftCorner();
        if(source.equals(target)) {
            return DirectionsE.STILL;
        }
        final List<MazeMove> possibleMoves = getCandidateMoves(sprite, target);
        return possibleMoves
                .stream()
                .sorted()
                .filter(move -> move.getDist2Target() < Integer.MAX_VALUE)
                .filter(move -> {
                    final Coordinate candidateNextCord = move.getCell().toCord(DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS);
                    final Rectangle rect = SpriteUtil.toRect(candidateNextCord, SpriteE.GHOST);
                    final List<CollisionReport> collisionReportOpt = M2SSpriteCollisionDetector.detect(rect, List.of(SpriteE.WALL, SpriteE.GHOST_HOUSE_WALL));
                    return collisionReportOpt.isEmpty();
                })
                .map(move -> {
                    final Coordinate candidateNextCord = move.getCell().toCord(DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS);
                    return source.getMovementDir(candidateNextCord);
                })
                .findFirst()
                .map(DirectionsE::fromVector)
                .orElse(DirectionsE.STILL);
    }


    private List<MazeMove> getCandidateMoves(MovingSprite moving, Coordinate target) {
        // this work can be parallelized
        final MazeCell targetCell = target.toCell(DirectionsE.STILL.toVector());
        final List<MazeCell> candidateNextMazeCell = getCandidateNextCells(moving);
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


    private List<MazeCell> getCandidateNextCells(MovingSprite sprite) {
        List<MazeCell> candidateNextCells = getIntersectingMazeCells(sprite);
        if (candidateNextCells.size() == 1) {
            // ghost lies completely in a maze cell
            MazeCell cell = sprite.getTopLeftCorner().toCell(Vector.STILL);
            candidateNextCells = cell.getAdjCells(DimensionsC.MAZE_WIDTH, DimensionsC.MAZE_HEIGHT);
        }
        return candidateNextCells
                .stream()
                .filter(cell -> !Playground.isWall(cell) && !Playground.isGhostHWall(cell))
                .toList();
    }


    private List<MazeCell> getIntersectingMazeCells(MovingSprite sprite) {
        final Rectangle rectangle = new Rectangle(sprite.getTopLeftCorner(), sprite.getWidth(), sprite.getHeight());
        final List<Coordinate> rectCorners = rectangle.corners();
        return rectCorners
                .stream()
                .map(corner -> Playground.getRectContainingPoint(corner).topLeftCorner())
                .map(topLeftCorner -> topLeftCorner.toCell(DirectionsE.STILL.toVector()))
                .distinct()
                .toList();
    }
}
