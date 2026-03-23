package org.example.ghostmode;

import org.example.constant.DirectionsE;
import org.example.constant.SpriteE;
import org.example.entity.CanvasCoordinate;
import org.example.entity.MazeCell;

public interface GhostMode {

    DirectionsE nextMoveDirection(CanvasCoordinate ghostCord, CanvasCoordinate pacCord, SpriteE[][] maze);
}
