package org.example.util;

import org.example.constant.DimensionsC;
import org.example.constant.DirectionsE;
import org.example.constant.SpriteE;
import org.example.entity.CanvasCoordinate;
import org.example.entity.MazeCell;

public class SugarUtil {

    public static CanvasCoordinate getSugarTopLeftCornerCanvas(CanvasCoordinate cellTopLeftCornerCanvas) {
        final double sugarTopLeftCornerRow = (DimensionsC.MAZE_CELL_SIZE_PIXELS - DimensionsC.SUGAR_CELL_SIZE_PIXELS) / 2 + cellTopLeftCornerCanvas.getRow();
        final double sugarTopLeftCornerCol =  (DimensionsC.MAZE_CELL_SIZE_PIXELS - DimensionsC.SUGAR_CELL_SIZE_PIXELS) / 2 + cellTopLeftCornerCanvas.getCol();
        return new CanvasCoordinate(sugarTopLeftCornerRow, sugarTopLeftCornerCol);
    }


    public static CanvasCoordinate getSuperSugarTopLeftCornerCanvas(CanvasCoordinate cellTopLeftCornerCanvas) {
        final double sugarTopLeftCornerRow = (DimensionsC.MAZE_CELL_SIZE_PIXELS - DimensionsC.SUPER_SUGAR_DIAMETER_PIXELS) / 2 + cellTopLeftCornerCanvas.getRow();
        final double sugarTopLeftCornerCol =  (DimensionsC.MAZE_CELL_SIZE_PIXELS - DimensionsC.SUPER_SUGAR_DIAMETER_PIXELS) / 2 + cellTopLeftCornerCanvas.getCol();
        return new CanvasCoordinate(sugarTopLeftCornerRow, sugarTopLeftCornerCol);
    }

    public static boolean isCanvasCellHasSugar(SpriteE[][] maze, CanvasCoordinate cellTopLeftCornerCanvas) {
        final MazeCell mazeIndex = CanvasUtil.toMazeCoordinate(cellTopLeftCornerCanvas, DirectionsE.STILL);
        return maze[mazeIndex.getRow()][mazeIndex.getCol()] == SpriteE.SUGAR;
    }

    public static boolean isCanvasCellHasSuperSugar(SpriteE[][] maze, CanvasCoordinate cellTopLeftCornerCanvas) {
        final MazeCell mazeIndex = CanvasUtil.toMazeCoordinate(cellTopLeftCornerCanvas, DirectionsE.STILL);
        return maze[mazeIndex.getRow()][mazeIndex.getCol()] == SpriteE.SUPER_SUGAR;
    }
}
