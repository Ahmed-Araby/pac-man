package com.ahmedaraby.game.pacman.ghostmode.navigation;

import com.ahmedaraby.game.pacman.collision.M2SSpriteCollisionDetector;
import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.game.pacman.entity.Cell;
import com.ahmedaraby.game.pacman.entity.MovementPlan;
import com.ahmedaraby.game.pacman.playground.Playground;
import com.ahmedaraby.game.pacman.sprite.MovingSprite;
import com.ahmedaraby.game.pacman.sprite.Sprite;
import com.ahmedaraby.jengine.entity.Rectangle;
import com.ahmedaraby.jengine.entity.Vector;
import lombok.AllArgsConstructor;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.game.pacman.entity.MazeMove;
import com.ahmedaraby.game.pacman.util.PlaygroundShortestPathNav;

import java.util.List;
import java.util.Objects;


@AllArgsConstructor
public class ShortestPathNavigator implements GhostNavigator {

    private final ConfigsEx configs;
    private final PlaygroundShortestPathNav playgroundShortestPathNav;

    @Override
    public double calcDist(MovingSprite movingSprite, Sprite targetSprite) {
        Cell sourceCell = movingSprite.calcCell();
        Cell targetCell = targetSprite.calcCell();
        return playgroundShortestPathNav.calcDist(sourceCell, targetCell) * configs.PLAYGROUND_CELL_SIZE();
    }

    @Override
    public Vector calcDir(MovingSprite sprite, Coordinate target) {
        final Coordinate source = sprite.getTopLeftCorner();
        if(source.equals(target)) {
            return Vector.STILL;
        }

        final List<MovementPlan> possibleMoves = getCandidateMoves(sprite, target);
        return possibleMoves
                .stream()
                .sorted()
                .filter(plan -> plan.getDist2Target() < Integer.MAX_VALUE)
                .map(MovementPlan::getDir)
                .findFirst()
                .orElse(Vector.STILL);
    }


    private List<MovementPlan> getCandidateMoves(MovingSprite moving, Coordinate target) {
        // this work can be parallelized
        final Cell targetCell = target.toCell();
        final List<MazeMove> candidateNextCell = getCandidateNextMoves(moving);
        return candidateNextCell
                .stream()
                // make sure that this move won't cause a collision
                .filter(move -> {
                    final Coordinate nextCord = moving.calcNextCord(moving.calcHEmptySpaceInPlaygroundCell(), move.getDir());
                    final Rectangle nextRect = new Rectangle(nextCord, moving.getWidth(), moving.getHeight());
                    return M2SSpriteCollisionDetector.detect(nextRect, List.of(SpriteE.WALL, SpriteE.GHOST_HOUSE_WALL)).isEmpty();
                })
                .map(move -> {
                    final int dist = playgroundShortestPathNav.calcDist(move.getTo(), targetCell);
                    if (dist == Integer.MAX_VALUE) {
                        return null;
                    }
                    return new MovementPlan(move.getDir(), dist);
                })
                .filter(Objects::nonNull)
                .toList();
    }


    private List<MazeMove> getCandidateNextMoves(MovingSprite sprite) {
        Cell spriteCell = sprite.getTopLeftCorner().toCell();
        List<MazeMove> candidateNextCells = spriteCell.getMoves(configs.PLAYGROUND_WIDTH(), configs.PLAYGROUND_HEIGHT());

        return candidateNextCells
                .stream()
                .filter(move -> !move.getDir().isOpposite(sprite.getDirV()))
                .filter(move -> !Playground.isWall(move.getTo()) && !Playground.isGhostHWall(move.getTo()))
                .toList();
    }
}
