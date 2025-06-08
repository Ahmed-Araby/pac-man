package org.example.maze;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

class RecursiveDivisionMazeGeneratorTest {

    @AllArgsConstructor
    private class Pair {
        @Getter
        private int x, y;
    }

    private Pair getEmptyMazeCell(boolean[][] maze) {
        for(int i=0; i<maze.length; i++) {
            for(int j=0; j<maze[0].length; j++) {
                if (!maze[i][j]) {
                    return new Pair(i, j);
                }
            }
        }
        throw new IllegalStateException();
    }

    private void bfsMazeTraversal(boolean[][] maze, boolean[][] visited, int row, int col) throws InterruptedException {
        // up, down, right, left
        int dRow[] = {-1, 1, 0, 0};
        int dCol[] = {0, 0, 1, -1};

        final Queue<Pair> queue = new ConcurrentLinkedQueue<>();
        queue.add(new Pair(row, col));
        visited[row][col] = true;

        while (!queue.isEmpty()) {
            final Pair cellPos = queue.poll();
            if ( cellPos == null) {
                break;
            }
            int currRow = cellPos.x;
            int currCol = cellPos.y;

            for(int i=0; i<4; i++) {
                int newRow = currRow + dRow[i];
                int newCol = currCol + dCol[i];
                if(newRow >= 0 && newRow < maze.length && newCol >=0 && newCol < maze[0].length) {
                    if (!maze[newRow][newCol] && !visited[newRow][newCol]) { // empty cell
                        visited[newRow][newCol] = true;
                        queue.add(new Pair(newRow, newCol));
                    }
                }
            }
        }
    }

    private boolean isMazeAConnectedGraph(boolean[][] maze, boolean[][] visited) {
        for(int i=0; i<maze.length; i++) {
            for(int j=0; j<maze[0].length; j++) {
                if (!maze[i][j] && !visited[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @RepeatedTest(100)
    void generateMaze_visualTesting() {
        // given
        final int height = 50, width = 50;
        final RecursiveDivisionMazeGenerator mazeGenerator = new RecursiveDivisionMazeGenerator();

        // when
        final boolean[][] maze = mazeGenerator.generateMaze(height, width, 2, 2);

        // then
        for (int i=0; i<height; i++) {
            for (int j=0; j<width; j++) {
                System.out.print(maze[i][j] ? 1 : 0);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    @ParameterizedTest
    @CsvSource({"10, 10", "10, 15", "50, 25","100, 100", "100, 100", "100, 100", "100, 100", "50, 50", "200, 200", "1000, 1000", "10000, 10000"})
    void generateMaze_MazeShouldBeAConnectedGraph(int mazeHeight, int mazeWidth) throws InterruptedException {
        final RecursiveDivisionMazeGenerator mazeGenerator = new RecursiveDivisionMazeGenerator();
        final boolean[][] maze = mazeGenerator.generateMaze(mazeHeight, mazeWidth, 2, 2);

        final Pair cellPos = getEmptyMazeCell(maze);
        boolean[][] visited = new boolean[mazeHeight][mazeWidth];
        bfsMazeTraversal(maze, visited, cellPos.getX(), cellPos.getY());
        final boolean isConnectedGraph = isMazeAConnectedGraph(maze, visited);

        Assertions.assertTrue(isConnectedGraph, "Generated Maze Should be a connected Graph");
    }

    @RepeatedTest(10)
    void generateMaze_MazeShouldBeAConnectedGraph() throws InterruptedException {
        int mazeHeight = 10;
        int mazeWidth = 15;
        final RecursiveDivisionMazeGenerator mazeGenerator = new RecursiveDivisionMazeGenerator();
        final boolean[][] maze = mazeGenerator.generateMaze(mazeHeight, mazeWidth, 2, 2);

        final Pair cellPos = getEmptyMazeCell(maze);
        boolean[][] visited = new boolean[mazeHeight][mazeWidth];
        bfsMazeTraversal(maze, visited, cellPos.getX(), cellPos.getY());
        final boolean isConnectedGraph = isMazeAConnectedGraph(maze, visited);

        for (int i=0; i<mazeHeight; i++) {
            for (int j=0; j<mazeWidth; j++) {
                System.out.print(maze[i][j] ? 1 : 0);
                System.out.print(" ");
            }
            System.out.println();
        }

        Assertions.assertTrue(isConnectedGraph, "Generated Maze Should be a connected Graph");
    }
}