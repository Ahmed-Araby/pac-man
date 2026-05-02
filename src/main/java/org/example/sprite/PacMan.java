package org.example.sprite;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.example.config.Configs;
import org.example.constant.DimensionsC;
import org.example.constant.DirectionsE;
import org.example.entity.CanvasCoordinate;
import org.example.entity.CanvasRect;
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
public class PacMan extends MovingSprite implements Subscriber {

    private final TurnBuffer turnBuffer;
    private final PixelStrideTracker closedMousePixelStrideTracker;

    // [TODO] remove this eventManager because it is not used
    private final EventManager eventManager;
    // this synchronous event manager is concerned with pac man movement and collision detection with walls and turn buffer.
    private final SyncEventManager syncEventManager;

    public PacMan(double col, double row, EventManager eventManager, SyncEventManager syncEventManager) {
        this.col = col;
        this.row = row;
        this.dir = DirectionsE.STILL;

        this.turnBuffer = new TurnBuffer();
        this.closedMousePixelStrideTracker = new PixelStrideTracker(DimensionsC.PAC_MAN_CLOSED_MOUSE_DISTANCE_PIXELS,
                DimensionsC.PAC_MAN_CLOSED_MOUSE_DISTANCE_PIXELS + DimensionsC.PAC_MAN_OPEN_MOUSE_DISTANCE_PIXELS // just the same as DimensionsC.PAC_MAN_COMPLETE_MOUSE_MOVEMENT_DISTANCE_PIXELS
        );

        this.eventManager = eventManager;
        this.syncEventManager = syncEventManager;
    }

    @Override
    public void render(Canvas canvas) {
        System.out.println("pac man row = " + row + ", pac man col " + col + ", direction = " + dir);

        final GraphicsContext con = canvas.getGraphicsContext2D();

        switch (dir) {
            case RIGHT:
                PacManGraphicsUtil.drawRightOpenMousePacMan(con, col, row);
                attemptMovement(new PacManMovementRequestEvent(DirectionsE.RIGHT, this));
                break;
            case UP:
                PacManGraphicsUtil.drawUpOpenMousePacMan(con, col, row);
                attemptMovement(new PacManMovementRequestEvent(DirectionsE.UP, this));
                break;
            case LEFT:
                PacManGraphicsUtil.drawLeftOpenMousePacMan(con, col, row);
                attemptMovement(new PacManMovementRequestEvent(DirectionsE.LEFT, this));
                break;
            case DOWN:
                PacManGraphicsUtil.drawDownOpenMousePacMan(con, col, row);
                attemptMovement(new PacManMovementRequestEvent(DirectionsE.DOWN, this));
                break;
            case STILL:
                PacManGraphicsUtil.drawClosedMousePacMan(con, col, row);
                break;
        }

        // close pac man mouse
        closedMousePixelStrideTracker.stride(DimensionsC.PAC_MAN_COMPLETE_MOUSE_MOVEMENT_DISTANCE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_MOUSE_OPEN_CLOSED_ANIMATION);
        if (!closedMousePixelStrideTracker.isDesiredPixelStrideAchieved()) {
            PacManGraphicsUtil.removePacMan(con, col, row);
            PacManGraphicsUtil.drawClosedMousePacMan(con, col, row);
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
                newCanvasCol = col + DimensionsC.PAC_MAN_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_STRIDE;
                publishPacManMovementAttemptEvent(row, newCanvasCol, DirectionsE.RIGHT, event.getSource());
                break;
            case UP:
                newCanvasRow = row - DimensionsC.PAC_MAN_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_STRIDE;
                publishPacManMovementAttemptEvent(newCanvasRow, col, DirectionsE.UP, event.getSource());
                break;
            case LEFT:
                newCanvasCol = col - DimensionsC.PAC_MAN_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_STRIDE;
                publishPacManMovementAttemptEvent(row, newCanvasCol, DirectionsE.LEFT, event.getSource());
                break;
            case DOWN:
                newCanvasRow = row + DimensionsC.PAC_MAN_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_STRIDE;
                publishPacManMovementAttemptEvent(newCanvasRow, col, DirectionsE.DOWN, event.getSource());
                break;
            case STILL:
                dir = event.getDirectionsE();
        }
    }
    private void publishPacManMovementAttemptEvent(double newCanvasRow, double newCanvasCol, DirectionsE desiredDirection, Object source) {
        syncEventManager.notifySubscribers(new PacManMovementAttemptEvent(new CanvasCoordinate(row, col), new CanvasCoordinate(newCanvasRow, newCanvasCol), desiredDirection, source));
    }

    private void handleApprovedMovementAttempt(PacManMovementAttemptApprovedEvent event) {
        System.out.println("approved move");
        row = event.getRequestedPacManCanvasRectTopLeftCorner().getRow();
        col = event.getRequestedPacManCanvasRectTopLeftCorner().getCol();
        dir = event.getRequestedDirection();

        if (event.getMovementAttemptSource() instanceof Scene || event.getMovementAttemptSource() instanceof TurnBuffer) {
            // user input or turn buffer automated move
            this.turnBuffer.discardTurnBuffer();
        } else if (event.getMovementAttemptSource() instanceof PacMan) {
            // automated straight line movement
            final CanvasRect pacManCanvasCanvasRect = new CanvasRect(event.getRequestedPacManCanvasRectTopLeftCorner(), DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS);
            if (turnBuffer.isThereBufferedTurn(pacManCanvasCanvasRect, event.getRequestedDirection())) {
                attemptMovement(turnBuffer.getBufferedPacManAutomatedMovementRequest());
            }
        }
    }

    private void handleDeniedMovementAttempt(PacManMovementAttemptDeniedEvent event) {
        System.out.println("denied move");
        if (event.getMovementAttemptSource() instanceof Scene) {
            // user input
            if(turnBuffer.isBlockedTurn(dir, event.getRequestedDirection())) {
                final CanvasRect pacManCanvasCanvasRect = new CanvasRect(new CanvasCoordinate(row, col), DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS);
                turnBuffer.bufferTurn(event.getRequestedDirection(), pacManCanvasCanvasRect);
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



    public CanvasCoordinate getCurrCanvasCord() {
        return new CanvasCoordinate(row, col);
    }
}
