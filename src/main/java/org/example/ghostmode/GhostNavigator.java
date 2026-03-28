package org.example.ghostmode;

import org.example.constant.DirectionsE;
import org.example.entity.CanvasCoordinate;

public interface GhostNavigator {

    DirectionsE nextMoveDirection(CanvasCoordinate ghostCord, CanvasCoordinate pacCord);
}
