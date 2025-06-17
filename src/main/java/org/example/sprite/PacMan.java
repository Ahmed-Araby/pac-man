package org.example.sprite;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.example.collision.PacManToWallCollisionDetection;
import org.example.constant.Configs;
import org.example.constant.Dimensions;
import org.example.constant.DirectionsE;
import org.example.constant.PacManAutomatedMovementTypeE;
import org.example.entity.Coordinate;
import org.example.event.*;
import org.example.util.pacman.PacManGraphicsUtil;
import org.example.util.pacman.PixelStrideTracker;
import org.example.util.pacman.TurnBuffer;

public class PacMan implements Sprite, Subscriber {

    private double canvasCol;
    private double canvasRow;
    private DirectionsE direction;
    private PacManAutomatedMovementTypeE nextAutomatedMove;

    private final PacManToWallCollisionDetection pacManToWallCollisionDetection;
    private final TurnBuffer turnBuffer;
    private final PixelStrideTracker closedMousePixelStrideTracker;

    private final EventManager eventManager;

    public PacMan(double canvasCol, double canvasRow, PacManToWallCollisionDetection pacManToWallCollisionDetection, EventManager eventManager) {
        this.canvasCol = canvasCol;
        this.canvasRow = canvasRow;
        this.direction = DirectionsE.STILL;
        this.nextAutomatedMove = PacManAutomatedMovementTypeE.TURN_BUFFER;

        this.pacManToWallCollisionDetection = pacManToWallCollisionDetection;
        this.turnBuffer = new TurnBuffer();
        this.closedMousePixelStrideTracker = new PixelStrideTracker(Dimensions.PAC_MAN_CLOSED_MOUSE_DISTANCE_PIXELS,
                Dimensions.PAC_MAN_CLOSED_MOUSE_DISTANCE_PIXELS + Dimensions.PAC_MAN_OPEN_MOUSE_DISTANCE_PIXELS // just the same as Dimensions.PAC_MAN_COMPLETE_MOUSE_MOVEMENT_DISTANCE_PIXELS
        );

        this.eventManager = eventManager;
    }

    @Override
    public void render(Canvas canvas) {
        System.out.println("pac man row = " + canvasRow+ ", pac man col " + canvasCol);

        final GraphicsContext con = canvas.getGraphicsContext2D();

        switch (direction) {
            case RIGHT:
                createAutomaticPacManMovementRequest(DirectionsE.RIGHT);
                PacManGraphicsUtil.drawRightOpenMousePacMan(con, canvasCol, canvasRow);
                break;
            case UP:
                createAutomaticPacManMovementRequest(DirectionsE.UP);
                PacManGraphicsUtil.drawUpOpenMousePacMan(con, canvasCol, canvasRow);
                break;
            case LEFT:
                createAutomaticPacManMovementRequest(DirectionsE.LEFT);
                PacManGraphicsUtil.drawLeftOpenMousePacMan(con, canvasCol, canvasRow);
                break;
            case DOWN:
                createAutomaticPacManMovementRequest(DirectionsE.DOWN);
                PacManGraphicsUtil.drawDownOpenMousePacMan(con, canvasCol, canvasRow);
                break;
            case STILL:
                PacManGraphicsUtil.drawClosedMousePacMan(con, canvasCol, canvasRow);
                break;
        }

        closedMousePixelStrideTracker.stride(Dimensions.PAC_MAN_COMPLETE_MOUSE_MOVEMENT_DISTANCE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_MOUSE_OPEN_CLOSED_ANIMATION);

        if (!closedMousePixelStrideTracker.isDesiredPixelStrideAchieved()) {
            PacManGraphicsUtil.removePacMan(con, canvasCol, canvasRow);
            PacManGraphicsUtil.drawClosedMousePacMan(con, canvasCol, canvasRow);
        } else if (closedMousePixelStrideTracker.isRestPixelStrideAchieved()) {
            closedMousePixelStrideTracker.reset();
        }

        this.eventManager.notifySubscribers(new PacManCurrentLocationEvent(new Coordinate(canvasRow, canvasCol), direction, this));
    }


    @Override
    public void move(Event event) {
        throw new RuntimeException();
    }

    public boolean attemptAutomatedMovement(PacManMovementRequestEvent event) {
        double newCanvasCol, newCanvasRow;

        switch (event.getDirectionsE()) {
            case RIGHT:
                newCanvasCol = canvasCol + Dimensions.PAC_MAN_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_STRIDE;
                if (!pacManToWallCollisionDetection.isAboutToCollide(new Coordinate(canvasRow, newCanvasCol))){
                    direction = event.getDirectionsE();
                    canvasCol = newCanvasCol;
                    return true;
                }
                break;
            case UP:
                newCanvasRow = canvasRow - Dimensions.PAC_MAN_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_STRIDE;
                if (!pacManToWallCollisionDetection.isAboutToCollide(new Coordinate(newCanvasRow, canvasCol))) {
                    direction = event.getDirectionsE();
                    canvasRow = newCanvasRow;
                    return true;
                }
                break;
            case LEFT:
                newCanvasCol = canvasCol - Dimensions.PAC_MAN_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_STRIDE;
                if (!pacManToWallCollisionDetection.isAboutToCollide(new Coordinate(canvasRow, newCanvasCol))) {
                    direction = event.getDirectionsE();
                    canvasCol = newCanvasCol;
                    return true;
                }
                break;
            case DOWN:
                newCanvasRow = canvasRow + Dimensions.PAC_MAN_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_STRIDE;
                if (!pacManToWallCollisionDetection.isAboutToCollide(new Coordinate(newCanvasRow, canvasCol))) {
                    direction = event.getDirectionsE();
                    canvasRow = newCanvasRow;
                    return true;
                }
                break;
            case STILL:
                direction = event.getDirectionsE();
                return false;
        }

        return false;
    }

