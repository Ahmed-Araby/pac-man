package org.example.util.sugar;

import org.example.constant.Dimensions;
import org.example.constant.DirectionsE;
import org.example.constant.SpriteE;
import org.example.entity.CanvasCoordinate;
import org.example.entity.MazeCoordinate;
import org.example.util.CoordinateUtil;

public class SugarUtil {

    public static CanvasCoordinate getSugarTopLeftCornerCanvas(CanvasCoordinate cellTopLeftCornerCanvas) {
        final double sugarTopLeftCornerRow = (Dimensions.MAZE_CELL_SIZE_PIXELS - Dimensions.SUGAR_CELL_SIZE_PIXELS) / 2 + cellTopLeftCornerCanvas.getRow();
        final double sugarTopLeftCornerCol =  (Dimensions.MAZE_CELL_SIZE_PIXELS - Dimensions.SUGAR_CELL_SIZE_PIXELS) / 2 + cellTopLeftCornerCanvas.getCol();
        return new CanvasCoordinate(sugarTopLeftCornerRow, sugarTopLeftCornerCol);
    }


    public static CanvasCoordinate getSuperSugarTopLeftCornerCanvas(CanvasCoordinate cellTopLeftCornerCanvas) {
        final double sugarTopLeftCornerRow = (Dimensions.MAZE_CELL_SIZE_PIXELS - Dimensions.SUPER_SUGAR_DIAMETER_PIXELS) / 2 + cellTopLeftCornerCanvas.getRow();
        final double sugarTopLeftCornerCol =  (Dimensions.MAZE_CELL_SIZE_PIXELS - Dimensions.SUPER_SUGAR_DIAMETER_PIXELS) / 2 + cellTopLeftCornerCanvas.getCol();
        return new CanvasCoordinate(sugarTopLeftCornerRow, sugarTopLeftCornerCol);
    }

    public static boolean isCanvasCellHasSugar(SpriteE[][] maze, CanvasCoordinate cellTopLeftCornerCanvas) {
        final MazeCoordinate mazeIndex = CoordinateUtil.toMazeCoordinate(cellTopLeftCornerCanvas, DirectionsE.STILL);
        return maze[mazeIndex.getRow()][mazeIndex.getCol()] == SpriteE.SUGAR;
    }

    public static boolean isCanvasCellHasSuperSugar(SpriteE[][] maze, CanvasCoordinate cellTopLeftCornerCanvas) {
        final MazeCoordinate mazeIndex = CoordinateUtil.toMazeCoordinate(cellTopLeftCornerCanvas, DirectionsE.STILL);
        return maze[mazeIndex.getRow()][mazeIndex.getCol()] == SpriteE.SUPER_SUGAR;
    }
}
