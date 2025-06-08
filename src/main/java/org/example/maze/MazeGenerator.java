package org.example.maze;

import java.util.Random;

public interface MazeGenerator {
    Random random = new Random();
    boolean[][] generateMaze(int width, int height, int chamberMinWidth, int chamberMinHeight);
}
