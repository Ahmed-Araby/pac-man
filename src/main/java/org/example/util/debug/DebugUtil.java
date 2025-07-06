package org.example.util.debug;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DebugUtil {
    public static void drawVirtualRect(GraphicsContext con, double pacManCanvasCol, double pacManCanvasRow, double width, double height, Color color) {
        con.setStroke(color);
        con.strokeRect(pacManCanvasCol, pacManCanvasRow, width, height);
    }

    public static void drawDummyPacman(GraphicsContext con, double pacManCanvasCol, double pacManCanvasRow, double width, double height, Color color) {
        con.setFill(color);
        con.fillOval(pacManCanvasCol, pacManCanvasRow, width, height);
        drawVirtualRect(con, pacManCanvasCol, pacManCanvasRow, width, height, Color.YELLOW);
    }
}