    public boolean attemptUserInputMovement(PacManMovementRequestEvent event) {
        double newCanvasCol, newCanvasRow;
        boolean detectedCollision = true;

        switch (event.getDirectionsE()) {
            case RIGHT:
                newCanvasCol = canvasCol + Dimensions.PAC_MAN_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_STRIDE;
                if (!pacManToWallCollisionDetection.isAboutToCollide(new Coordinate(canvasRow, newCanvasCol))){
                    detectedCollision = false;
                    direction = event.getDirectionsE();
                    canvasCol = newCanvasCol;
                }
                break;
            case UP:
                newCanvasRow = canvasRow - Dimensions.PAC_MAN_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_STRIDE;
                if (!pacManToWallCollisionDetection.isAboutToCollide(new Coordinate(newCanvasRow, canvasCol))) {
                    detectedCollision = false;
                    direction = event.getDirectionsE();
                    canvasRow = newCanvasRow;
                }
                break;
            case LEFT:
                newCanvasCol = canvasCol - Dimensions.PAC_MAN_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_STRIDE;
                if (!pacManToWallCollisionDetection.isAboutToCollide(new Coordinate(canvasRow, newCanvasCol))) {
                    detectedCollision = false;
                    direction = event.getDirectionsE();
                    canvasCol = newCanvasCol;
                }
                break;
            case DOWN:
                newCanvasRow = canvasRow + Dimensions.PAC_MAN_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_STRIDE;
                if (!pacManToWallCollisionDetection.isAboutToCollide(new Coordinate(newCanvasRow, canvasCol))) {
                    detectedCollision = false;
                    direction = event.getDirectionsE();
                    canvasRow = newCanvasRow;
                }
                break;
            case STILL:
                detectedCollision = false;
                direction = event.getDirectionsE();
                break;
        }

        if (turnBuffer.isBlockedTurn(detectedCollision, direction, event.getDirectionsE())) {
            turnBuffer.bufferTurn(event.getDirectionsE(), new Coordinate(canvasRow, canvasCol));
            nextAutomatedMove = PacManAutomatedMovementTypeE.TURN_BUFFER;
        } else {
            turnBuffer.discardTurnBuffer();
            nextAutomatedMove = PacManAutomatedMovementTypeE.STRAIGHT_LINE;
        }

        return !detectedCollision && event.getDirectionsE() != DirectionsE.STILL;
    }

    private void createAutomaticPacManMovementRequest(DirectionsE direction) {
        // this code is ugly I know
        if (nextAutomatedMove == PacManAutomatedMovementTypeE.TURN_BUFFER && isThereBufferedTurn()) {
            final PacManMovementRequestEvent bufferedPacManMovementRequestEvent = turnBuffer.getBufferedTurnKeyEvent();
            if (attemptAutomatedMovement(bufferedPacManMovementRequestEvent)) {
                nextAutomatedMove = PacManAutomatedMovementTypeE.STRAIGHT_LINE;
            } else {
                // don't waste the frame
                final PacManMovementRequestEvent straightLinePacManMovementRequestEvent = new PacManMovementRequestEvent(direction, this);
                attemptAutomatedMovement(straightLinePacManMovementRequestEvent);
            }
        } else {
            final PacManMovementRequestEvent straightLinePacManMovementRequestEvent = new PacManMovementRequestEvent(direction, this);
            attemptAutomatedMovement(straightLinePacManMovementRequestEvent);
            nextAutomatedMove = nextAutomatedMove == PacManAutomatedMovementTypeE.TURN_BUFFER ? PacManAutomatedMovementTypeE.STRAIGHT_LINE : PacManAutomatedMovementTypeE.TURN_BUFFER;
        }
    }

    private boolean isThereBufferedTurn() {
        return turnBuffer.isThereBufferedTurn(new Coordinate(canvasRow, canvasCol), direction, Dimensions.CANVAS_CELL_SIZE_PIXELS, Dimensions.CANVAS_CELL_SIZE_PIXELS);
    }

    @Override
    public void update(Event event) {
        switch (event.getType()) {
            case PAC_MAN_MOVEMENT_REQUEST:
                attemptUserInputMovement(((PacManMovementRequestEvent)event));
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }
}
