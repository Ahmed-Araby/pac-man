package org.example.entity;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.ArcType;
import org.example.collision.PacManToWallCollisionDetection;
import org.example.constant.ColorConstants;
import org.example.constant.Dimensions;
import org.example.constant.DirectionsE;
import org.example.maze.Coordinate;

public class PacMan implements Sprite{

    private double canvasCol;
    private double canvasRow;
    private DirectionsE direction;
    private PacManToWallCollisionDetection pacManToWallCollisionDetection;

    public PacMan(double canvasCol, double canvasRow, PacManToWallCollisionDetection pacManToWallCollisionDetection) {
        this.canvasCol = canvasCol;
        this.canvasRow = canvasRow;
        this.direction = DirectionsE.STILL;
        this.pacManToWallCollisionDetection = pacManToWallCollisionDetection;
    }

    public PacMan() {
        this.canvasCol = Dimensions.CANVAS_WIDTH_PIXELS - Dimensions.PAC_MAN_DIAMETER_PIXELS;
        this.canvasRow = Dimensions.CANVAS_HEIGHT_PIXELS - Dimensions.PAC_MAN_DIAMETER_PIXELS;
        this.direction = DirectionsE.STILL;
    }

    @Override
    public void render(Canvas canvas) {
        final GraphicsContext con = canvas.getGraphicsContext2D();
        con.setFill(ColorConstants.PAC_MAN_COLOR);
        switch (direction) {
            case RIGHT:
                move(createDummyKeyEvent(KeyCode.RIGHT));
                drawRightOpenMousePacMan(con);
                break;
            case UP:
                move(createDummyKeyEvent(KeyCode.UP));
                drawUpOpenMousePacMan(con);
                break;
            case LEFT:
                move(createDummyKeyEvent(KeyCode.LEFT));
                drawLeftOpenMousePacMan(con);
                break;
            case DOWN:
                move(createDummyKeyEvent(KeyCode.DOWN));
                drawDownOpenMousePacMan(con);
                break;
            case STILL:
                drawClosedMousePacMan(con);
                break;
        }

        System.out.println("pac man row = " + canvasRow + ", pac man col = " + canvasCol + ", pac man stride = " + Dimensions.PAC_MAN_STRIDE_PIXELS / 60);
    }

    @Override
    public void move(KeyEvent event) {
        final KeyCode code = event.getCode();
        double newCanvasCol, newCanvasRow;

        switch (code) {
            case RIGHT:
                newCanvasCol = canvasCol + Dimensions.PAC_MAN_STRIDE_PIXELS / 60;
                if (!pacManToWallCollisionDetection.isAboutToCollide(new Coordinate(canvasRow, newCanvasCol), DirectionsE.RIGHT)){
                    direction = DirectionsE.RIGHT;
                    canvasCol = newCanvasCol;
                }
                break;
            case UP:
                newCanvasRow = canvasRow - Dimensions.PAC_MAN_STRIDE_PIXELS / 60;
                if (!pacManToWallCollisionDetection.isAboutToCollide(new Coordinate(newCanvasRow, canvasCol), DirectionsE.UP)) {
                    direction = DirectionsE.UP;
                    canvasRow = newCanvasRow;
                }
                break;
            case LEFT:
                newCanvasCol = canvasCol - Dimensions.PAC_MAN_STRIDE_PIXELS / 60;
                if (!pacManToWallCollisionDetection.isAboutToCollide(new Coordinate(canvasRow, newCanvasCol), DirectionsE.LEFT)) {
                    direction = DirectionsE.LEFT;
                    canvasCol = newCanvasCol;
                }
                break;
            case DOWN:
                newCanvasRow = canvasRow + Dimensions.PAC_MAN_STRIDE_PIXELS / 60;
                if (!pacManToWallCollisionDetection.isAboutToCollide(new Coordinate(newCanvasRow, canvasCol), DirectionsE.DOWN)) {
                    direction = DirectionsE.DOWN;
                    canvasRow = newCanvasRow;
                }
                break;
            case SPACE:
                direction = DirectionsE.STILL;
                break;
        }
    }

    private void drawClosedMousePacMan(GraphicsContext con) {
        con.fillArc(canvasCol, canvasRow, Dimensions.PAC_MAN_DIAMETER_PIXELS, Dimensions.PAC_MAN_DIAMETER_PIXELS,
                Dimensions.PAC_MAN_CLOSED_MOUSE_START_ANGLE_IN_DEGREES, Dimensions.PAC_MAN_CLOSED_MOUSE_ARC_EXTENT_IN_DEGREES, ArcType.ROUND);
    }

    private void drawRightOpenMousePacMan(GraphicsContext con) {
        con.fillArc(canvasCol, canvasRow, Dimensions.PAC_MAN_DIAMETER_PIXELS, Dimensions.PAC_MAN_DIAMETER_PIXELS,
                Dimensions.PAC_MAN_RIGHT_OPEN_MOUSE_START_ANGLE_IN_DEGREES, Dimensions.PAC_MAN_OPEN_MOUSE_ARC_EXTENT_IN_DEGREES, ArcType.ROUND);
    }

    private void drawUpOpenMousePacMan(GraphicsContext con) {
        con.fillArc(canvasCol, canvasRow, Dimensions.PAC_MAN_DIAMETER_PIXELS, Dimensions.PAC_MAN_DIAMETER_PIXELS,
                Dimensions.PAC_MAN_UP_OPEN_MOUSE_START_ANGLE_IN_DEGREES, Dimensions.PAC_MAN_OPEN_MOUSE_ARC_EXTENT_IN_DEGREES, ArcType.ROUND);
    }

    private void drawLeftOpenMousePacMan(GraphicsContext con) {
        con.fillArc(canvasCol, canvasRow, Dimensions.PAC_MAN_DIAMETER_PIXELS, Dimensions.PAC_MAN_DIAMETER_PIXELS,
                Dimensions.PAC_MAN_LEFT_OPEN_MOUSE_START_ANGLE_IN_DEGREES, Dimensions.PAC_MAN_OPEN_MOUSE_ARC_EXTENT_IN_DEGREES, ArcType.ROUND);
    }

    private void drawDownOpenMousePacMan(GraphicsContext con) {
        con.fillArc(canvasCol, canvasRow, Dimensions.PAC_MAN_DIAMETER_PIXELS, Dimensions.PAC_MAN_DIAMETER_PIXELS,
                Dimensions.PAC_MAN_DOWN_OPEN_MOUSE_START_ANGLE_IN_DEGREES, Dimensions.PAC_MAN_OPEN_MOUSE_ARC_EXTENT_IN_DEGREES, ArcType.ROUND);
    }

    private KeyEvent createDummyKeyEvent(KeyCode code) {
        return new KeyEvent(null, null, null, code, false, false, false, false);
    }
}
