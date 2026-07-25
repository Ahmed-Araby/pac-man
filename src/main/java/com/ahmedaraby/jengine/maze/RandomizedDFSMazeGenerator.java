package com.ahmedaraby.jengine.maze;

import com.ahmedaraby.game.pacman.model.Cell;
import com.ahmedaraby.jengine.entity.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class RandomizedDFSMazeGenerator implements MazeGenerator{

    @Override
    public boolean[][] gen(int height, int width) {
        final boolean[][] maze = new boolean[height][width];
        final boolean[][] visited = new boolean[height][width];

        // start with walls at odd columns
        for(int i=0; i<height; i++) {
            for(int j=1; j<width; j+=2) {
                if(visited[i][j]) {
                    continue;
                }
                maze[i][j] = true; // wall
                visited[i][j] = false;
            }
        }

        final Stack<Cell> cells = new Stack<>();
        visited[0][0] = true;
        cells.push(new Cell(0, 0));

        while(!cells.isEmpty()) {
            final Cell currCell = cells.pop();
            List<Vector> eligibleDirections = getEligibleDirections(currCell, visited);
            if(eligibleDirections.isEmpty()) {
                // dead end
                continue;
            } else if(eligibleDirections.size() > 1) {
                cells.push(currCell);
            }

            int index = enrichedRandom.nextIntStartInclEndExcl(0, eligibleDirections.size());
            Vector dir = eligibleDirections.get(index);


            Cell nextEmptyCell = new Cell(currCell.getRow() + (int)dir.getY() * 2, currCell.getCol() + (int)dir.getX() * 2);
            Cell nextWall = new Cell(currCell.getRow() + (int)dir.getY() , currCell.getCol() + (int)dir.getX());

            // open the wall
            maze[nextWall.getRow()][nextWall.getCol()] = false;
            visited[nextWall.getRow()][nextWall.getCol()] = true;

            visited[nextEmptyCell.getRow()][nextEmptyCell.getCol()] = true;
            cells.push(nextEmptyCell);
        }
        return maze;
    }


    private List<Vector> getEligibleDirections(Cell currCell, boolean[][] visited) {
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
