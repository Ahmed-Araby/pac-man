package com.ahmedaraby.game.pacman.ghostmode.navigation;

import com.ahmedaraby.game.pacman.entity.Cell;
import com.ahmedaraby.game.pacman.sprite.MovingSprite;
import com.ahmedaraby.game.pacman.sprite.Sprite;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.entity.Vector;

public interface GhostNavigator {

    Vector calcDir(MovingSprite sprite, Coordinate target);
    double calcDist(MovingSprite sprite, Sprite targetSprite);
    double calcDist(MovingSprite sprite, Coordinate target);
    double calcDist(Cell source, Cell target);
}
