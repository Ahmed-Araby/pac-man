package org.example.sprite;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import org.example.constant.ColorConstants;
import org.example.constant.Configs;
import org.example.constant.Dimensions;
import org.example.constant.MazeCellContentE;
import org.example.entity.Coordinate;
import org.example.util.EnrichedThreadLocalRandom;
import org.example.util.MazeCanvasCoordinateMapping;
import org.example.util.sugar.SugarUtil;

public class Sugar implements Sprite {
    private final MazeCellContentE[][] maze;
    private final EnrichedThreadLocalRandom enrichedRandom = new EnrichedThreadLocalRandom();

    public Sugar(MazeCellContentE[][] maze) {
        this.maze = maze;

        for(int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[0].length; col++) {
                if (maze[row][col] == MazeCellContentE.EMPTY) {
                    if (enrichedRandom.nextPercentage() <= Configs.SUPER_SUGAR_PERCENTAGE) {
                        maze[row][col] = MazeCellContentE.SUPER_SUGAR;

                    } else {
                        maze[row][col] = MazeCellContentE.SUGAR;
                    }
                }
            }
        }
    }

    @Override
    public void render(Canvas canvas) {
        final GraphicsContext con = canvas.getGraphicsContext2D();

        con.setFill(ColorConstants.SUGAR_COLOR);

        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[0].length; col++) {
                if (maze[row][col] == MazeCellContentE.SUGAR) {
                    final Coordinate cellTopLeftCornerCanvas = MazeCanvasCoordinateMapping.mazeCordToCanvasCord(row, col);
                    final Coordinate sugarCellTopLeftCornerCanvas = SugarUtil.getSugarTopLeftCornerCanvas(cellTopLeftCornerCanvas);
                    con.fillOval(sugarCellTopLeftCornerCanvas.getCol(), sugarCellTopLeftCornerCanvas.getRow(), Dimensions.SUGAR_DIAMETER_PIXELS, Dimensions.SUGAR_DIAMETER_PIXELS);
                } else if (maze[row][col] == MazeCellContentE.SUPER_SUGAR) {
                    final Coordinate cellTopLeftCornerCanvas = MazeCanvasCoordinateMapping.mazeCordToCanvasCord(row, col);
                    final Coordinate sugarCellTopLeftCornerCanvas = SugarUtil.getSuperSugarTopLeftCornerCanvas(cellTopLeftCornerCanvas);
                    con.fillOval(sugarCellTopLeftCornerCanvas.getCol(), sugarCellTopLeftCornerCanvas.getRow(), Dimensions.SUPER_SUGAR_DIAMETER_PIXELS, Dimensions.SUPER_SUGAR_DIAMETER_PIXELS);
                }
            }
        }
    }

    @Override
    public void move(KeyEvent event) {
        throw new UnsupportedOperationException();
    }
}
