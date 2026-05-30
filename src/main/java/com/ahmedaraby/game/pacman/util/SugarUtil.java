package com.ahmedaraby.game.pacman.util;

import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.game.pacman.entity.MazeCell;
import com.ahmedaraby.game.pacman.util.canvas.CanvasUtil;
import com.ahmedaraby.game.pacman.maze.Playground;

public class SugarUtil {

    public static Coordinate getSugarTopLeftCornerCanvas(Coordinate cellTopLeftCornerCanvas) {
        final double sugarTopLeftCornerRow = (DimensionsC.MAZE_CELL_SIZE_PIXELS - DimensionsC.SUGAR_CELL_SIZE_PIXELS) / 2 + cellTopLeftCornerCanvas.getRow();
        final double sugarTopLeftCornerCol =  (DimensionsC.MAZE_CELL_SIZE_PIXELS - DimensionsC.SUGAR_CELL_SIZE_PIXELS) / 2 + cellTopLeftCornerCanvas.getCol();
        return new Coordinate(sugarTopLeftCornerRow, sugarTopLeftCornerCol);
    }


    public static Coordinate getSuperSugarTopLeftCornerCanvas(Coordinate cellTopLeftCornerCanvas) {
        final double sugarTopLeftCornerRow = (DimensionsC.MAZE_CELL_SIZE_PIXELS - DimensionsC.SUPER_SUGAR_DIAMETER_PIXELS) / 2 + cellTopLeftCornerCanvas.getRow();
        final double sugarTopLeftCornerCol =  (DimensionsC.MAZE_CELL_SIZE_PIXELS - DimensionsC.SUPER_SUGAR_DIAMETER_PIXELS) / 2 + cellTopLeftCornerCanvas.getCol();
        return new Coordinate(sugarTopLeftCornerRow, sugarTopLeftCornerCol);
    }

    public static boolean isCanvasCellHasSugar(Coordinate cellTopLeftCornerCanvas) {
        final MazeCell cell = CanvasUtil.toMazeCoordinate(cellTopLeftCornerCanvas, DirectionsE.STILL);
        return Playground.hasSugar(cell);
    }

    public static boolean isCanvasCellHasSuperSugar(Coordinate cellTopLeftCornerCanvas) {
        final MazeCell cell = CanvasUtil.toMazeCoordinate(cellTopLeftCornerCanvas, DirectionsE.STILL);
        return Playground.hasSuperSugar(cell);
    }
}
