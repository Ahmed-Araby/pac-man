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
        double newCanvasCol, newCanvasRow;

        switch (direction) {
            case RIGHT:
                newCanvasCol = canvasCol + Dimensions.PAC_MAN_STRIDE_PIXELS / 60;
                if (!pacManToWallCollisionDetection.isAboutToCollide(new Coordinate(canvasRow, newCanvasCol), DirectionsE.RIGHT)){
                    canvasCol = newCanvasCol;
                }
                drawRightOpenMousePacMan(con);
                break;
            case UP:
                newCanvasRow = canvasRow - Dimensions.PAC_MAN_STRIDE_PIXELS / 60;
                if (!pacManToWallCollisionDetection.isAboutToCollide(new Coordinate(newCanvasRow, canvasCol), DirectionsE.UP)) {
                    canvasRow = newCanvasRow;
                }
                drawUpOpenMousePacMan(con);
                break;
            case LEFT:
                newCanvasCol = canvasCol - Dimensions.PAC_MAN_STRIDE_PIXELS / 60;
                if (!pacManToWallCollisionDetection.isAboutToCollide(new Coordinate(canvasRow, newCanvasCol), DirectionsE.LEFT)) {
                    canvasCol = newCanvasCol;
                }
                drawLeftOpenMousePacMan(con);
                break;
            case DOWN:
                newCanvasRow = canvasRow + Dimensions.PAC_MAN_STRIDE_PIXELS / 60;
                if (!pacManToWallCollisionDetection.isAboutToCollide(new Coordinate(newCanvasRow, canvasCol), DirectionsE.DOWN)) {
                    canvasRow = newCanvasRow;
                }
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
        switch (code) {
            case RIGHT:
                direction = DirectionsE.RIGHT;
                break;
            case UP:
                direction = DirectionsE.UP;
                break;
            case LEFT:
                direction = DirectionsE.LEFT;
                break;
            case DOWN:
                direction = DirectionsE.DOWN;
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
}
