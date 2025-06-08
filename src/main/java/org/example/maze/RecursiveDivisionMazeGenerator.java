package org.example.maze;

import java.util.concurrent.ThreadLocalRandom;

public class RecursiveDivisionMazeGenerator implements MazeGenerator {

    @Override
    public boolean[][] generateMaze(int width, int height, int chamberMinWidth, int chamberMinHeight) {
        ThreadLocalRandom.current().setSeed(Thread.currentThread().threadId());

        final boolean[][] chamber = new boolean[width][height];
        generateMaze(chamber, 0, 0,
                width, height,
                chamberMinWidth, chamberMinHeight);
        return chamber;
    }

    private void generateMaze(boolean[][] chamber, int topLeftCornerRow, int topLeftCornerCol,
                                     int width, int height,
                                     int chamberMinWidth, int chamberMinHeight) {
        if (width <= chamberMinWidth || height <= chamberMinHeight) {
            return;
        }

        // build perpendicular walls
        int interPRow = getRandomRow(topLeftCornerRow, height);
        putWallsInRow(chamber, topLeftCornerCol, width, interPRow);

        int interPCol = getRandomCol(topLeftCornerCol, width);
        putWallsInCol(chamber, topLeftCornerRow, height, interPCol);

        // randomly open passages
        openWallAboveTheIntersectionPoint(chamber, topLeftCornerRow, interPRow, interPCol);
        openWallToTheRightOfIntersectionPoint(chamber, interPRow, interPCol, width);
        openWallBellowTheIntersectionPoint(chamber, interPRow, interPCol, height);
        openWallToTheLeftOfIntersectionPoint(chamber, topLeftCornerCol, interPRow, interPCol);
        // work on the smaller chambers
    }

    private void putWallsInRow(boolean[][] chamber, int topLeftCornerCol, int width, int row) {
        for (int colIndex = topLeftCornerCol; colIndex < topLeftCornerCol + width; colIndex++) {
            chamber[row][colIndex] = true;
        }
    }

    private void putWallsInCol(boolean[][] chamber, int topLeftCornerRow, int height, int col) {
        for (int rowIndex = topLeftCornerRow; rowIndex < topLeftCornerRow + height; rowIndex++) {
            chamber[rowIndex][col] = true;
        }
    }

    private int getRandomRow(int topLeftCornerRow, int chamberHeight) {
        int minRow = topLeftCornerRow + 1;
        int maxRow = topLeftCornerRow + chamberHeight - 2;
        return random.nextInt(maxRow - minRow + 1) + minRow;
    }

    private int getRandomCol(int topLeftCornerCol, int chamberWidth) {
        int minCol = topLeftCornerCol + 1;
        int maxCol = topLeftCornerCol + chamberWidth - 2;
        return random.nextInt(maxCol - minCol + 1) + minCol;
    }

    // can we open the wall while building the perpendicular walls !?
    private void openWallToTheRightOfIntersectionPoint(boolean[][] chamber, int interPRow, int interPCol, int chamberWidth) {
        final int nextToInterPCol = interPCol + 1; // exclude the intersection point col from the possible positions of an open wall
        final int randCol = random.nextInt(chamberWidth - nextToInterPCol) + nextToInterPCol;
        chamber[interPRow][randCol] = false;
    }

    private void openWallToTheLeftOfIntersectionPoint(boolean[][] chamber, int topLeftCornerCol, int interPRow, int interPCol) {
        final int randCol = random.nextInt(interPCol - topLeftCornerCol) + topLeftCornerCol;
        chamber[interPRow][randCol] = false;
    }

    private void openWallAboveTheIntersectionPoint(boolean[][] chamber, int topLeftCornerRow, int interPRow, int interPCol) {
        final int randRow = random.nextInt(interPRow - topLeftCornerRow) + topLeftCornerRow;
        chamber[randRow][interPCol] = false;
    }
    private void openWallBellowTheIntersectionPoint(boolean[][] chamber, int interPRow, int interPCol, int chamberHeight) {
        int nextToInterPRow = interPRow + 1; // exclude the intersection point row from the possible positions of an open wall
        final int randRow = random.nextInt(chamberHeight - nextToInterPRow) + nextToInterPRow;
        chamber[randRow][interPCol] = false;
    }
}
