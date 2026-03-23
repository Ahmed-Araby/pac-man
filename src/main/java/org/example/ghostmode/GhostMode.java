package org.example.ghostmode;

import org.example.constant.DirectionsE;
import org.example.constant.SpriteE;
import org.example.entity.Coordinate;
import org.example.entity.MazeCoordinate;

public interface GhostMode {

    DirectionsE nextMoveDirection(Coordinate ghostCord, MazeCoordinate pacCord, SpriteE[][] maze);
}
