package org.example.maze;

import org.example.constant.DimensionsC;
import org.example.constant.SpriteE;
import org.example.entity.CanvasCoordinate;
import org.example.entity.MazeCell;

public class MazeMatrix {

    private static SpriteE[][] maze;

    public static void init() {
        final MazeGenerator recursiveDivisionMazeGen = new RecursiveDivisionMazeGenerator();
        final boolean[][] booleanMaze = recursiveDivisionMazeGen.generateMaze(DimensionsC.MAZE_HEIGHT, DimensionsC.MAZE_WIDTH, DimensionsC.MAZE_CHAMBER_MIN_WIDTH, DimensionsC.MAZE_CHAMBER_MIN_HEIGHT);
        maze = new SpriteE[DimensionsC.MAZE_HEIGHT][DimensionsC.MAZE_WIDTH];
        for(int mazeRow = 0; mazeRow< DimensionsC.MAZE_HEIGHT; mazeRow++) {
            for(int mazeCol = 0; mazeCol< DimensionsC.MAZE_WIDTH; mazeCol++) {
                if (booleanMaze[mazeRow][mazeCol]) {
                    maze[mazeRow][mazeCol] = SpriteE.WALL;
                } else {
                    maze[mazeRow][mazeCol] = SpriteE.EMPTY;
                }
            }
        }
    }

    public static SpriteE get(MazeCell cell) {
        return get(cell.getRow(), cell.getCol());
    }

    public static void set(int row, int col, SpriteE sprite) {
        maze[row][col] = sprite;
    }

    public static SpriteE get(int row, int col) {
        return maze[row][col];
    }

    public static boolean isEmpty(int row, int col) {
        return maze[row][col] == SpriteE.EMPTY;
    }

    public static boolean hasSugar(MazeCell cell) {
        return hasSugar(cell.getRow(), cell.getCol());
    }
    public static boolean hasSugar(int row, int col) {
        return maze[row][col] == SpriteE.SUGAR;
    }

    public static boolean hasSuperSugar(MazeCell cell) {
        return hasSuperSugar(cell.getRow(), cell.getCol());
    }
    public static boolean hasSuperSugar(int row, int col) {
        return maze[row][col] == SpriteE.SUPER_SUGAR;
    }

    public static boolean isWall(MazeCell cell) {
        return isWall(cell.getRow(), cell.getCol());
    }
    public static boolean isWall(int row, int col) {
        return maze[row][col] == SpriteE.WALL;
    }

    public static int height() {
        return maze.length;
    }

    public static int width() {
        return maze[0].length;
    }

    public static CanvasCoordinate getEmptyMazePosition() {
        for (int mazeRow = 0; mazeRow< MazeMatrix.height(); mazeRow++) {
            for (int mazeCol = 0; mazeCol< MazeMatrix.width(); mazeCol++) {
                if (isEmpty(mazeRow, mazeCol)) {
                    set(mazeRow, mazeCol, SpriteE.PAC_MAN);
                    return new CanvasCoordinate(mazeRow, mazeCol);
                }
            }
        }
        return null;
    }
}
