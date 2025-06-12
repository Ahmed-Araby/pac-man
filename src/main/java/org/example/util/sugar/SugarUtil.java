package org.example.util.sugar;

import org.example.constant.Dimensions;
import org.example.constant.MazeCellContentE;
import org.example.entity.Coordinate;
import org.example.util.MazeCanvasCoordinateMapping;

public class SugarUtil {

    public static Coordinate getSugarTopLeftCornerCanvas(Coordinate cellTopLeftCornerCanvas) {
        final double sugarTopLeftCornerRow = (Dimensions.CANVAS_CELL_SIZE_PIXELS - Dimensions.SUGAR_CELL_SIZE_PIXELS) / 2 + cellTopLeftCornerCanvas.getRow();
        final double sugarTopLeftCornerCol =  (Dimensions.CANVAS_CELL_SIZE_PIXELS - Dimensions.SUGAR_CELL_SIZE_PIXELS) / 2 + cellTopLeftCornerCanvas.getCol();
        return new Coordinate(sugarTopLeftCornerRow, sugarTopLeftCornerCol);
    }


    public static Coordinate getSuperSugarTopLeftCornerCanvas(Coordinate cellTopLeftCornerCanvas) {
        final double sugarTopLeftCornerRow = (Dimensions.CANVAS_CELL_SIZE_PIXELS - Dimensions.SUPER_SUGAR_DIAMETER_PIXELS) / 2 + cellTopLeftCornerCanvas.getRow();
        final double sugarTopLeftCornerCol =  (Dimensions.CANVAS_CELL_SIZE_PIXELS - Dimensions.SUPER_SUGAR_DIAMETER_PIXELS) / 2 + cellTopLeftCornerCanvas.getCol();
        return new Coordinate(sugarTopLeftCornerRow, sugarTopLeftCornerCol);
    }

    public static boolean isCanvasCellHasSugar(MazeCellContentE[][] maze, Coordinate cellTopLeftCornerCanvas) {
        final Coordinate mazeIndex = MazeCanvasCoordinateMapping.canvasCordToMazeCordFloored(cellTopLeftCornerCanvas);
        return maze[(int) mazeIndex.getRow()][(int) mazeIndex.getCol()] == MazeCellContentE.SUGAR;
    }
}
