package org.example.util;

import org.example.constant.DirectionsE;
import org.example.entity.Coordinate;
import org.example.entity.Rect;

import java.util.List;

public class RectUtils {

    public static List<Coordinate> get4Corners(Rect rect) {
        return List.of(
                rect.getTopLeftCorner(),
                getTopRightCorner(rect),
                getBottomRightCorner(rect),
                getBottomLeftCorner(rect)
        );
    }

    private static Coordinate getTopRightCorner(Rect rect) {
        return new Coordinate(rect.getTopLeftCorner().getRow(), rect.getTopLeftCorner().getCol() + rect.getWidth() -1);
    }

    private static Coordinate getBottomRightCorner(Rect rect) {
        return new Coordinate(rect.getTopLeftCorner().getRow() + rect.getHeight() - 1, rect.getTopLeftCorner().getCol() + rect.getWidth() - 1);
    }

    private static Coordinate getBottomLeftCorner(Rect rect) {
        return new Coordinate(rect.getTopLeftCorner().getRow() + rect.getHeight() - 1, rect.getTopLeftCorner().getCol());
    }

    public static Coordinate getTopLeftCornerOfRectContainingPoint(double rectWidth, double rectHeight, Coordinate point) {
        final double topLeftCornerRow = point.getRow() - point.getRow() % rectHeight;
        final double topLeftCornerCol = point.getCol() - point.getCol() % rectWidth;
        return new Coordinate(topLeftCornerRow, topLeftCornerCol);
    }

    public static boolean isRectContainsPoint(Rect rect, Coordinate point) {
        final Coordinate rectBottomRightCorner = getBottomRightCorner(rect);
        return point.getCol() >= rect.getTopLeftCorner().getCol() &&
                point.getCol() <= rectBottomRightCorner.getCol() &&
                point.getRow() >= rect.getTopLeftCorner().getRow() &&
                point.getRow() <= rectBottomRightCorner.getRow();
    }
    public static boolean isRectContainsRect(Rect parentRect, Rect childRect) {
        final Coordinate childRectBottomRightCorner = getBottomRightCorner(childRect);
        return isRectContainsPoint(parentRect, childRect.getTopLeftCorner()) && isRectContainsPoint(parentRect, childRectBottomRightCorner);
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
