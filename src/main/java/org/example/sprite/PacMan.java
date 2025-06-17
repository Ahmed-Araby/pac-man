package org.example.sprite;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.example.constant.Configs;
import org.example.constant.Dimensions;
import org.example.constant.DirectionsE;
import org.example.entity.Coordinate;
import org.example.entity.Rect;
import org.example.event.*;
import org.example.event.manager.EventManager;
import org.example.event.manager.SyncEventManager;
import org.example.event.movement.PacManMovementAttemptApprovedEvent;
import org.example.event.movement.PacManMovementAttemptDeniedEvent;
import org.example.event.movement.PacManMovementAttemptEvent;
import org.example.event.movement.PacManMovementRequestEvent;
import org.example.util.pacman.PacManGraphicsUtil;
import org.example.util.pacman.PixelStrideTracker;
import org.example.util.pacman.TurnBuffer;

/**
 * notes:
 * - PacManMovementAttemptEvents has to be processed sequentially, otherwise some user inputs or buffered turns can be lost
 */
public class PacMan implements Sprite, Subscriber {

    private double canvasCol;
    private double canvasRow;
    private DirectionsE direction;

    private final TurnBuffer turnBuffer;
    private final PixelStrideTracker closedMousePixelStrideTracker;

    private final EventManager eventManager;
    // this synchronous event manager is concerned with pac man movement and collision detection with walls and turn buffer.
    private final SyncEventManager syncEventManager;

    public PacMan(double canvasCol, double canvasRow, EventManager eventManager, SyncEventManager syncEventManager) {
        this.canvasCol = canvasCol;
        this.canvasRow = canvasRow;
        this.direction = DirectionsE.STILL;

        this.turnBuffer = new TurnBuffer();
        this.closedMousePixelStrideTracker = new PixelStrideTracker(Dimensions.PAC_MAN_CLOSED_MOUSE_DISTANCE_PIXELS,
                Dimensions.PAC_MAN_CLOSED_MOUSE_DISTANCE_PIXELS + Dimensions.PAC_MAN_OPEN_MOUSE_DISTANCE_PIXELS // just the same as Dimensions.PAC_MAN_COMPLETE_MOUSE_MOVEMENT_DISTANCE_PIXELS
        );

        this.eventManager = eventManager;
        this.syncEventManager = syncEventManager;
    }

    @Override
    public void render(Canvas canvas) {
        System.out.println("pac man row = " + canvasRow+ ", pac man col " + canvasCol + ", direction = " + direction);

        final GraphicsContext con = canvas.getGraphicsContext2D();

        switch (direction) {
            case RIGHT:
                PacManGraphicsUtil.drawRightOpenMousePacMan(con, canvasCol, canvasRow);
                attemptMovement(new PacManMovementRequestEvent(DirectionsE.RIGHT, this));
                break;
            case UP:
                PacManGraphicsUtil.drawUpOpenMousePacMan(con, canvasCol, canvasRow);
                attemptMovement(new PacManMovementRequestEvent(DirectionsE.UP, this));
                break;
            case LEFT:
                PacManGraphicsUtil.drawLeftOpenMousePacMan(con, canvasCol, canvasRow);
                attemptMovement(new PacManMovementRequestEvent(DirectionsE.LEFT, this));
                break;
            case DOWN:
                PacManGraphicsUtil.drawDownOpenMousePacMan(con, canvasCol, canvasRow);
                attemptMovement(new PacManMovementRequestEvent(DirectionsE.DOWN, this));
                break;
            case STILL:
                PacManGraphicsUtil.drawClosedMousePacMan(con, canvasCol, canvasRow);
                break;
        }

        // close pac man mouse
        closedMousePixelStrideTracker.stride(Dimensions.PAC_MAN_COMPLETE_MOUSE_MOVEMENT_DISTANCE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_MOUSE_OPEN_CLOSED_ANIMATION);
        if (!closedMousePixelStrideTracker.isDesiredPixelStrideAchieved()) {
            PacManGraphicsUtil.removePacMan(con, canvasCol, canvasRow);
            PacManGraphicsUtil.drawClosedMousePacMan(con, canvasCol, canvasRow);
        } else if (closedMousePixelStrideTracker.isRestPixelStrideAchieved()) {
            closedMousePixelStrideTracker.reset();
        }
    }


