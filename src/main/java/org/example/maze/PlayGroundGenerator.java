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

        final boolean[][] ghostHouseMask = getGhostHouseMask(width, height);

        final boolean[][] visited = new boolean[height][width];
        markGhostHouseCellsAsVisited(visited);

        return mazeGenerator.generateMaze(ghostHouseMask, visited);
    }

    private boolean[][] getGhostHouseMask(int width, int height) {
        boolean[][] mask = new boolean[height][width];

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
            mask[ghostHERow][i] = true;
            mask[ghostHSRow][i] = true;

            mask[ghostHSRow - 1][i] = false;
            mask[ghostHERow + 1][i] = false;
        }

        // build vertical walls of the ghost house
        for(int i=ghostHSRow; i<=ghostHERow; i++) {
            mask[i][ghostHSCol] = true;
            mask[i][ghostHECol] = true;

            mask[i][ghostHSCol - 1] = false;
            mask[i][ghostHECol + 1] = false;
        }

        // make the house empty from the inside
        for(int i=ghostHSRow + 1; i< ghostHERow; i++) {
            for(int j=ghostHSCol + 1; j< ghostHECol; j++) {
                mask[i][j] = false;
            }
        }

        // open a door in the ghost house
        mask[ghostHSRow][width / 2] = false;

        return mask;
    }
    private void markGhostHouseCellsAsVisited(boolean[][] visited) {
        final int width = visited[0].length;
        final int height = visited.length;

        int ghostHSCol = width / 2 - 3;
        int ghostHECol = width / 2 + 3;
        int ghostHSRow = height / 2 - 3;
        int ghostHERow = height / 2;

        for(int i=ghostHSRow - 1; i<=ghostHERow + 1; i++) {
            for(int j=ghostHSCol -1; j<=ghostHECol +1; j++) {
                visited[i][j] = true;
            }
        }
    }
}
