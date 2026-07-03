package com.ahmedaraby.game.pacman.ghostmode.navigation;

import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.sprite.MovingSprite;
import com.ahmedaraby.jengine.entity.Coordinate;

public interface GhostNavigator {

    DirectionsE calcDir(MovingSprite sprite, Coordinate target);
    double calcDist(MovingSprite sprite, Coordinate target);
}
