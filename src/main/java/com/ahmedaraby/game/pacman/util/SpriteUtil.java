package com.ahmedaraby.game.pacman.util;

import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.game.pacman.entity.CanvasCoordinate;
import com.ahmedaraby.game.pacman.entity.Rectangle;
import com.ahmedaraby.game.pacman.entity.MazeCell;
import com.ahmedaraby.game.pacman.maze.Playground;
import com.ahmedaraby.game.pacman.util.canvas.CanvasUtil;

public class SpriteUtil {
    private SpriteUtil() {}


    public static Rectangle toRect(CanvasCoordinate cTopLeftCorner, SpriteE type) {
        final CanvasCoordinate spriteTopLeftCorner = c2STopLeftCorner(cTopLeftCorner, type);
        return switch (type) {
            case SUGAR -> toSugarRect(spriteTopLeftCorner);
            case SUPER_SUGAR -> toSuperSugarRect(spriteTopLeftCorner);
            case PAC_MAN -> toPacManRect(spriteTopLeftCorner);
            case GHOST -> toGhostRect(spriteTopLeftCorner);
            case WALL -> toWallRect(spriteTopLeftCorner);
            case GHOST_HOUSE_WALL -> toGhostHWallRect(spriteTopLeftCorner);
            default -> throw new IllegalArgumentException(type.toString());
        };
    }
    
    public static CanvasCoordinate c2STopLeftCorner(CanvasCoordinate cord, SpriteE type) {
        return switch (type) {
            case SUGAR -> c2SugarTopLeftCorner(cord);
            case SUPER_SUGAR -> c2SuperSugarTopLeftCorner(cord);
            case PAC_MAN -> c2PacmanTopLeftCorner(cord);
            case GHOST -> c2GhostTopLeftCorner(cord);
            case WALL -> c2WallTopLeftCorner(cord);
            case GHOST_HOUSE_WALL -> c2GhostHWallTopLeftCorner(cord);
            default -> throw new IllegalArgumentException(type.toString());
        };
    }

    public static SpriteE getSpriteType(CanvasCoordinate cord) {
        final MazeCell cell = CanvasUtil.toMazeCoordinate(cord, DirectionsE.STILL);
        return Playground.get(cell);
    }


    private static Rectangle toSugarRect(CanvasCoordinate topLeftCorner) {
        return new Rectangle(topLeftCorner, DimensionsC.SUGAR_CELL_SIZE_PIXELS, DimensionsC.SUGAR_CELL_SIZE_PIXELS);
    }

    private static Rectangle toSuperSugarRect(CanvasCoordinate topLeftCorner) {
        return new Rectangle(topLeftCorner, DimensionsC.SUPER_SUGAR_DIAMETER_PIXELS, DimensionsC.SUPER_SUGAR_DIAMETER_PIXELS);
    }

    private static Rectangle toPacManRect(CanvasCoordinate topLeftCorner) {
        return new Rectangle(topLeftCorner, DimensionsC.PAC_MAN_DIAMETER_PIXELS, DimensionsC.PAC_MAN_DIAMETER_PIXELS);
    }

    private static Rectangle toGhostRect(CanvasCoordinate topLeftCorner) {
        return new Rectangle(topLeftCorner, DimensionsC.GHOST_WIDTH_PIXELS, DimensionsC.GHOST_HEIGHT_PIXELS);
    }

    private static Rectangle toWallRect(CanvasCoordinate topLeftCorner) {
        return new Rectangle(topLeftCorner, DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS);
    }

    private static Rectangle toGhostHWallRect(CanvasCoordinate topLeftCorner) {
        return new Rectangle(topLeftCorner, DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS);
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

    private static CanvasCoordinate c2WallTopLeftCorner(CanvasCoordinate cord) {
        return cord;
    }

    private static CanvasCoordinate c2GhostHWallTopLeftCorner(CanvasCoordinate cord) {
        return cord;
    }

}
