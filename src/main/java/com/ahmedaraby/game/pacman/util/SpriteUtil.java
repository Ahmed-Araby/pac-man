package com.ahmedaraby.game.pacman.util;

import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.game.pacman.entity.Coordinate;
import com.ahmedaraby.game.pacman.entity.Rectangle;
import com.ahmedaraby.game.pacman.entity.MazeCell;
import com.ahmedaraby.game.pacman.maze.Playground;
import com.ahmedaraby.game.pacman.util.canvas.CanvasUtil;

public class SpriteUtil {
    private SpriteUtil() {}


    public static Rectangle toRect(Coordinate cTopLeftCorner, SpriteE type) {
        final Coordinate spriteTopLeftCorner = c2STopLeftCorner(cTopLeftCorner, type);
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
    
    public static Coordinate c2STopLeftCorner(Coordinate cord, SpriteE type) {
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

    public static SpriteE getSpriteType(Coordinate cord) {
        final MazeCell cell = CanvasUtil.toMazeCoordinate(cord, DirectionsE.STILL);
        return Playground.get(cell);
    }


    private static Rectangle toSugarRect(Coordinate topLeftCorner) {
        return new Rectangle(topLeftCorner, DimensionsC.SUGAR_CELL_SIZE_PIXELS, DimensionsC.SUGAR_CELL_SIZE_PIXELS);
    }

    private static Rectangle toSuperSugarRect(Coordinate topLeftCorner) {
        return new Rectangle(topLeftCorner, DimensionsC.SUPER_SUGAR_DIAMETER_PIXELS, DimensionsC.SUPER_SUGAR_DIAMETER_PIXELS);
    }

    private static Rectangle toPacManRect(Coordinate topLeftCorner) {
        return new Rectangle(topLeftCorner, DimensionsC.PAC_MAN_DIAMETER_PIXELS, DimensionsC.PAC_MAN_DIAMETER_PIXELS);
    }

    private static Rectangle toGhostRect(Coordinate topLeftCorner) {
        return new Rectangle(topLeftCorner, DimensionsC.GHOST_WIDTH_PIXELS, DimensionsC.GHOST_HEIGHT_PIXELS);
    }

    private static Rectangle toWallRect(Coordinate topLeftCorner) {
        return new Rectangle(topLeftCorner, DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS);
    }

    private static Rectangle toGhostHWallRect(Coordinate topLeftCorner) {
        return new Rectangle(topLeftCorner, DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS);
    }



    private static Coordinate c2SugarTopLeftCorner(Coordinate cord) {
        final double offset = (DimensionsC.MAZE_CELL_SIZE_PIXELS - DimensionsC.SUGAR_CELL_SIZE_PIXELS) / 2;
        return new Coordinate(cord.getRow() + offset, cord.getCol() + offset);
    }

    private static Coordinate c2SuperSugarTopLeftCorner(Coordinate cord) {
        final double offset = (DimensionsC.MAZE_CELL_SIZE_PIXELS - DimensionsC.SUPER_SUGAR_DIAMETER_PIXELS) / 2;
        return new Coordinate(cord.getRow() + offset, cord.getCol() + offset);
    }

    private static Coordinate c2PacmanTopLeftCorner(Coordinate cord) {
        final double offset = (DimensionsC.MAZE_CELL_SIZE_PIXELS - DimensionsC.PAC_MAN_DIAMETER_PIXELS) / 2;
        return new Coordinate(cord.getRow() + offset, cord.getCol() + offset);
    }

    private static Coordinate c2GhostTopLeftCorner(Coordinate cord) {
        final double topOffset = (DimensionsC.MAZE_CELL_SIZE_PIXELS - DimensionsC.GHOST_HEIGHT_PIXELS) / 2;
        final double leftOffset = (DimensionsC.MAZE_CELL_SIZE_PIXELS - DimensionsC.GHOST_WIDTH_PIXELS) / 2;
        return new Coordinate(cord.getRow() + topOffset, cord.getCol() + leftOffset);
    }

    private static Coordinate c2WallTopLeftCorner(Coordinate cord) {
        return cord;
    }

    private static Coordinate c2GhostHWallTopLeftCorner(Coordinate cord) {
        return cord;
    }

}
