package org.example.maze;

public class RecursiveDivisionMazeGenerator implements MazeGenerator {

    @Override
    public boolean[][] generateMaze(int height, int width, int chamberMinWidth, int chamberMinHeight) {
        final boolean[][] chamber = new boolean[height][width];
        generateMaze(chamber, 0, 0,
                height - 1, width - 1,
                chamberMinWidth, chamberMinHeight);
        return chamber;
    }

    private void generateMaze(boolean[][] chamber, int topLeftCornerRow, int topLeftCornerCol,
                              int bottomRightCornerRow, int bottomRightCornerCol,
                              int chamberMinWidth, int chamberMinHeight) {
        if (bottomRightCornerCol - topLeftCornerCol + 1 <= chamberMinWidth || bottomRightCornerRow - topLeftCornerRow + 1 <= chamberMinHeight) {
            return;
        }

        // build perpendicular walls
        int interPRow = getRandomRowForPlacingWalls(topLeftCornerRow, bottomRightCornerRow);
        putWallsInRow(chamber, topLeftCornerCol, bottomRightCornerCol, interPRow);

        int interPCol = getRandomColForPlacingWalls(topLeftCornerCol, bottomRightCornerCol);
        putWallsInCol(chamber, topLeftCornerRow, bottomRightCornerRow, interPCol);

        // randomly open passages
        openWallAboveTheIntersectionPoint(chamber, topLeftCornerRow, interPRow, interPCol);
        openWallToTheRightOfIntersectionPoint(chamber, interPRow, interPCol, bottomRightCornerCol);
        openWallBellowTheIntersectionPoint(chamber, interPRow, interPCol, bottomRightCornerRow);
        openWallToTheLeftOfIntersectionPoint(chamber, topLeftCornerCol, interPRow, interPCol);

        // recurse and work on the smaller chambers
        recurseOnTopRightChamber(chamber, bottomRightCornerCol, topLeftCornerRow, interPRow, interPCol, chamberMinWidth, chamberMinHeight);
        recurseOnBottomRightChamber(chamber, bottomRightCornerRow, bottomRightCornerCol, interPRow, interPCol, chamberMinWidth, chamberMinHeight);
        recurseOnTopLeftChamber(chamber, topLeftCornerRow, topLeftCornerCol, interPRow, interPCol, chamberMinWidth, chamberMinHeight);
        recurseOnBottomLeftChamber(chamber, bottomRightCornerRow, topLeftCornerCol, interPRow, interPCol, chamberMinWidth, chamberMinHeight);
    }

    // PUT WALLS
    private void putWallsInRow(boolean[][] chamber, int topLeftCornerCol, int bottomRightCornerCol, int row) {
        for (int colIndex = topLeftCornerCol; colIndex <= bottomRightCornerCol; colIndex++) {
            chamber[row][colIndex] = true;
        }
    }

    private void putWallsInCol(boolean[][] chamber, int topLeftCornerRow, int bottomRightCornerRow, int col) {
        for (int rowIndex = topLeftCornerRow; rowIndex <= bottomRightCornerRow; rowIndex++) {
            chamber[rowIndex][col] = true;
        }
    }

    private int getRandomRowForPlacingWalls(int topLeftCornerRow, int bottomRightCornerRow) {
        // to get 4 sub chambers, we must not place walls on the edges of the current chamber
        return enrichedRandom.nextIntStartExclEndExcl(topLeftCornerRow, bottomRightCornerRow);
    }

    private int getRandomColForPlacingWalls(int topLeftCornerCol, int bottomRightCornerCol) {
        // to get 4 sub chambers, we must not place walls on the edges of the current chamber
        return enrichedRandom.nextIntStartExclEndExcl(topLeftCornerCol, bottomRightCornerCol);
    }

    // OPEN PASSAGES
    // can we open the wall while building the perpendicular walls !?
    private void openWallToTheRightOfIntersectionPoint(boolean[][] chamber, int interPRow, int interPCol, int bottomRightCornerCol) {
        // exclude the intersection point col from the possible positions of an open wall, because pac man can't move diagonally
        final int randCol = enrichedRandom.nextIntStartExclEndIncl(interPCol, bottomRightCornerCol);
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
    private void openWallBellowTheIntersectionPoint(boolean[][] chamber, int interPRow, int interPCol, int bottomRightCornerRow) {
        // exclude the intersection point row from the possible positions of an open wall, because pac man can't move diagonally
        final int randRow = enrichedRandom.nextIntStartExclEndIncl(interPRow, bottomRightCornerRow);
        chamber[randRow][interPCol] = false;
    }

    // RECURSE ON THE NEW SUB CHAMBERS.
    private void recurseOnTopRightChamber(boolean[][] chamber, int bottomRightCornerCol,
                                         int topLeftCornerRow,
                                         int interPRow, int interPCol,
                                         int chamberMinWidth, int chamberMinHeight) {
        final int newTopLeftCornerCol = interPCol + 1;
        final int newBottomRightCornerRow = interPRow - 1;
        generateMaze(chamber, topLeftCornerRow, newTopLeftCornerCol, newBottomRightCornerRow, bottomRightCornerCol, chamberMinWidth, chamberMinHeight);
    }

    private void recurseOnBottomRightChamber(boolean[][] chamber, int bottomRightCornerRow, int bottomRightCornerCol,
                                             int interPRow, int interPCol,
                                             int chamberMinWidth, int chamberMinHeight) {
        final int newTopLeftCornerRow = interPRow + 1;
        final int newTopLeftCornerCol = interPCol + 1;
        generateMaze(chamber, newTopLeftCornerRow, newTopLeftCornerCol, bottomRightCornerRow, bottomRightCornerCol, chamberMinWidth, chamberMinHeight);

    }

    private void recurseOnTopLeftChamber(boolean[][] chamber, int topLeftCornerRow, int topLeftCornerCol,
                                        int interPRow, int interPCol,
                                        int chamberMinWidth, int chamberMinHeight) {
        final int newBottomRightCornerCol = interPCol - 1;
        final int newBottomRightCornerRow = interPRow - 1;
        generateMaze(chamber, topLeftCornerRow, topLeftCornerCol, newBottomRightCornerRow, newBottomRightCornerCol, chamberMinWidth, chamberMinHeight);
    }


    private void recurseOnBottomLeftChamber(boolean[][] chamber, int bottomRightCornerRow,
                                            int topLeftCornerCol,
                                            int interPRow, int interPCol,
                                            int chamberMinWidth, int chamberMinHeight) {
        final int newTopLeftCornerRow = interPRow + 1;
        final int newBottomRightCornerCol = interPCol - 1;
        generateMaze(chamber, newTopLeftCornerRow, topLeftCornerCol, bottomRightCornerRow, newBottomRightCornerCol, chamberMinWidth, chamberMinHeight);

    }
}
