package com.ahmedaraby.game.pacman.ghostmode.navigation;

import com.ahmedaraby.game.pacman.collision.M2SSpriteCollisionDetector;
import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.entity.Cell;
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
import com.ahmedaraby.game.pacman.util.PlaygroundShortestPathNav;

import java.util.List;
import java.util.Objects;


@AllArgsConstructor
public class ShortestPathNavigator implements GhostNavigator {

    private final ConfigsEx configs;
    private final PlaygroundShortestPathNav playgroundShortestPathNav;

    @Override
    public double calcDist(MovingSprite sprite, Coordinate targetCord) {
        Cell sourceCell = sprite.getTopLeftCorner().toCell(sprite.getDirV());
        Cell targetCell = targetCord.toCell(Vector.STILL);
        return playgroundShortestPathNav.calcDist(sourceCell, targetCell) * configs.PLAYGROUND_CELL_SIZE();
    }


    @Override
    public Vector calcDir(MovingSprite sprite, Coordinate target) {
        final Coordinate source = sprite.getTopLeftCorner();
        if(source.equals(target)) {
            return Vector.STILL;
        }
        final List<MazeMove> possibleMoves = getCandidateMoves(sprite, target);
        return possibleMoves
                .stream()
                .sorted()
                .filter(move -> move.getDist2Target() < Integer.MAX_VALUE)
                .filter(move -> {
                    final Coordinate candidateNextCord = move.getCell().toCord(configs.PLAYGROUND_CELL_SIZE(), configs.PLAYGROUND_CELL_SIZE());
                    final Rectangle rect = SpriteUtil.toRect(candidateNextCord, SpriteE.GHOST);
                    final List<CollisionReport> collisionReportOpt = M2SSpriteCollisionDetector.detect(rect, List.of(SpriteE.WALL, SpriteE.GHOST_HOUSE_WALL));
                    return collisionReportOpt.isEmpty();
                })
                .map(move -> {
                    final Coordinate candidateNextCord = move.getCell().toCord(configs.PLAYGROUND_CELL_SIZE(), configs.PLAYGROUND_CELL_SIZE());
                    return source.getMovementDir(candidateNextCord);
                })
                .findFirst()
                .orElse(Vector.STILL);
    }


    private List<MazeMove> getCandidateMoves(MovingSprite moving, Coordinate target) {
        // this work can be parallelized
        final Cell targetCell = target.toCell(Vector.STILL);
        final List<Cell> candidateNextCell = getCandidateNextCells(moving);
        return candidateNextCell
                .stream()
                .map(interestingCell -> {
                    final int dist = playgroundShortestPathNav.calcDist(interestingCell, targetCell);
                    if (dist == Integer.MAX_VALUE) {
                        return null;
                    }
                    return new MazeMove(interestingCell, dist);
                })
                .filter(Objects::nonNull)
                .toList();
    }


    private List<Cell> getCandidateNextCells(MovingSprite sprite) {
        List<Cell> candidateNextCells = getIntersectingMazeCells(sprite);
        if (candidateNextCells.size() == 1) {
            // ghost lies completely in a maze cell
            Cell cell = sprite.getTopLeftCorner().toCell(Vector.STILL);
            candidateNextCells = cell.getAdjCells(configs.PLAYGROUND_WIDTH(), configs.PLAYGROUND_HEIGHT());
        }
        return candidateNextCells
                .stream()
                .filter(cell -> !Playground.isWall(cell) && !Playground.isGhostHWall(cell))
                .toList();
    }


    private List<Cell> getIntersectingMazeCells(MovingSprite sprite) {
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
