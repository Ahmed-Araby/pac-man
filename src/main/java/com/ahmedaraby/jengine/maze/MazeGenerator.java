package com.ahmedaraby.jengine.maze;

import com.ahmedaraby.game.pacman.util.EnrichedThreadLocalRandom;

public interface MazeGenerator {
    EnrichedThreadLocalRandom enrichedRandom = new EnrichedThreadLocalRandom();
    boolean[][] gen(int height, int width);
}
