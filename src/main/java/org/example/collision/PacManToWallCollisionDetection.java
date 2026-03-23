package org.example.collision;

import org.example.constant.DimensionsC;
import org.example.constant.DirectionsE;
import org.example.constant.SpriteE;
import org.example.entity.CanvasCoordinate;
import org.example.entity.MazeCoordinate;
import org.example.entity.CanvasRect;
import org.example.event.Event;
import org.example.event.manager.SyncEventManager;
import org.example.event.movement.PacManMovementAttemptApprovedEvent;
import org.example.event.movement.PacManMovementAttemptDeniedEvent;
import org.example.event.movement.PacManMovementAttemptEvent;
import org.example.event.Subscriber;
import org.example.util.CanvasUtil;
import org.example.util.CanvasRectUtils;

import java.util.List;

public class PacManToWallCollisionDetection implements Subscriber {
    private SpriteE[][] maze;
    private SyncEventManager syncEventManager;

    public PacManToWallCollisionDetection(SpriteE[][] maze, SyncEventManager syncEventManager) {
        this.maze = maze;
        this.syncEventManager = syncEventManager;
    }

    public void checkCollision(PacManMovementAttemptEvent event) {
        if (isPacManGoingOutOfCanvas(event.getRequestedPacManCanvasRectTopLeftCorner())) {
            publishMovementDenial(event);
            return;
        }

        final CanvasRect pacManCanvasCanvasRect = new CanvasRect(event.getRequestedPacManCanvasRectTopLeftCorner(), DimensionsC.PAC_MAN_DIAMETER_PIXELS, DimensionsC.PAC_MAN_DIAMETER_PIXELS);
        final List<CanvasCoordinate> pacManRect4Corners = CanvasRectUtils.get4Corners(pacManCanvasCanvasRect);
        final boolean isCollidingWithWall = pacManRect4Corners.stream()
                .map(this::toTopLeftCornerOfRectContainingPoint)
                .anyMatch(this::isPacManCollidingWithAWall);
        if (isCollidingWithWall) {
            publishMovementDenial(event);
        } else {
            publishMovementApproval(event);
        }
    }

    private CanvasCoordinate toTopLeftCornerOfRectContainingPoint(CanvasCoordinate point) {
        return CanvasRectUtils.getTopLeftCornerOfRectContainingPoint(DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS, point);
    }

    private boolean isPacManGoingOutOfCanvas(CanvasCoordinate pacManCanvasTopLeftCorner) {
        return pacManCanvasTopLeftCorner.getRow() < 0 || pacManCanvasTopLeftCorner.getRow() > DimensionsC.CANVAS_HEIGHT_PIXELS - DimensionsC.PAC_MAN_DIAMETER_PIXELS ||
                pacManCanvasTopLeftCorner.getCol() < 0 || pacManCanvasTopLeftCorner.getCol() > DimensionsC.CANVAS_WIDTH_PIXELS - DimensionsC.PAC_MAN_DIAMETER_PIXELS;
    }

    private boolean isPacManCollidingWithAWall(CanvasCoordinate wallCanvasTopLeftCorner) {
        final MazeCoordinate wallMazeCord = CanvasUtil.toMazeCoordinate(wallCanvasTopLeftCorner, DirectionsE.STILL);
        return maze[wallMazeCord.getRow()][wallMazeCord.getCol()] == SpriteE.WALL;
    }

    private void publishMovementApproval(PacManMovementAttemptEvent event) {
        syncEventManager.notifySubscribers(new PacManMovementAttemptApprovedEvent(event.getCurrentPacManCanvasRectTopLeftCorner(), event.getRequestedPacManCanvasRectTopLeftCorner(), event.getRequestedDirection(), event.getSource()));
    }

    private void publishMovementDenial(PacManMovementAttemptEvent event) {
        syncEventManager.notifySubscribers(new PacManMovementAttemptDeniedEvent(event.getRequestedPacManCanvasRectTopLeftCorner(), event.getRequestedDirection(), event.getSource()));
    }

    @Override
    public void update(Event event) {
        switch (event.getType()) {
            case PAC_MAN_MOVEMENT_ATTEMPT -> checkCollision((PacManMovementAttemptEvent) event);
            default -> throw new UnsupportedOperationException();
        }
    }
}
