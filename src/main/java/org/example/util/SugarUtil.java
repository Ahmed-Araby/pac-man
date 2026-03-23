package org.example.util;

import org.example.constant.DimensionsC;
import org.example.constant.DirectionsE;
import org.example.entity.CanvasCoordinate;
import org.example.entity.MazeCell;
import org.example.maze.MazeMatrix;

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

    public static boolean isCanvasCellHasSugar(CanvasCoordinate cellTopLeftCornerCanvas) {
        final MazeCell cell = CanvasUtil.toMazeCoordinate(cellTopLeftCornerCanvas, DirectionsE.STILL);
        return MazeMatrix.hasSugar(cell);
    }

    public static boolean isCanvasCellHasSuperSugar(CanvasCoordinate cellTopLeftCornerCanvas) {
        final MazeCell cell = CanvasUtil.toMazeCoordinate(cellTopLeftCornerCanvas, DirectionsE.STILL);
        return MazeMatrix.hasSuperSugar(cell);
    }
}
