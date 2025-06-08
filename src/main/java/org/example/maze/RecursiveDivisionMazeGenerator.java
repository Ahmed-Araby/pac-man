package org.example.maze;

public class RecursiveDivisionMazeGenerator implements MazeGenerator {

    @Override
    public boolean[][] generateMaze(int width, int height, int chamberMinWidth, int chamberMinHeight) {
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
        int interPRow = getRandomRowForPlacingWalls(topLeftCornerRow, height);
        putWallsInRow(chamber, topLeftCornerCol, width, interPRow);

        int interPCol = getRandomColForPlacingWalls(topLeftCornerCol, width);
        putWallsInCol(chamber, topLeftCornerRow, height, interPCol);

        // randomly open passages
        openWallAboveTheIntersectionPoint(chamber, topLeftCornerRow, interPRow, interPCol);
        openWallToTheRightOfIntersectionPoint(chamber, interPRow, interPCol, width);
        openWallBellowTheIntersectionPoint(chamber, interPRow, interPCol, height);
        openWallToTheLeftOfIntersectionPoint(chamber, topLeftCornerCol, interPRow, interPCol);

        // recurse and work on the smaller chambers
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

    private int getRandomRowForPlacingWalls(int topLeftCornerRow, int chamberHeight) {
        int minRow = topLeftCornerRow + 1;
        int maxRow = topLeftCornerRow + chamberHeight - 2;
        return enrichedRandom.nextIntStartInclEndIncl(minRow, maxRow);
    }

    private int getRandomColForPlacingWalls(int topLeftCornerCol, int chamberWidth) {
        int minCol = topLeftCornerCol + 1;
        int maxCol = topLeftCornerCol + chamberWidth - 2;
        return enrichedRandom.nextIntStartExclEndExcl(minCol, maxCol);
    }

    // OPEN WALS
    // can we open the wall while building the perpendicular walls !?
    private void openWallToTheRightOfIntersectionPoint(boolean[][] chamber, int interPRow, int interPCol, int chamberWidth) {
        // exclude the intersection point col from the possible positions of an open wall
        final int randCol = enrichedRandom.nextIntStartExclEndExcl(interPCol, chamberWidth);
        chamber[interPRow][randCol] = false;
    }

    private void openWallToTheLeftOfIntersectionPoint(boolean[][] chamber, int topLeftCornerCol, int interPRow, int interPCol) {
        final int randCol = enrichedRandom.nextIntStartInclEndExcl(topLeftCornerCol, interPCol);
        chamber[interPRow][randCol] = false;
    }

    private void openWallAboveTheIntersectionPoint(boolean[][] chamber, int topLeftCornerRow, int interPRow, int interPCol) {
        final int randRow = enrichedRandom.nextIntStartInclEndExcl(topLeftCornerRow, interPRow);
        chamber[randRow][interPCol] = false;
    }
    private void openWallBellowTheIntersectionPoint(boolean[][] chamber, int interPRow, int interPCol, int chamberHeight) {
        // exclude the intersection point row from the possible positions of an open wall
        final int randRow = enrichedRandom. nextIntStartExclEndExcl(interPRow, chamberHeight);
        chamber[randRow][interPCol] = false;
    }
}
