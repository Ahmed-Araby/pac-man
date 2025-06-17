package org.example.collision;

import org.example.constant.Dimensions;
import org.example.constant.SpriteE;
import org.example.entity.Coordinate;
import org.example.entity.Rect;
import org.example.event.EventManager;
import org.example.event.Event;
import org.example.event.movement.PacManMovementAttemptApprovedEvent;
import org.example.event.movement.PacManMovementAttemptDeniedEvent;
import org.example.event.movement.PacManMovementAttemptEvent;
import org.example.event.Subscriber;
import org.example.util.MazeCanvasCoordinateMapping;
import org.example.util.RectUtils;

import java.util.List;

public class PacManToWallCollisionDetection implements Subscriber {
    private SpriteE[][] maze;
    private EventManager eventManager;

    public PacManToWallCollisionDetection(SpriteE[][] maze, EventManager eventManager) {
        this.maze = maze;
        this.eventManager = eventManager;
    }

    public void checkCollision(PacManMovementAttemptEvent event) {
        if (isPacManGoingOutOfCanvas(event.getRequestedPacManCanvasRectTopLeftCorner())) {
            publishMovementDenial(event);
            return;
        }

        final Rect pacManCanvasRect = new Rect(event.getRequestedPacManCanvasRectTopLeftCorner(), Dimensions.PAC_MAN_DIAMETER_PIXELS, Dimensions.PAC_MAN_DIAMETER_PIXELS);
        final List<Coordinate> pacManRect4Corners = RectUtils.get4Corners(pacManCanvasRect);
        final boolean isCollidingWithWall = pacManRect4Corners.stream()
                .map(this::toTopLeftCornerOfRectContainingPoint)
                .anyMatch(this::isPacManCollidingWithAWall);
        if (isCollidingWithWall) {
            publishMovementDenial(event);
        } else {
            publishMovementApproval(event);
        }
    }

    private Coordinate toTopLeftCornerOfRectContainingPoint(Coordinate point) {
        return RectUtils.getTopLeftCornerOfRectContainingPoint(Dimensions.CANVAS_CELL_SIZE_PIXELS, Dimensions.CANVAS_CELL_SIZE_PIXELS, point);
    }

    private boolean isPacManGoingOutOfCanvas(Coordinate pacManCanvasTopLeftCorner) {
        return pacManCanvasTopLeftCorner.getRow() < 0 || pacManCanvasTopLeftCorner.getRow() > Dimensions.CANVAS_HEIGHT_PIXELS - Dimensions.PAC_MAN_DIAMETER_PIXELS ||
                pacManCanvasTopLeftCorner.getCol() < 0 || pacManCanvasTopLeftCorner.getCol() > Dimensions.CANVAS_WIDTH_PIXELS - Dimensions.PAC_MAN_DIAMETER_PIXELS;
    }

    private boolean isPacManCollidingWithAWall(Coordinate wallCanvasTopLeftCorner) {
        final Coordinate wallMazeCord = MazeCanvasCoordinateMapping.canvasCordToMazeCordFloored(wallCanvasTopLeftCorner);
        return maze[(int) wallMazeCord.getRow()][(int) wallMazeCord.getCol()] == SpriteE.WALL;
    }

    private void publishMovementApproval(PacManMovementAttemptEvent event) {
        eventManager.notifySubscribers(new PacManMovementAttemptApprovedEvent(event.getCurrentPacManCanvasRectTopLeftCorner(), event.getRequestedPacManCanvasRectTopLeftCorner(), event.getRequestedDirection(), event.getSource()));
    }

    private void publishMovementDenial(PacManMovementAttemptEvent event) {
        eventManager.notifySubscribers(new PacManMovementAttemptDeniedEvent(event.getRequestedPacManCanvasRectTopLeftCorner(), event.getRequestedDirection(), event.getSource()));
    }

    @Override
    public void update(Event event) {
        switch (event.getType()) {
            case PAC_MAN_MOVEMENT_ATTEMPT -> checkCollision((PacManMovementAttemptEvent) event);
            default -> throw new UnsupportedOperationException();
        }
    }
}
