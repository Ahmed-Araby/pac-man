package org.example.util;

import org.example.constant.DirectionsE;
import org.example.entity.CanvasCoordinate;
import org.example.entity.CanvasRect;

import java.util.List;

public class CanvasRectUtils {

    public static List<CanvasCoordinate> get4Corners(CanvasRect canvasRect) {
        return List.of(
                canvasRect.getTopLeftCorner(),
                getTopRightCorner(canvasRect),
                getBottomRightCorner(canvasRect),
                getBottomLeftCorner(canvasRect)
        );
    }

    private static CanvasCoordinate getTopRightCorner(CanvasRect canvasRect) {
        return new CanvasCoordinate(canvasRect.getTopLeftCorner().getRow(), canvasRect.getTopLeftCorner().getCol() + canvasRect.getWidth() -1);
    }

    private static CanvasCoordinate getBottomRightCorner(CanvasRect canvasRect) {
        return new CanvasCoordinate(canvasRect.getTopLeftCorner().getRow() + canvasRect.getHeight() - 1, canvasRect.getTopLeftCorner().getCol() + canvasRect.getWidth() - 1);
    }

    private static CanvasCoordinate getBottomLeftCorner(CanvasRect canvasRect) {
        return new CanvasCoordinate(canvasRect.getTopLeftCorner().getRow() + canvasRect.getHeight() - 1, canvasRect.getTopLeftCorner().getCol());
    }

    public static CanvasCoordinate getTopLeftCornerOfRectContainingPoint(double rectWidth, double rectHeight, CanvasCoordinate point) {
        return getRectContainingPoint(rectWidth, rectHeight, point).getTopLeftCorner();
    }
    public static CanvasRect getRectContainingPoint(double rectWidth, double rectHeight, CanvasCoordinate point) {
        final double topLeftCornerRow = point.getRow() - point.getRow() % rectHeight;
        final double topLeftCornerCol = point.getCol() - point.getCol() % rectWidth;
        return new CanvasRect(new CanvasCoordinate(topLeftCornerRow, topLeftCornerCol), rectWidth, rectHeight);
    }

    public static boolean isRectContainsRect(CanvasRect parentCanvasRect, CanvasRect childCanvasRect) {
        final CanvasCoordinate childRectBottomRightCorner = getBottomRightCorner(childCanvasRect);
        return isRectContainsPoint(parentCanvasRect, childCanvasRect.getTopLeftCorner()) && isRectContainsPoint(parentCanvasRect, childRectBottomRightCorner);
    }
    public static boolean isRectContainsPoint(CanvasRect canvasRect, CanvasCoordinate point) {
        final CanvasCoordinate rectBottomRightCorner = getBottomRightCorner(canvasRect);
        return point.getCol() >= canvasRect.getTopLeftCorner().getCol() &&
                point.getCol() <= rectBottomRightCorner.getCol() &&
                point.getRow() >= canvasRect.getTopLeftCorner().getRow() &&
                point.getRow() <= rectBottomRightCorner.getRow();
    }


    public static CanvasCoordinate getTopLeftCornerOfNextRect(CanvasRect canvasRect, DirectionsE movementDirection) {
        return getNextRect(canvasRect, movementDirection).getTopLeftCorner();
    }
    public static CanvasRect getNextRect(CanvasRect canvasRect, DirectionsE movementDirection) {
        double nextCanvasCellCol;
        double nextCanvasCellRow;

        switch (movementDirection) {
            case RIGHT:
                nextCanvasCellCol = getTopLeftCornerColOfNextRect(canvasRect);
                return new CanvasRect(new CanvasCoordinate(canvasRect.getTopLeftCorner().getRow(), nextCanvasCellCol), canvasRect.getWidth(), canvasRect.getHeight());
            case UP:
                nextCanvasCellRow = getTopLeftCornerRowOfPrevRect(canvasRect);
                return new CanvasRect(new CanvasCoordinate(nextCanvasCellRow, canvasRect.getTopLeftCorner().getCol()), canvasRect.getWidth(), canvasRect.getHeight());
            case DOWN:
                nextCanvasCellRow = getTopLeftCornerRowOfNextRect(canvasRect);
                return new CanvasRect(new CanvasCoordinate(nextCanvasCellRow, canvasRect.getTopLeftCorner().getCol()), canvasRect.getWidth(), canvasRect.getHeight());
            case LEFT:
                nextCanvasCellCol = getTopLeftCornerColOfPrevRect(canvasRect);
                return new CanvasRect(new CanvasCoordinate(canvasRect.getTopLeftCorner().getRow(), nextCanvasCellCol), canvasRect.getWidth(), canvasRect.getHeight());
            case STILL:
                return new CanvasRect(new CanvasCoordinate(canvasRect.getTopLeftCorner().getRow(), canvasRect.getTopLeftCorner().getCol()), canvasRect.getWidth(), canvasRect.getHeight());
            default:
                throw new IllegalStateException();
        }
    }

    public static double getTopLeftCornerColOfNextRect(CanvasRect canvasRect) {
        return canvasRect.getTopLeftCorner().getCol() + canvasRect.getWidth() - canvasRect.getTopLeftCorner().getCol() % canvasRect.getWidth();
    }

    public static double getTopLeftCornerColOfPrevRect(CanvasRect canvasRect) {
        double topLeftCornerColOfPrevCell = canvasRect.getTopLeftCorner().getCol() - canvasRect.getWidth() - canvasRect.getTopLeftCorner().getCol() % canvasRect.getWidth();
        return Math.max(0, topLeftCornerColOfPrevCell);
    }

    public static double getTopLeftCornerRowOfNextRect(CanvasRect canvasRect) {
        return canvasRect.getTopLeftCorner().getRow() + canvasRect.getHeight() - canvasRect.getTopLeftCorner().getRow() % canvasRect.getHeight();
    }

    public static double getTopLeftCornerRowOfPrevRect(CanvasRect canvasRect) {
        double topLeftCornerRoeOfPrevCell = canvasRect.getTopLeftCorner().getRow() - canvasRect.getHeight() - canvasRect.getTopLeftCorner().getRow() % canvasRect.getHeight();
        return Math.max(0, topLeftCornerRoeOfPrevCell);
    }
}
