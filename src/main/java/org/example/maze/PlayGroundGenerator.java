package org.example.maze;

import org.example.constant.DimensionsC;

public class PlayGroundGenerator {

    private final MazeGenerator mazeGenerator;

    public PlayGroundGenerator() {
        mazeGenerator = new RandomizedDFSMazeGenerator();
    }

    public boolean[][] generate() {
        final int width = (int) (DimensionsC.CANVAS_WIDTH_PIXELS / DimensionsC.MAZE_CELL_SIZE_PIXELS);
        final int height = (int) (DimensionsC.CANVAS_HEIGHT_PIXELS / DimensionsC.MAZE_CELL_SIZE_PIXELS);

        final boolean[][] maze = mazeGenerator.generateMaze(height, width, 1, 1);

        putGhostHouse(maze);

        return maze;
    }

    private void putGhostHouse(boolean[][] maze) {
        final int width = maze[0].length;
        final int height = maze.length;

        /**
         * ghost house will span an area of 4 * 7, where the empty area will be 2 * 5 surrounded by walls,
         * with a door at the middle of the first row.
         */
        int ghostHSCol = width / 2 - 3;
        int ghostHECol = width / 2 + 3;
        int ghostHSRow = height / 2 - 3;
        int ghostHERow = height / 2;

        // surround the house with empty cells

        // build horizontal walls of the ghost house
        for(int i=ghostHSCol; i<=ghostHECol; i++) {
            maze[ghostHERow][i] = true;
            maze[ghostHSRow][i] = true;

            maze[ghostHSRow - 1][i] = false;
            maze[ghostHERow + 1][i] = false;
        }

        // build vertical walls of the ghost house
        for(int i=ghostHSRow; i<=ghostHERow; i++) {
            maze[i][ghostHSCol] = true;
            maze[i][ghostHECol] = true;

            maze[i][ghostHSCol - 1] = false;
            maze[i][ghostHECol + 1] = false;
        }

        // make the house empty from the inside
        for(int i=ghostHSRow + 1; i< ghostHERow; i++) {
            for(int j=ghostHSCol + 1; j< ghostHECol; j++) {
                maze[i][j] = false;
            }
        }

        // open a door in the ghost house
        maze[ghostHSRow][width / 2] = false;
    }
}
