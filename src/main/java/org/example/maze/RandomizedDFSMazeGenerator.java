package org.example.maze;

import org.example.entity.MazeCell;
import org.example.entity.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class RandomizedDFSMazeGenerator implements MazeGenerator{

    @Override
    public boolean[][] generateMaze(boolean[][] mask, boolean[][] visited) {
        final int width = mask[0].length;
        final int height = mask.length;
        final boolean[][] maze = new boolean[height][width];
        for(int i=0; i<height; i++) {
            for (int j=0; j<width; j++) {
                maze[i][j] = mask[i][j];
            }
        }

        return buildMaze(maze, visited);
    }

    @Override
    public boolean[][] generateMaze(int height, int width, int chamberMinWidth, int chamberMinHeight) {
        final boolean[][] maze = new boolean[height][width];
        final boolean[][] visited = new boolean[height][width];
        return buildMaze(maze, visited);
    }

    private boolean[][] buildMaze(boolean[][] maze, boolean[][] visited) {
        final int width = maze[0].length;
        final int height = maze.length;

        // start with walls at odd rows and columns
        for(int i=0; i<height; i++) {
            for(int j=1; j<width; j+=2) {
                if(visited[i][j]) {
                    continue;
                }
                maze[i][j] = true; // wall
                visited[i][j] = false;
            }
        }

        final Stack<MazeCell> cells = new Stack<>();
        visited[0][0] = true;
        cells.push(new MazeCell(0, 0));

        while(!cells.isEmpty()) {
            final MazeCell currCell = cells.pop();
            List<Vector> eligibleDirections = getEligibleDirections(currCell, visited);
            if(eligibleDirections.isEmpty()) {
                // dead end
                continue;
            } else if(eligibleDirections.size() > 1) {
                cells.push(currCell);
            }

            int index = enrichedRandom.nextIntStartInclEndExcl(0, eligibleDirections.size());
            Vector dir = eligibleDirections.get(index);


            MazeCell nextEmptyCell = new MazeCell(currCell.getRow() + (int)dir.getY() * 2, currCell.getCol() + (int)dir.getX() * 2);
            MazeCell nextWall = new MazeCell(currCell.getRow() + (int)dir.getY() , currCell.getCol() + (int)dir.getX());

            // open the wall
            maze[nextWall.getRow()][nextWall.getCol()] = false;
            visited[nextWall.getRow()][nextWall.getCol()] = true;

            visited[nextEmptyCell.getRow()][nextEmptyCell.getCol()] = true;
            cells.push(nextEmptyCell);
        }
        return maze;
    }


    private List<Vector> getEligibleDirections(MazeCell currCell, boolean[][] visited) {
        final int width = visited[0].length;
        final int height = visited.length;

        final List<Vector> eligibleDirections = new ArrayList<>();
        final int[] dCol ={0, 0, 1, -1};
        final int[] dRow = {-1, 1, 0, 0};
        for (int i=0; i<4; i++) {
            int nCol = currCell.getCol() + dCol[i] * 2;
            int nRow = currCell.getRow() + dRow[i] * 2;
            if(!(nCol >= 0 && nCol < width && nRow >= 0 && nRow < height)) {
                continue;
            }
            if(!visited[nRow][nCol]) {
                eligibleDirections.add(new Vector(dCol[i], dRow[i]));
            }
        }

        return eligibleDirections;
    }
}
