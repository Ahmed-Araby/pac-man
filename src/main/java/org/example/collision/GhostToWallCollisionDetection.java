package org.example.collision;

import org.example.constant.DimensionsC;
import org.example.constant.DirectionsE;
import org.example.entity.CanvasCoordinate;
import org.example.entity.MazeCell;
import org.example.entity.CanvasRect;
import org.example.event.ghost.GhostMovementAttemptEvent;
import org.example.maze.MazeMatrix;
import org.example.util.CanvasUtil;
import org.example.util.ghost.GhostUtil;
import org.example.util.CanvasRectUtils;

import java.util.List;

public class GhostToWallCollisionDetection {

    private GhostToWallCollisionDetection() {}

    public static boolean checkCollision(GhostMovementAttemptEvent event) {
        final CanvasCoordinate nextGhostCord = GhostUtil.move(event);
        final CanvasRect ghostCanvasCanvasRect = new CanvasRect(nextGhostCord, DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS);
        final List<CanvasCoordinate> ghostRect4Corners = CanvasRectUtils.get4Corners(ghostCanvasCanvasRect);
        final boolean isCollidingWithWall = ghostRect4Corners.stream()
                .map(GhostToWallCollisionDetection::toTopLeftCornerOfRectContainingPoint)
                .anyMatch(GhostToWallCollisionDetection::isWall);
        return isCollidingWithWall;
    }

    private static CanvasCoordinate toTopLeftCornerOfRectContainingPoint(CanvasCoordinate point) {
        return CanvasRectUtils.getTopLeftCornerOfRectContainingPoint(DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS, point);
    }


    private static boolean isWall(CanvasCoordinate cord) {
        final MazeCell cell = CanvasUtil.toMazeCoordinate(cord, DirectionsE.STILL);
        return MazeMatrix.isWall(cell);
    }

}
