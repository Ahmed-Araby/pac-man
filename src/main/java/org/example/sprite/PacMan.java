package org.example.sprite;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.example.collision.PacManToSugarCollisionDetection;
import org.example.collision.PacManToWallCollisionDetection;
import org.example.constant.Configs;
import org.example.constant.Dimensions;
import org.example.constant.DirectionsE;
import org.example.entity.Coordinate;
import org.example.util.pacman.PacManGraphicsUtil;
import org.example.util.pacman.PixelStrideTracker;
import org.example.util.pacman.TurnBuffer;

public class PacMan implements Sprite{

    private double canvasCol;
    private double canvasRow;
    private DirectionsE direction;

    private final PacManToWallCollisionDetection pacManToWallCollisionDetection;
    private final PacManToSugarCollisionDetection pacManToSugarCollisionDetection;
    private final TurnBuffer turnBuffer;
    private final PixelStrideTracker closedMousePixelStrideTracker;
    private boolean isPacManEating = false;

    public PacMan(double canvasCol, double canvasRow, PacManToWallCollisionDetection pacManToWallCollisionDetection, PacManToSugarCollisionDetection pacManToSugarCollisionDetection) {
        this.canvasCol = canvasCol;
        this.canvasRow = canvasRow;
        this.direction = DirectionsE.STILL;

        this.pacManToWallCollisionDetection = pacManToWallCollisionDetection;
        this.pacManToSugarCollisionDetection = pacManToSugarCollisionDetection;
        this.turnBuffer = new TurnBuffer();
        this.closedMousePixelStrideTracker = new PixelStrideTracker(Dimensions.PAC_MAN_CLOSED_MOUSE_DISTANCE_PIXELS,
                Dimensions.PAC_MAN_CLOSED_MOUSE_DISTANCE_PIXELS + Dimensions.PAC_MAN_OPEN_MOUSE_DISTANCE_PIXELS // just the same as Dimensions.PAC_MAN_COMPLETE_MOUSE_MOVEMENT_DISTANCE_PIXELS
        );
    }

    @Override
    public void render(Canvas canvas) {
        System.out.println("pac man row = " + canvasRow+ ", pac man col " + canvasCol);

        final GraphicsContext con = canvas.getGraphicsContext2D();

        switch (direction) {
            case RIGHT:
                move(createDummyKeyEvent(KeyCode.RIGHT));
                PacManGraphicsUtil.drawRightOpenMousePacMan(con, canvasCol, canvasRow);
                break;
            case UP:
                move(createDummyKeyEvent(KeyCode.UP));
                PacManGraphicsUtil.drawUpOpenMousePacMan(con, canvasCol, canvasRow);
                break;
            case LEFT:
                move(createDummyKeyEvent(KeyCode.LEFT));
                PacManGraphicsUtil.drawLeftOpenMousePacMan(con, canvasCol, canvasRow);
                break;
            case DOWN:
                move(createDummyKeyEvent(KeyCode.DOWN));
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

        if(pacManToSugarCollisionDetection.isEatingSugar(new Coordinate(canvasRow, canvasCol))) {
            System.out.println("eating sugar ******************");
        }
    }

    @Override
    public void move(KeyEvent event) {

        if (event.getSource() instanceof Scene){
            userInputMove(event);
            return;
        } else if (event.getSource() instanceof PacMan) {
            automatedMove(event);
        }

        if (turnBuffer.isThereBufferedTurn(new Coordinate(canvasRow, canvasCol), direction, Dimensions.CANVAS_CELL_SIZE_PIXELS, Dimensions.CANVAS_CELL_SIZE_PIXELS)) {
            System.out.println("there is a buffered turn *************");
            final KeyEvent bufferedTurnKeyEvent = turnBuffer.getBufferedTurnKeyEvent();
            automatedMove(bufferedTurnKeyEvent);
        }
    }

    public void automatedMove(KeyEvent event) {
        final DirectionsE requestedDirection = DirectionsE.from(event.getCode());
        double newCanvasCol, newCanvasRow;

        switch (requestedDirection) {
            case RIGHT:
                newCanvasCol = canvasCol + Dimensions.PAC_MAN_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_STRIDE;
                if (!pacManToWallCollisionDetection.isAboutToCollide(new Coordinate(canvasRow, newCanvasCol))){
                    direction = requestedDirection;
                    canvasCol = newCanvasCol;
                }
                break;
            case UP:
                newCanvasRow = canvasRow - Dimensions.PAC_MAN_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_STRIDE;
                if (!pacManToWallCollisionDetection.isAboutToCollide(new Coordinate(newCanvasRow, canvasCol))) {
                    direction = requestedDirection;
                    canvasRow = newCanvasRow;
                }
                break;
            case LEFT:
                newCanvasCol = canvasCol - Dimensions.PAC_MAN_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_STRIDE;
                if (!pacManToWallCollisionDetection.isAboutToCollide(new Coordinate(canvasRow, newCanvasCol))) {
                    direction = requestedDirection;
                    canvasCol = newCanvasCol;
                }
                break;
            case DOWN:
                newCanvasRow = canvasRow + Dimensions.PAC_MAN_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_STRIDE;
                if (!pacManToWallCollisionDetection.isAboutToCollide(new Coordinate(newCanvasRow, canvasCol))) {
                    direction = requestedDirection;
                    canvasRow = newCanvasRow;
                }
                break;
            case STILL:
                direction = requestedDirection;
                break;
        }
    }

    public void userInputMove(KeyEvent event) {

        final DirectionsE requestedDirection = DirectionsE.from(event.getCode());
        double newCanvasCol, newCanvasRow;
        boolean detectedCollision = true;

        switch (requestedDirection) {
            case RIGHT:
                newCanvasCol = canvasCol + Dimensions.PAC_MAN_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_STRIDE;
                if (!pacManToWallCollisionDetection.isAboutToCollide(new Coordinate(canvasRow, newCanvasCol))){
                    detectedCollision = false;
                    direction = requestedDirection;
                    canvasCol = newCanvasCol;
                }
                break;
            case UP:
                newCanvasRow = canvasRow - Dimensions.PAC_MAN_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_STRIDE;
                if (!pacManToWallCollisionDetection.isAboutToCollide(new Coordinate(newCanvasRow, canvasCol))) {
                    detectedCollision = false;
                    direction = requestedDirection;
                    canvasRow = newCanvasRow;
                }
                break;
            case LEFT:
                newCanvasCol = canvasCol - Dimensions.PAC_MAN_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_STRIDE;
                if (!pacManToWallCollisionDetection.isAboutToCollide(new Coordinate(canvasRow, newCanvasCol))) {
                    detectedCollision = false;
                    direction = requestedDirection;
                    canvasCol = newCanvasCol;
                }
                break;
            case DOWN:
                newCanvasRow = canvasRow + Dimensions.PAC_MAN_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_STRIDE;
                if (!pacManToWallCollisionDetection.isAboutToCollide(new Coordinate(newCanvasRow, canvasCol))) {
                    detectedCollision = false;
                    direction = requestedDirection;
                    canvasRow = newCanvasRow;
                }
                break;
            case STILL:
                detectedCollision = false;
                direction = requestedDirection;
                break;
        }

        if (turnBuffer.isBlockedTurn(detectedCollision, direction, requestedDirection)) {
            turnBuffer.bufferTurn(requestedDirection, new Coordinate(canvasRow, canvasCol));
        } else {
            turnBuffer.discardTurnBuffer();
        }
    }

    private KeyEvent createDummyKeyEvent(KeyCode code) {
        return new KeyEvent(this, null, null, null, null, code, false, false, false, false);
    }
}
