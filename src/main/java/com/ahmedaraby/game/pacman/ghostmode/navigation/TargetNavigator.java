package com.ahmedaraby.game.pacman.ghostmode.navigation;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.entity.Cell;
import com.ahmedaraby.game.pacman.entity.MazeMove;
import com.ahmedaraby.game.pacman.entity.MovementPlan;
import com.ahmedaraby.game.pacman.sprite.MachineControlledSprite;
import com.ahmedaraby.game.pacman.sprite.Sprite;
import com.ahmedaraby.jengine.entity.Vector;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class TargetNavigator {

    private final ConfigsEx configs;
    // [TODO] replace this with the cell based shortest path calculator
    private final GhostNavigator navigator;

    public Vector calcDir(MachineControlledSprite source, Sprite target) {
        final List<MazeMove> possibleMoves = source.getPossibleMazeMoves();
        final Cell targetCell = target.calcCell();
        final Optional<MovementPlan> movementPlan = possibleMoves
                .stream()
                .map(move -> {
                    final double dist = navigator.calcDist(move.getTo(), targetCell);
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
