package org.example.entity;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.ArcType;
import org.example.constant.ColorConstants;
import org.example.constant.Dimensions;
import org.example.constant.DirectionsE;

public class PacMan implements Sprite{

    private double centerX;
    private double centerY;
    private DirectionsE direction;

    public PacMan() {
        this.centerX = Dimensions.CANVAS_WIDTH - Dimensions.PAC_MAN_DIAMِETER;
        this.centerY = Dimensions.CANVAS_HEIGHT - Dimensions.PAC_MAN_DIAMِETER;
        this.direction = DirectionsE.STILL;
    }

    @Override
    public void render(Canvas canvas) {
        final GraphicsContext con = canvas.getGraphicsContext2D();
        con.setFill(ColorConstants.PAC_MAN_COLOR);

        switch (direction) {
            case RIGHT:
                centerX+= Dimensions.PAC_MAN_STRIDE / 60;
                drawRightOpenMousePacMan(con);
                break;
            case UP:
                centerY-= Dimensions.PAC_MAN_STRIDE / 60;
                drawUpOpenMousePacMan(con);
                break;
            case LEFT:
                centerX-= Dimensions.PAC_MAN_STRIDE / 60;
                drawLeftOpenMousePacMan(con);
                break;
            case DOWN:
                centerY+= Dimensions.PAC_MAN_STRIDE / 60;
                drawDownOpenMousePacMan(con);
                break;
            case STILL:
                drawClosedMousePacMan(con);
                break;
        }
    }

    @Override
    public void move(KeyEvent event) {
        final KeyCode code = event.getCode();
        System.out.println("code = " + code);
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
        con.fillArc(centerX, centerY, Dimensions.PAC_MAN_DIAMِETER, Dimensions.PAC_MAN_DIAMِETER,
                Dimensions.PAC_MAN_CLOSED_MOUSE_START_ANGLE_IN_DEGREES, Dimensions.PAC_MAN_CLOSED_MOUSE_ARC_EXTENT_IN_DEGREES, ArcType.ROUND);
    }

    private void drawRightOpenMousePacMan(GraphicsContext con) {
        con.fillArc(centerX, centerY, Dimensions.PAC_MAN_DIAMِETER, Dimensions.PAC_MAN_DIAMِETER,
                Dimensions.PAC_MAN_RIGHT_OPEN_MOUSE_START_ANGLE_IN_DEGREES, Dimensions.PAC_MAN_OPEN_MOUSE_ARC_EXTENT_IN_DEGREES, ArcType.ROUND);
    }

    private void drawUpOpenMousePacMan(GraphicsContext con) {
        con.fillArc(centerX, centerY, Dimensions.PAC_MAN_DIAMِETER, Dimensions.PAC_MAN_DIAMِETER,
                Dimensions.PAC_MAN_UP_OPEN_MOUSE_START_ANGLE_IN_DEGREES, Dimensions.PAC_MAN_OPEN_MOUSE_ARC_EXTENT_IN_DEGREES, ArcType.ROUND);
    }

    private void drawLeftOpenMousePacMan(GraphicsContext con) {
        con.fillArc(centerX, centerY, Dimensions.PAC_MAN_DIAMِETER, Dimensions.PAC_MAN_DIAMِETER,
                Dimensions.PAC_MAN_LEFT_OPEN_MOUSE_START_ANGLE_IN_DEGREES, Dimensions.PAC_MAN_OPEN_MOUSE_ARC_EXTENT_IN_DEGREES, ArcType.ROUND);
    }

    private void drawDownOpenMousePacMan(GraphicsContext con) {
        con.fillArc(centerX, centerY, Dimensions.PAC_MAN_DIAMِETER, Dimensions.PAC_MAN_DIAMِETER,
                Dimensions.PAC_MAN_DOWN_OPEN_MOUSE_START_ANGLE_IN_DEGREES, Dimensions.PAC_MAN_OPEN_MOUSE_ARC_EXTENT_IN_DEGREES, ArcType.ROUND);
    }
}
