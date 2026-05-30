package com.ahmedaraby.game.pacman.ghostmode.navigation;

import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.entity.CanvasCoordinate;

public interface GhostNavigator {

    DirectionsE nextMoveDirection(CanvasCoordinate ghostCord, CanvasCoordinate pacCord);
}
