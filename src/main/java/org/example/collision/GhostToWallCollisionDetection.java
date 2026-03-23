package org.example.collision;

import org.example.constant.Dimensions;
import org.example.constant.DirectionsE;
import org.example.constant.SpriteE;
import org.example.entity.CanvasCoordinate;
import org.example.entity.MazeCoordinate;
import org.example.entity.Rect;
import org.example.event.ghost.GhostMovementAttemptEvent;
import org.example.util.CoordinateUtil;
import org.example.util.GhostUtil;
import org.example.util.RectUtils;

import java.util.List;

public class GhostToWallCollisionDetection {
    private SpriteE[][] maze;

    public GhostToWallCollisionDetection(SpriteE[][] maze) {
        this.maze = maze;
    }

    public boolean checkCollision(GhostMovementAttemptEvent event) {
        final CanvasCoordinate nextGhostCord = GhostUtil.move(event);
        final Rect ghostCanvasRect = new Rect(nextGhostCord, Dimensions.PAC_MAN_DIAMETER_PIXELS, Dimensions.PAC_MAN_DIAMETER_PIXELS);
        final List<CanvasCoordinate> ghostRect4Corners = RectUtils.get4Corners(ghostCanvasRect);
        final boolean isCollidingWithWall = ghostRect4Corners.stream()
                .map(this::toTopLeftCornerOfRectContainingPoint)
                .anyMatch(this::isWall);
        return isCollidingWithWall;
    }

    private CanvasCoordinate toTopLeftCornerOfRectContainingPoint(CanvasCoordinate point) {
        return RectUtils.getTopLeftCornerOfRectContainingPoint(Dimensions.CANVAS_CELL_SIZE_PIXELS, Dimensions.CANVAS_CELL_SIZE_PIXELS, point);
    }


    private boolean isWall(CanvasCoordinate cord) {
        final MazeCoordinate wallMazeCord = CoordinateUtil.toMazeCoordinate(cord, DirectionsE.STILL);
        return maze[wallMazeCord.getRow()][wallMazeCord.getCol()] == SpriteE.WALL;
    }

}
