package org.example.maze;

import org.example.util.EnrichedThreadLocalRandom;

public interface MazeGenerator {
    EnrichedThreadLocalRandom  enrichedRandom = new EnrichedThreadLocalRandom();
    boolean[][] generateMaze(int height, int width, int chamberMinWidth, int chamberMinHeight);
}
