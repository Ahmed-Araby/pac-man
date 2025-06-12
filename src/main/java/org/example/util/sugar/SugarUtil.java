package org.example.util.sugar;

import org.example.constant.Dimensions;
import org.example.entity.Coordinate;

public class SugarUtil {

    public static Coordinate getSugarTopLeftCornerCanvas(Coordinate cellTopLeftCornerCanvas) {
        final double sugarTopLeftCornerRow = (Dimensions.CANVAS_CELL_SIZE_PIXELS - Dimensions.SUGAR_DIAMETER_PIXELS) / 2 + cellTopLeftCornerCanvas.getRow();
        final double sugarTopLeftCornerCol =  (Dimensions.CANVAS_CELL_SIZE_PIXELS - Dimensions.SUGAR_DIAMETER_PIXELS) / 2 + cellTopLeftCornerCanvas.getCol();
        return new Coordinate(sugarTopLeftCornerRow, sugarTopLeftCornerCol);
    }
}
