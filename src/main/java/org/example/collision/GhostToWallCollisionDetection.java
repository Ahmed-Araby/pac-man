package org.example.collision;

import org.example.constant.DimensionsC;
import org.example.constant.DirectionsE;
import org.example.entity.CanvasCoordinate;
import org.example.entity.MazeCell;
import org.example.entity.CanvasRect;
import org.example.event.ghost.GhostMovementAttemptEvent;
import org.example.maze.MazeMatrix;
import org.example.util.canvas.CanvasUtil;
import org.example.util.ghost.GhostUtil;
import org.example.util.canvas.CanvasRectUtils;

import java.util.List;

// [TODO] uniform collision detection classes
public class GhostToWallCollisionDetection {

    private GhostToWallCollisionDetection() {}

    public static boolean checkCollision(GhostMovementAttemptEvent event) {
        final CanvasCoordinate nextGhostCord = GhostUtil.move(event);
        if (isGoingOutOfCanvas(nextGhostCord)) {
            return true;
        }
        final CanvasRect ghostCanvasCanvasRect = new CanvasRect(nextGhostCord, DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS);
        final List<CanvasCoordinate> ghostRect4Corners = CanvasRectUtils.get4Corners(ghostCanvasCanvasRect);
        final boolean isCollidingWithWall = ghostRect4Corners.stream()
                .map(GhostToWallCollisionDetection::toTopLeftCornerOfRectContainingPoint)
                .anyMatch(GhostToWallCollisionDetection::isWall);
        return isCollidingWithWall;
    }

    // [TODO] move this method because it doesn't fit this class
    private static boolean isGoingOutOfCanvas(CanvasCoordinate topLeftCorner) {
        return topLeftCorner.getRow() < 0 || topLeftCorner.getRow() > DimensionsC.CANVAS_HEIGHT_PIXELS - DimensionsC.GHOST_HEIGHT_PIXELS ||
                topLeftCorner.getCol() < 0 || topLeftCorner.getCol() > DimensionsC.CANVAS_WIDTH_PIXELS - DimensionsC.GHOST_WIDTH_PIXELS;
    }

    private static CanvasCoordinate toTopLeftCornerOfRectContainingPoint(CanvasCoordinate point) {
        return CanvasRectUtils.getTopLeftCornerOfRectContainingPoint(DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS, point);
    }


    private static boolean isWall(CanvasCoordinate cord) {
        final MazeCell cell = CanvasUtil.toMazeCoordinate(cord, DirectionsE.STILL);
        return MazeMatrix.isWall(cell);
    }

}
