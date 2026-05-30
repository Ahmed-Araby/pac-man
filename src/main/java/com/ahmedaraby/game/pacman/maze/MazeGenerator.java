package com.ahmedaraby.game.pacman.maze;

import com.ahmedaraby.game.pacman.util.EnrichedThreadLocalRandom;

public interface MazeGenerator {
    EnrichedThreadLocalRandom enrichedRandom = new EnrichedThreadLocalRandom();
    boolean[][] gen(int height, int width);
}
