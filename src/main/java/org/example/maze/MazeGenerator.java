package org.example.maze;

import org.example.util.EnrichedThreadLocalRandom;

public interface MazeGenerator {
    EnrichedThreadLocalRandom  enrichedRandom = new EnrichedThreadLocalRandom();
    boolean[][] generateMaze(int width, int height, int chamberMinWidth, int chamberMinHeight);
}
