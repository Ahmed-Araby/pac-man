package com.ahmedaraby.game.pacman.ghostmode.navigation;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.entity.Cell;
import com.ahmedaraby.game.pacman.sprite.MovingSprite;
import com.ahmedaraby.game.pacman.sprite.Sprite;
import lombok.AllArgsConstructor;
import com.ahmedaraby.game.pacman.util.PlaygroundShortestPathNav;


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

    /**
     *
     * @param source
     * @param target
     * @return distance in terms of Cells
     */
    @Override
    public double calcDist(Cell source, Cell target) {
        return playgroundShortestPathNav.calcDist(source, target);
    }
}
