package org.example.maze;


import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

class RecursiveDivisionMazeGeneratorTest {

    @RepeatedTest(100)
    void generateMaze() {
        // given
        final int height = 50, width = 50;
        final RecursiveDivisionMazeGenerator mazeGenerator = new RecursiveDivisionMazeGenerator();

        // when
        final boolean[][] maze = mazeGenerator.generateMaze(width, height, 2, 2);

        // then
        for (int i=0; i<height; i++) {
            for (int j=0; j<width; j++) {
                System.out.print(maze[i][j] ? 1 : 0);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}