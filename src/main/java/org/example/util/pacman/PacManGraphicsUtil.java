package org.example.util.pacman;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import org.example.config.GameConfig;
import org.example.constant.ColorConstants;
import org.example.constant.Dimensions;
import org.example.util.debug.DebugUtil;

public class PacManGraphicsUtil {

    public static void removePacMan(GraphicsContext con, double pacManCanvasCol, double pacManCanvasRow) {
        con.setFill(ColorConstants.CANVAS_COLOR);
        con.fillArc(pacManCanvasCol, pacManCanvasRow, Dimensions.PAC_MAN_DIAMETER_PIXELS, Dimensions.PAC_MAN_DIAMETER_PIXELS,
                Dimensions.PAC_MAN_CLOSED_MOUSE_START_ANGLE_IN_DEGREES, Dimensions.PAC_MAN_CLOSED_MOUSE_ARC_EXTENT_IN_DEGREES, ArcType.ROUND);
        if(GameConfig.isDebugModeOn()) {
            DebugUtil.drawVirtualRect(con, pacManCanvasCol, pacManCanvasRow, Dimensions.PAC_MAN_DIAMETER_PIXELS, Dimensions.PAC_MAN_DIAMETER_PIXELS, Color.GREEN);
        }
    }

    public static void drawClosedMousePacMan(GraphicsContext con, double pacManCanvasCol, double pacManCanvasRow) {
        con.setFill(ColorConstants.PAC_MAN_COLOR);
        con.fillArc(pacManCanvasCol, pacManCanvasRow, Dimensions.PAC_MAN_DIAMETER_PIXELS, Dimensions.PAC_MAN_DIAMETER_PIXELS,
                Dimensions.PAC_MAN_CLOSED_MOUSE_START_ANGLE_IN_DEGREES, Dimensions.PAC_MAN_CLOSED_MOUSE_ARC_EXTENT_IN_DEGREES, ArcType.ROUND);
        if(GameConfig.isDebugModeOn()) {
            DebugUtil.drawVirtualRect(con, pacManCanvasCol, pacManCanvasRow, Dimensions.PAC_MAN_DIAMETER_PIXELS, Dimensions.PAC_MAN_DIAMETER_PIXELS, Color.GREEN);
        }
    }

    public static void drawRightOpenMousePacMan(GraphicsContext con, double pacManCanvasCol, double pacManCanvasRow) {
        con.setFill(ColorConstants.PAC_MAN_COLOR);
        con.fillArc(pacManCanvasCol, pacManCanvasRow, Dimensions.PAC_MAN_DIAMETER_PIXELS, Dimensions.PAC_MAN_DIAMETER_PIXELS,
                Dimensions.PAC_MAN_RIGHT_OPEN_MOUSE_START_ANGLE_IN_DEGREES, Dimensions.PAC_MAN_OPEN_MOUSE_ARC_EXTENT_IN_DEGREES, ArcType.ROUND);
        if(GameConfig.isDebugModeOn()) {
            DebugUtil.drawVirtualRect(con, pacManCanvasCol, pacManCanvasRow, Dimensions.PAC_MAN_DIAMETER_PIXELS, Dimensions.PAC_MAN_DIAMETER_PIXELS, Color.GREEN);
        }
    }

    public static void drawUpOpenMousePacMan(GraphicsContext con, double pacManCanvasCol, double pacManCanvasRow) {
        con.setFill(ColorConstants.PAC_MAN_COLOR);
        con.fillArc(pacManCanvasCol, pacManCanvasRow, Dimensions.PAC_MAN_DIAMETER_PIXELS, Dimensions.PAC_MAN_DIAMETER_PIXELS,
                Dimensions.PAC_MAN_UP_OPEN_MOUSE_START_ANGLE_IN_DEGREES, Dimensions.PAC_MAN_OPEN_MOUSE_ARC_EXTENT_IN_DEGREES, ArcType.ROUND);
        if(GameConfig.isDebugModeOn()) {
            DebugUtil.drawVirtualRect(con, pacManCanvasCol, pacManCanvasRow, Dimensions.PAC_MAN_DIAMETER_PIXELS, Dimensions.PAC_MAN_DIAMETER_PIXELS, Color.GREEN);
        }
    }

    public static void drawLeftOpenMousePacMan(GraphicsContext con, double pacManCanvasCol, double pacManCanvasRow) {
        con.setFill(ColorConstants.PAC_MAN_COLOR);
        con.fillArc(pacManCanvasCol, pacManCanvasRow, Dimensions.PAC_MAN_DIAMETER_PIXELS, Dimensions.PAC_MAN_DIAMETER_PIXELS,
                Dimensions.PAC_MAN_LEFT_OPEN_MOUSE_START_ANGLE_IN_DEGREES, Dimensions.PAC_MAN_OPEN_MOUSE_ARC_EXTENT_IN_DEGREES, ArcType.ROUND);
        if(GameConfig.isDebugModeOn()) {
            DebugUtil.drawVirtualRect(con, pacManCanvasCol, pacManCanvasRow, Dimensions.PAC_MAN_DIAMETER_PIXELS, Dimensions.PAC_MAN_DIAMETER_PIXELS, Color.GREEN);
        }
    }

    public static void drawDownOpenMousePacMan(GraphicsContext con, double pacManCanvasCol, double pacManCanvasRow) {
        con.setFill(ColorConstants.PAC_MAN_COLOR);
        con.fillArc(pacManCanvasCol, pacManCanvasRow, Dimensions.PAC_MAN_DIAMETER_PIXELS, Dimensions.PAC_MAN_DIAMETER_PIXELS,
                Dimensions.PAC_MAN_DOWN_OPEN_MOUSE_START_ANGLE_IN_DEGREES, Dimensions.PAC_MAN_OPEN_MOUSE_ARC_EXTENT_IN_DEGREES, ArcType.ROUND);
        if(GameConfig.isDebugModeOn()) {
            DebugUtil.drawVirtualRect(con, pacManCanvasCol, pacManCanvasRow, Dimensions.PAC_MAN_DIAMETER_PIXELS, Dimensions.PAC_MAN_DIAMETER_PIXELS, Color.GREEN);
        }

    }
}