    @Override
    public void move(Event event) {
        switch (event.getType()) {
            case PAC_MAN_MOVEMENT_ATTEMPT_APPROVED -> handleApprovedMovementAttempt((PacManMovementAttemptApprovedEvent) event);
            case PAC_MAN_MOVEMENT_ATTEMPT_DENIED -> handleDeniedMovementAttempt((PacManMovementAttemptDeniedEvent) event);
            default -> throw new IllegalArgumentException();
        }
    }

    private void attemptMovement(PacManMovementRequestEvent event) {
        double newCanvasCol, newCanvasRow;
        switch (event.getDirectionsE()) {
            case RIGHT:
                newCanvasCol = canvasCol + Dimensions.PAC_MAN_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_STRIDE;
                publishPacManMovementAttemptEvent(canvasRow, newCanvasCol, DirectionsE.RIGHT, event.getSource());
                break;
            case UP:
                newCanvasRow = canvasRow - Dimensions.PAC_MAN_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_STRIDE;
                publishPacManMovementAttemptEvent(newCanvasRow, canvasCol, DirectionsE.UP, event.getSource());
                break;
            case LEFT:
                newCanvasCol = canvasCol - Dimensions.PAC_MAN_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_STRIDE;
                publishPacManMovementAttemptEvent(canvasRow, newCanvasCol, DirectionsE.LEFT, event.getSource());
                break;
            case DOWN:
                newCanvasRow = canvasRow + Dimensions.PAC_MAN_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_STRIDE;
                publishPacManMovementAttemptEvent(newCanvasRow, canvasCol, DirectionsE.DOWN, event.getSource());
                break;
            case STILL:
                direction = event.getDirectionsE();
        }
    }
    private void publishPacManMovementAttemptEvent(double newCanvasRow, double newCanvasCol, DirectionsE desiredDirection, Object source) {
        syncEventManager.notifySubscribers(new PacManMovementAttemptEvent(new Coordinate(canvasRow, canvasCol), new Coordinate(newCanvasRow, newCanvasCol), desiredDirection, source));
    }

    private void handleApprovedMovementAttempt(PacManMovementAttemptApprovedEvent event) {
        System.out.println("approved move");
        canvasRow = event.getRequestedPacManCanvasRectTopLeftCorner().getRow();
        canvasCol = event.getRequestedPacManCanvasRectTopLeftCorner().getCol();
        direction = event.getRequestedDirection();

        if (event.getMovementAttemptSource() instanceof Scene || event.getMovementAttemptSource() instanceof TurnBuffer) {
            // user input or turn buffer automated move
            this.turnBuffer.discardTurnBuffer();
        } else if (event.getMovementAttemptSource() instanceof PacMan) {
            // automated straight line movement
            final Rect pacManCanvasRect = new Rect(event.getRequestedPacManCanvasRectTopLeftCorner(), Dimensions.CANVAS_CELL_SIZE_PIXELS, Dimensions.CANVAS_CELL_SIZE_PIXELS);
            if (turnBuffer.isThereBufferedTurn(pacManCanvasRect, event.getRequestedDirection())) {
                attemptMovement(turnBuffer.getBufferedPacManAutomatedMovementRequest());
            }
        }

        this.eventManager.notifySubscribers(new PacManCurrentLocationEvent(new Coordinate(canvasRow, canvasCol), direction, this));
    }

    private void handleDeniedMovementAttempt(PacManMovementAttemptDeniedEvent event) {
        System.out.println("denied move");
        if (event.getMovementAttemptSource() instanceof Scene) {
            // user input
            if(turnBuffer.isBlockedTurn(direction, event.getRequestedDirection())) {
                final Rect pacManCanvasRect = new Rect(new Coordinate(canvasRow, canvasCol), Dimensions.CANVAS_CELL_SIZE_PIXELS, Dimensions.CANVAS_CELL_SIZE_PIXELS);
                turnBuffer.bufferTurn(event.getRequestedDirection(), pacManCanvasRect);
            }
        }
        // do nothing for denied automated movements
    }

    @Override
    public void update(Event event) {
        switch (event.getType()) {
            case PAC_MAN_MOVEMENT_REQUEST:
                attemptMovement(((PacManMovementRequestEvent)event));
                break;
            case PAC_MAN_MOVEMENT_ATTEMPT_APPROVED, PAC_MAN_MOVEMENT_ATTEMPT_DENIED:
                move(event);
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }
}
