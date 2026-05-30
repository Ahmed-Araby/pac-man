package com.ahmedaraby.game.pacman.ghostmode.navigation;

import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.entity.Coordinate;

public interface GhostNavigator {

    DirectionsE nextMoveDirection(Coordinate ghostCord, Coordinate pacCord);
}
