package com.ahmedaraby.game.pacman.ghostmode.navigation;

import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.jengine.entity.Coordinate;

public interface GhostNavigator {

    DirectionsE nextMoveDirection(Coordinate source, Coordinate target);
    double calcDist(Coordinate source, Coordinate target);
}
