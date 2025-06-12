package org.example.collision;

import org.example.constant.Dimensions;
import org.example.constant.MazeCellContentE;
import org.example.entity.Coordinate;
import org.example.util.MazeCanvasCoordinateMapping;
import org.example.util.RectUtils;
import org.example.util.sugar.SugarUtil;

import java.util.List;
import java.util.stream.Collectors;

public class PacManToSugarCollisionDetection {
    private MazeCellContentE[][] maze;

    public PacManToSugarCollisionDetection(MazeCellContentE[][] maze) {
        this.maze = maze;
    }

    public boolean isEatingSugar(Coordinate pacManCanvasTopLeftCorner) {

        final List<Coordinate> pacManRect4Corners = RectUtils.get4Corners(pacManCanvasTopLeftCorner, Dimensions.PAC_MAN_DIAMETER_PIXELS, Dimensions.PAC_MAN_DIAMETER_PIXELS);
        final List<Coordinate> canvasSugarToBeEaten = pacManRect4Corners.stream()
                .map(pacManCanvasRectCorner -> RectUtils.getTopLeftCornerOfRectContainingPoint(Dimensions.CANVAS_CELL_SIZE_PIXELS, Dimensions.CANVAS_CELL_SIZE_PIXELS, pacManCanvasRectCorner))
                .filter(rectTopLeftCornerCanvas -> SugarUtil.isCanvasCellHasSugar(maze, rectTopLeftCornerCanvas))
                .map(SugarUtil::getSugarTopLeftCornerCanvas)
                .filter(sugarCanvasRectTopLeftCorner -> RectUtils.isRectContainsRect(pacManCanvasTopLeftCorner, Dimensions.PAC_MAN_DIAMETER_PIXELS, Dimensions.PAC_MAN_DIAMETER_PIXELS,
                        sugarCanvasRectTopLeftCorner, Dimensions.SUGAR_CELL_SIZE_PIXELS, Dimensions.SUGAR_CELL_SIZE_PIXELS))
                .collect(Collectors.toUnmodifiableList());
        System.out.println("Canvas Sugar To Be Eaten " + canvasSugarToBeEaten);
        canvasSugarToBeEaten.stream()
                .map(MazeCanvasCoordinateMapping::canvasCordToMazeCordFloored)
                .forEach(mazeIndex -> {
                    maze[(int) mazeIndex.getRow()][(int) mazeIndex.getCol()] = MazeCellContentE.EMPTY;
                });
        return !canvasSugarToBeEaten.isEmpty();
    }
}
