package com.ahmedaraby.game.pacman.sprite;

import com.ahmedaraby.game.pacman.config.Configs;
import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.game.pacman.event.EventType;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.entity.Rectangle;
import com.ahmedaraby.game.pacman.event.Event;
import com.ahmedaraby.jengine.event.Subscriber;
import com.ahmedaraby.game.pacman.event.movement.PacManMovementAttemptApprovedEvent;
import com.ahmedaraby.game.pacman.event.movement.PacManMovementAttemptDeniedEvent;
import com.ahmedaraby.game.pacman.event.movement.PacManMovementRequestEvent;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.util.pacman.PacManGraphicsUtil;
import com.ahmedaraby.game.pacman.util.pacman.PixelStrideTracker;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.playground.Playground;
import com.ahmedaraby.game.pacman.util.pacman.TurnBuffer;

/**
 * notes:
 * - PacManMovementAttemptEvents has to be processed sequentially, otherwise some user inputs or buffered turns can be lost
 */
public class PacMan extends MovingSprite implements Subscriber<EventType> {

    private final TurnBuffer turnBuffer;
    private final PixelStrideTracker closedMousePixelStrideTracker;

    public PacMan(GameState gameState) {
        super(gameState, SpriteE.PAC_MAN, null, DimensionsC.PAC_MAN_DIAMETER_PIXELS, DimensionsC.PAC_MAN_DIAMETER_PIXELS, DirectionsE.STILL);

        final Coordinate emptyCellPos = Playground.getEmptyMazePosition();
        setTopLeftCorner(emptyCellPos);

        this.turnBuffer = new TurnBuffer();
        this.closedMousePixelStrideTracker = new PixelStrideTracker(DimensionsC.PAC_MAN_CLOSED_MOUSE_DISTANCE_PIXELS,
                DimensionsC.PAC_MAN_CLOSED_MOUSE_DISTANCE_PIXELS + DimensionsC.PAC_MAN_OPEN_MOUSE_DISTANCE_PIXELS // just the same as DimensionsC.PAC_MAN_COMPLETE_MOUSE_MOVEMENT_DISTANCE_PIXELS
        );
    }

    @Override
    public void render(Canvas canvas) {
        final GraphicsContext con = canvas.getGraphicsContext2D();
        final double col = getCol();
        final double row = getRow();
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
        throw new IllegalStateException("PacMan.move is not implemented, refactoring is coming in the way");
    }

    private void attemptMovement(PacManMovementRequestEvent event) {
        final double col = getCol();
        final double row = getRow();
        double newCol = getCol();
        double newRow = getRow();

        switch (event.getDirectionsE()) {
            case RIGHT:
                newCol = col + DimensionsC.PAC_MAN_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_STRIDE;
                break;
            case UP:
                newRow = row - DimensionsC.PAC_MAN_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_STRIDE;
                break;
            case LEFT:
                newCol = col - DimensionsC.PAC_MAN_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_STRIDE;
                break;
            case DOWN:
                newRow = row + DimensionsC.PAC_MAN_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_STRIDE;
                break;
            case STILL:
                dir = event.getDirectionsE();
        }

        if (DirectionsE.STILL == event.getDirectionsE()) {
            return;
        }

        final Coordinate nextCord = new Coordinate(newRow, newCol);
        final Rectangle virtualPacManRect = new Rectangle(nextCord, DimensionsC.PAC_MAN_DIAMETER_PIXELS, DimensionsC.PAC_MAN_DIAMETER_PIXELS);

        if (!virtualPacManRect.within(gameState.getMaze().getRect())) {
            final PacManMovementAttemptDeniedEvent deniedEvent = new PacManMovementAttemptDeniedEvent(
                    nextCord, event.getDirectionsE(), event.getSource()
            );
            handleDeniedMovementAttempt(deniedEvent);
            return;
        }

        if (isCollidingWithWallOrGhostHWall(nextCord)) {
            final PacManMovementAttemptDeniedEvent deniedEvent = new PacManMovementAttemptDeniedEvent(
                    nextCord, event.getDirectionsE(), event.getSource()
            );
            handleDeniedMovementAttempt(deniedEvent);
        } else {
            final PacManMovementAttemptApprovedEvent approvedEvent = new PacManMovementAttemptApprovedEvent(
                    getTopLeftCorner(), nextCord, event.getDirectionsE(), event.getSource()
            );
            handleApprovedMovementAttempt(approvedEvent);
        }
    }

    private void handleApprovedMovementAttempt(PacManMovementAttemptApprovedEvent event) {
        System.out.println("approved move");
        setRow(event.getRequestedPacManCanvasRectTopLeftCorner().getRow());
        setCol(event.getRequestedPacManCanvasRectTopLeftCorner().getCol());
        dir = event.getRequestedDirection();

        if (event.getMovementAttemptSource() instanceof Scene || event.getMovementAttemptSource() instanceof TurnBuffer) {
            // user input or turn buffer automated move
            this.turnBuffer.discardTurnBuffer();
        } else if (event.getMovementAttemptSource() instanceof PacMan) {
            // automated straight line movement
            final Rectangle pacManCanvasRectangle = new Rectangle(event.getRequestedPacManCanvasRectTopLeftCorner(), DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS);
            if (turnBuffer.isThereBufferedTurn(pacManCanvasRectangle, event.getRequestedDirection())) {
                attemptMovement(turnBuffer.getBufferedPacManAutomatedMovementRequest());
            }
        }
    }

    private void handleDeniedMovementAttempt(PacManMovementAttemptDeniedEvent event) {
        System.out.println("denied move");
        if (event.getMovementAttemptSource() instanceof Scene) {
            // user input
            if(turnBuffer.isBlockedTurn(dir, event.getRequestedDirection())) {
                final Rectangle pacManCanvasRectangle = new Rectangle(new Coordinate(getRow(), getCol()), DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS);
                turnBuffer.bufferTurn(event.getRequestedDirection(), pacManCanvasRectangle);
            }
        }
        // do nothing for denied automated movements
    }

    @Override
    public void update(Event<EventType> event) {
        switch (event.getType()) {
            case PAC_MAN_MOVEMENT_REQUEST:
                attemptMovement(((PacManMovementRequestEvent)event));
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }
}
