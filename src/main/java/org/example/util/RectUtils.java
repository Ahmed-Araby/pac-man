package org.example.util;

import org.example.entity.Coordinate;
import java.util.List;

public class RectUtils {

    public static List<Coordinate> get4Corners(Coordinate rectTopLeftCorner, double rectWidth, double rectHeight) {
        return List.of(
                rectTopLeftCorner,
                getTopRightCorner(rectTopLeftCorner, rectWidth),
                getBottomRightCorner(rectTopLeftCorner, rectWidth, rectHeight),
                getBottomLeftCorner(rectTopLeftCorner, rectHeight)
        );
    }

    private static Coordinate getTopRightCorner(Coordinate rectTopLeftCorner, double rectWidth) {
        return new Coordinate(rectTopLeftCorner.getRow(), rectTopLeftCorner.getCol() + rectWidth -1);
    }

    private static Coordinate getBottomRightCorner(Coordinate rectTopLeftCorner, double rectWidth, double rectHeight) {
        return new Coordinate(rectTopLeftCorner.getRow() + rectHeight -1, rectTopLeftCorner.getCol() + rectWidth -1);
    }

    private static Coordinate getBottomLeftCorner(Coordinate rectTopLeftCorner, double rectHeight) {
        return new Coordinate(rectTopLeftCorner.getRow() + rectHeight - 1, rectTopLeftCorner.getCol());
    }

    public static Coordinate getTopLeftCornerOfRectContainingPoint(double rectWidth, double rectHeight, Coordinate point) {
        final double topLeftCornerRow = point.getRow() - point.getRow() % rectHeight;
        final double topLeftCornerCol = point.getCol() - point.getCol() % rectWidth;
        return new Coordinate(topLeftCornerRow, topLeftCornerCol);
    }
}
