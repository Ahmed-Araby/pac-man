package org.example.ghostmode;

import org.example.constant.SpriteE;
import org.example.entity.MazeCoordinate;

public interface GhostMode {

    MazeCoordinate nextPosition(MazeCoordinate ghostCord, MazeCoordinate targetCord, SpriteE[][] maze);
}
