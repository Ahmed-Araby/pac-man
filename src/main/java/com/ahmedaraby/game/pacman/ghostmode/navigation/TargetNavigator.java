package com.ahmedaraby.game.pacman.ghostmode.navigation;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.model.Cell;
import com.ahmedaraby.game.pacman.model.MazeMove;
import com.ahmedaraby.game.pacman.model.MovementPlan;
import com.ahmedaraby.game.pacman.sprite.MachineControlledSprite;
import com.ahmedaraby.game.pacman.sprite.Sprite;
import com.ahmedaraby.game.pacman.util.PlaygroundShortestPathNav;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.entity.Vector;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class TargetNavigator {

    private final ConfigsEx configs;
    private final PlaygroundShortestPathNav playgroundShortestPathNav = new PlaygroundShortestPathNav();

    public Vector calcDir(MachineControlledSprite source, Sprite target) {
        return calcDir(source, target.calcCell());
    }

    public Vector calcDir(MachineControlledSprite source, Coordinate target) {
        return calcDir(source, target.toCell());
    }

    private Vector calcDir(MachineControlledSprite source, Cell target) {
        final List<MazeMove> possibleMoves = source.getPossibleMazeMoves();
        final Optional<MovementPlan> movementPlan = possibleMoves
                .stream()
                .map(move -> {
                    final double dist = playgroundShortestPathNav.calcDist(move.getTo(), target);
                    return new MovementPlan(move.getDir(), dist);
                })
                .filter(plan -> plan.getDist2Target() < Integer.MAX_VALUE)
                .sorted()
                .findFirst();

        if (movementPlan.isEmpty()) {
            return Vector.STILL;
        }
        return movementPlan.get().getDir();
    }
}
