package org.example.maze;

import org.example.constant.DimensionsC;
import org.example.model.GhostHouse;

public class PlayGroundGenerator {

    private final MazeGenerator mazeGenerator;

    public PlayGroundGenerator() {
        mazeGenerator = new RandomizedDFSMazeGenerator();
    }

    public boolean[][] generate() {
        final int width = (int) (DimensionsC.CANVAS_WIDTH_PIXELS / DimensionsC.MAZE_CELL_SIZE_PIXELS);
        final int height = (int) (DimensionsC.CANVAS_HEIGHT_PIXELS / DimensionsC.MAZE_CELL_SIZE_PIXELS);

        final boolean[][] maze = mazeGenerator.gen(height, width);

        final GhostHouse ghostHouse = getGhostHouse(width,  height);
        putGhostHouse(ghostHouse, maze);

        return maze;
    }

    private GhostHouse getGhostHouse(int width, int height) {
        /**
         * ghost house will span an area of 4 * 7, where the empty area will be 2 * 5 surrounded by walls,
         * with a door at the middle of the first row.
         */
        int ghostHSCol = width / 2 - 3;
        int ghostHECol = width / 2 + 3;
        int ghostHSRow = height / 2 - 3;
        int ghostHERow = height / 2;
        return new GhostHouse(ghostHSCol, ghostHECol, ghostHSRow, ghostHERow);
    }

    private void putGhostHouse(GhostHouse ghostHouse, boolean[][] maze) {
        for (int i = ghostHouse.getSRow(); i <= ghostHouse.getERow(); i++) {
            for (int j = ghostHouse.getSRow(); j <= ghostHouse.getECol(); j++) {
                if (i == ghostHouse.getSRow() || i == ghostHouse.getERow()) {
                    maze[i][j] = true;
                } else if (j == ghostHouse.getSCol() || j == ghostHouse.getECol()) {
                    maze[i][j] = true;
                } else {
                    maze[i][j] = false;
                }
            }
        }
        final int ghostHDoorRow = ghostHouse.calcDoorRow();
        final int ghostHDoorCol = ghostHouse.calcDoorCol();
        maze[ghostHDoorRow][ghostHDoorCol] = false;

    }
}
