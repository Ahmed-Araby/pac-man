package org.example.util;

import org.example.constant.DirectionsE;
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

    public static Coordinate getTopLeftCornerOfNextRect(Coordinate currRectTopLeftCorner, double canvasCellWidth, double canvasCellHeight, DirectionsE movementDirection) {
        double nextCanvasCellCol;
        double nextCanvasCellRow;

        switch (movementDirection) {
            case RIGHT:
                nextCanvasCellCol = getTopLeftCornerColOfNextRect(currRectTopLeftCorner.getCol(), canvasCellWidth);
                return new Coordinate(currRectTopLeftCorner.getRow(), nextCanvasCellCol);
            case UP:
                nextCanvasCellRow = getTopLeftCornerRowOfPrevRect(currRectTopLeftCorner.getRow(), canvasCellHeight);
                return new Coordinate(nextCanvasCellRow, currRectTopLeftCorner.getCol());
            case DOWN:
                nextCanvasCellRow = getTopLeftCornerRowOfNextRect(currRectTopLeftCorner.getRow(), canvasCellHeight);
                return new Coordinate(nextCanvasCellRow, currRectTopLeftCorner.getCol());
            case LEFT:
                nextCanvasCellCol = getTopLeftCornerColOfPrevRect(currRectTopLeftCorner.getCol(), canvasCellWidth);
                return new Coordinate(currRectTopLeftCorner.getRow(), nextCanvasCellCol);
            case STILL:
                return new Coordinate(currRectTopLeftCorner.getRow(), currRectTopLeftCorner.getCol());
            default:
                throw new IllegalStateException();
        }
    }

    public static double getTopLeftCornerColOfNextRect(double colWithinCurrRect, double cellWidth) {
        return colWithinCurrRect + cellWidth - colWithinCurrRect % cellWidth;
    }

    public static double getTopLeftCornerColOfPrevRect(double colWithinCurrRect, double cellWidth) {
        double topLeftCornerColOfPrevCell = colWithinCurrRect - cellWidth - colWithinCurrRect % cellWidth;
        return Math.max(0, topLeftCornerColOfPrevCell);
    }

    public static double getTopLeftCornerRowOfNextRect(double rowWithinCurrRect, double cellHeight) {
        return rowWithinCurrRect + cellHeight - rowWithinCurrRect % cellHeight;
    }

    public static double getTopLeftCornerRowOfPrevRect(double rowWithinCurrRect, double cellHeight) {
        double topLeftCornerRoeOfPrevCell = rowWithinCurrRect - cellHeight - rowWithinCurrRect % cellHeight;
        return Math.max(0, topLeftCornerRoeOfPrevCell);
    }
}
