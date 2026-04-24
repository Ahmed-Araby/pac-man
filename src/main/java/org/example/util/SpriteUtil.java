package org.example.util;

import org.example.constant.DimensionsC;
import org.example.constant.DirectionsE;
import org.example.constant.SpriteE;
import org.example.entity.CanvasCoordinate;
import org.example.entity.CanvasRect;
import org.example.entity.MazeCell;
import org.example.maze.MazeMatrix;
import org.example.util.canvas.CanvasUtil;

public class SpriteUtil {
    private SpriteUtil() {}


    public static CanvasRect toRect(CanvasCoordinate cTopLeftCorner, SpriteE type) {
        final CanvasCoordinate spriteTopLeftCorner = c2STopLeftCorner(cTopLeftCorner, type);
        return switch (type) {
            case SUGAR -> toSugarRect(spriteTopLeftCorner);
            case SUPER_SUGAR -> toSuperSugarRect(spriteTopLeftCorner);
            case PAC_MAN -> toPacManRect(spriteTopLeftCorner);
            case GHOST -> toGhostRect(spriteTopLeftCorner);
            default -> throw new IllegalArgumentException(type.toString());
        };
    }
    
    public static CanvasCoordinate c2STopLeftCorner(CanvasCoordinate cord, SpriteE type) {
        return switch (type) {
            case SUGAR -> c2SugarTopLeftCorner(cord);
            case SUPER_SUGAR -> c2SuperSugarTopLeftCorner(cord);
            case PAC_MAN -> c2PacmanTopLeftCorner(cord);
            case GHOST -> c2GhostTopLeftCorner(cord);
            default -> throw new IllegalArgumentException(type.toString());
        };
    }

    public static SpriteE getSpriteType(CanvasCoordinate cord) {
        final MazeCell cell = CanvasUtil.toMazeCoordinate(cord, DirectionsE.STILL);
        return MazeMatrix.get(cell);
    }


    private static CanvasRect toSugarRect(CanvasCoordinate topLeftCorner) {
        return new CanvasRect(topLeftCorner, DimensionsC.SUGAR_CELL_SIZE_PIXELS, DimensionsC.SUGAR_CELL_SIZE_PIXELS);
    }

    private static CanvasRect toSuperSugarRect(CanvasCoordinate topLeftCorner) {
        return new CanvasRect(topLeftCorner, DimensionsC.SUPER_SUGAR_DIAMETER_PIXELS, DimensionsC.SUPER_SUGAR_DIAMETER_PIXELS);
    }

    private static CanvasRect toPacManRect(CanvasCoordinate topLeftCorner) {
        return new CanvasRect(topLeftCorner, DimensionsC.PAC_MAN_DIAMETER_PIXELS, DimensionsC.PAC_MAN_DIAMETER_PIXELS);
    }

    private static CanvasRect toGhostRect(CanvasCoordinate topLeftCorner) {
        return new CanvasRect(topLeftCorner, DimensionsC.GHOST_WIDTH_PIXELS, DimensionsC.GHOST_HEIGHT_PIXELS);
    }


    private static CanvasCoordinate c2SugarTopLeftCorner(CanvasCoordinate cord) {
        final double offset = (DimensionsC.MAZE_CELL_SIZE_PIXELS - DimensionsC.SUGAR_CELL_SIZE_PIXELS) / 2;
        return new CanvasCoordinate(cord.getRow() + offset, cord.getCol() + offset);
    }

    private static CanvasCoordinate c2SuperSugarTopLeftCorner(CanvasCoordinate cord) {
        final double offset = (DimensionsC.MAZE_CELL_SIZE_PIXELS - DimensionsC.SUPER_SUGAR_DIAMETER_PIXELS) / 2;
        return new CanvasCoordinate(cord.getRow() + offset, cord.getCol() + offset);
    }

    private static CanvasCoordinate c2PacmanTopLeftCorner(CanvasCoordinate cord) {
        final double offset = (DimensionsC.MAZE_CELL_SIZE_PIXELS - DimensionsC.PAC_MAN_DIAMETER_PIXELS) / 2;
        return new CanvasCoordinate(cord.getRow() + offset, cord.getCol() + offset);
    }

    private static CanvasCoordinate c2GhostTopLeftCorner(CanvasCoordinate cord) {
        final double topOffset = (DimensionsC.MAZE_CELL_SIZE_PIXELS - DimensionsC.GHOST_HEIGHT_PIXELS) / 2;
        final double leftOffset = (DimensionsC.MAZE_CELL_SIZE_PIXELS - DimensionsC.GHOST_WIDTH_PIXELS) / 2;
        return new CanvasCoordinate(cord.getRow() + topOffset, cord.getCol() + leftOffset);
    }

}
