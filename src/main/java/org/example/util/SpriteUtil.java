package org.example.util;

import org.example.constant.DimensionsC;
import org.example.constant.SpriteE;
import org.example.entity.CanvasCoordinate;
import org.example.entity.CanvasRect;

public class SpriteUtil {
    private SpriteUtil() {}


    public CanvasRect toRect(CanvasCoordinate topLeftCord, SpriteE type) {
        return switch (type) {
            case SUGAR -> toSugarRect(topLeftCord);
            default -> throw new IllegalArgumentException(type.toString());
        };
    }

    private CanvasRect toSugarRect(CanvasCoordinate topLeftCord) {
        return new CanvasRect(topLeftCord, DimensionsC.SUGAR_CELL_SIZE_PIXELS, DimensionsC.SUGAR_CELL_SIZE_PIXELS);
    }


}
