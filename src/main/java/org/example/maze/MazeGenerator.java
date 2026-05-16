package org.example.maze;

import org.example.util.EnrichedThreadLocalRandom;

public interface MazeGenerator {
    EnrichedThreadLocalRandom  enrichedRandom = new EnrichedThreadLocalRandom();
    boolean[][] gen(int height, int width);
}
