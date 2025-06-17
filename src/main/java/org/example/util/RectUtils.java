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

    public static Coordinate getTopLeftCornerOfNextRect(Rect rect, DirectionsE movementDirection) {

        double nextCanvasCellCol;
        double nextCanvasCellRow;

        switch (movementDirection) {
            case RIGHT:
                nextCanvasCellCol = getTopLeftCornerColOfNextRect(rect);
                return new Coordinate(rect.getTopLeftCorner().getRow(), nextCanvasCellCol);
            case UP:
                nextCanvasCellRow = getTopLeftCornerRowOfPrevRect(rect);
                return new Coordinate(nextCanvasCellRow, rect.getTopLeftCorner().getCol());
            case DOWN:
                nextCanvasCellRow = getTopLeftCornerRowOfNextRect(rect);
                return new Coordinate(nextCanvasCellRow, rect.getTopLeftCorner().getCol());
            case LEFT:
                nextCanvasCellCol = getTopLeftCornerColOfPrevRect(rect);
                return new Coordinate(rect.getTopLeftCorner().getRow(), nextCanvasCellCol);
            case STILL:
                return new Coordinate(rect.getTopLeftCorner().getRow(), rect.getTopLeftCorner().getCol());
            default:
                throw new IllegalStateException();
        }
    }

    public static double getTopLeftCornerColOfNextRect(Rect rect) {
        return rect.getTopLeftCorner().getCol() + rect.getWidth() - rect.getTopLeftCorner().getCol() % rect.getWidth();
    }

    public static double getTopLeftCornerColOfPrevRect(Rect rect) {
        double topLeftCornerColOfPrevCell = rect.getTopLeftCorner().getCol() - rect.getWidth() - rect.getTopLeftCorner().getCol() % rect.getWidth();
        return Math.max(0, topLeftCornerColOfPrevCell);
    }

    public static double getTopLeftCornerRowOfNextRect(Rect rect) {
        return rect.getTopLeftCorner().getRow() + rect.getHeight() - rect.getTopLeftCorner().getRow() % rect.getHeight();
    }

    public static double getTopLeftCornerRowOfPrevRect(Rect rect) {
        double topLeftCornerRoeOfPrevCell = rect.getTopLeftCorner().getRow() - rect.getHeight() - rect.getTopLeftCorner().getRow() % rect.getHeight();
        return Math.max(0, topLeftCornerRoeOfPrevCell);
    }
}
