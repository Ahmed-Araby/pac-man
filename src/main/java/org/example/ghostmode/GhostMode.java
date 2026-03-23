package org.example.ghostmode;

import org.example.constant.DirectionsE;
import org.example.constant.SpriteE;
import org.example.entity.CanvasCoordinate;
import org.example.entity.MazeCoordinate;

public interface GhostMode {

    DirectionsE nextMoveDirection(CanvasCoordinate ghostCord, MazeCoordinate pacCord, SpriteE[][] maze);
}
