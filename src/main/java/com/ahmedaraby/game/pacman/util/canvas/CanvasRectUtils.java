package com.ahmedaraby.game.pacman.util.canvas;

import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.entity.Rectangle;

import java.util.List;

public class CanvasRectUtils {

    public static List<Coordinate> get4Corners(Rectangle rectangle) {
        return List.of(
                rectangle.getTopLeftCorner(),
                getTopRightCorner(rectangle),
                getBottomRightCorner(rectangle),
                getBottomLeftCorner(rectangle)
        );
    }

    private static Coordinate getTopRightCorner(Rectangle rectangle) {
        return new Coordinate(rectangle.getTopLeftCorner().getRow(), rectangle.getTopLeftCorner().getCol() + rectangle.getWidth() -1);
    }

    private static Coordinate getBottomRightCorner(Rectangle rectangle) {
        return new Coordinate(rectangle.getTopLeftCorner().getRow() + rectangle.getHeight() - 1, rectangle.getTopLeftCorner().getCol() + rectangle.getWidth() - 1);
    }

    private static Coordinate getBottomLeftCorner(Rectangle rectangle) {
        return new Coordinate(rectangle.getTopLeftCorner().getRow() + rectangle.getHeight() - 1, rectangle.getTopLeftCorner().getCol());
    }

    public static Coordinate getTopLeftCornerOfRectContainingPoint(double rectWidth, double rectHeight, Coordinate point) {
        return getRectContainingPoint(rectWidth, rectHeight, point).getTopLeftCorner();
    }
    public static Rectangle getRectContainingPoint(double rectWidth, double rectHeight, Coordinate point) {
        final double topLeftCornerRow = point.getRow() - point.getRow() % rectHeight;
        final double topLeftCornerCol = point.getCol() - point.getCol() % rectWidth;
        return new Rectangle(new Coordinate(topLeftCornerRow, topLeftCornerCol), rectWidth, rectHeight);
    }

    public static boolean isRectContainsRect(Rectangle parentRectangle, Rectangle childRectangle) {
        final Coordinate childRectBottomRightCorner = getBottomRightCorner(childRectangle);
        return isRectContainsPoint(parentRectangle, childRectangle.getTopLeftCorner()) && isRectContainsPoint(parentRectangle, childRectBottomRightCorner);
    }
    public static boolean isRectContainsPoint(Rectangle rectangle, Coordinate point) {
        final Coordinate rectBottomRightCorner = getBottomRightCorner(rectangle);
        return point.getCol() >= rectangle.getTopLeftCorner().getCol() &&
                point.getCol() <= rectBottomRightCorner.getCol() &&
                point.getRow() >= rectangle.getTopLeftCorner().getRow() &&
                point.getRow() <= rectBottomRightCorner.getRow();
    }


    public static Coordinate getTopLeftCornerOfNextRect(Rectangle rectangle, DirectionsE movementDirection) {
        return getNextRect(rectangle, movementDirection).getTopLeftCorner();
    }
    public static Rectangle getNextRect(Rectangle rectangle, DirectionsE movementDirection) {
        double nextCanvasCellCol;
        double nextCanvasCellRow;

        switch (movementDirection) {
            case RIGHT:
                nextCanvasCellCol = getTopLeftCornerColOfNextRect(rectangle);
                return new Rectangle(new Coordinate(rectangle.getTopLeftCorner().getRow(), nextCanvasCellCol), rectangle.getWidth(), rectangle.getHeight());
            case UP:
                nextCanvasCellRow = getTopLeftCornerRowOfPrevRect(rectangle);
                return new Rectangle(new Coordinate(nextCanvasCellRow, rectangle.getTopLeftCorner().getCol()), rectangle.getWidth(), rectangle.getHeight());
            case DOWN:
                nextCanvasCellRow = getTopLeftCornerRowOfNextRect(rectangle);
                return new Rectangle(new Coordinate(nextCanvasCellRow, rectangle.getTopLeftCorner().getCol()), rectangle.getWidth(), rectangle.getHeight());
            case LEFT:
                nextCanvasCellCol = getTopLeftCornerColOfPrevRect(rectangle);
                return new Rectangle(new Coordinate(rectangle.getTopLeftCorner().getRow(), nextCanvasCellCol), rectangle.getWidth(), rectangle.getHeight());
            case STILL:
                return new Rectangle(new Coordinate(rectangle.getTopLeftCorner().getRow(), rectangle.getTopLeftCorner().getCol()), rectangle.getWidth(), rectangle.getHeight());
            default:
                throw new IllegalStateException();
        }
    }

    public static double getTopLeftCornerColOfNextRect(Rectangle rectangle) {
        return rectangle.getTopLeftCorner().getCol() + rectangle.getWidth() - rectangle.getTopLeftCorner().getCol() % rectangle.getWidth();
    }

    public static double getTopLeftCornerColOfPrevRect(Rectangle rectangle) {
        double topLeftCornerColOfPrevCell = rectangle.getTopLeftCorner().getCol() - rectangle.getWidth() - rectangle.getTopLeftCorner().getCol() % rectangle.getWidth();
        return Math.max(0, topLeftCornerColOfPrevCell);
    }

    public static double getTopLeftCornerRowOfNextRect(Rectangle rectangle) {
        return rectangle.getTopLeftCorner().getRow() + rectangle.getHeight() - rectangle.getTopLeftCorner().getRow() % rectangle.getHeight();
    }

    public static double getTopLeftCornerRowOfPrevRect(Rectangle rectangle) {
        double topLeftCornerRoeOfPrevCell = rectangle.getTopLeftCorner().getRow() - rectangle.getHeight() - rectangle.getTopLeftCorner().getRow() % rectangle.getHeight();
        return Math.max(0, topLeftCornerRoeOfPrevCell);
    }
}
