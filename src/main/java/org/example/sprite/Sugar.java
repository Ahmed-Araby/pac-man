package org.example.sprite;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import org.example.constant.ColorConstants;
import org.example.constant.Configs;
import org.example.constant.Dimensions;
import org.example.constant.SpriteE;
import org.example.entity.Coordinate;
import org.example.event.Event;
import org.example.event.PacManSugarCollisionEvent;
import org.example.event.Subscriber;
import org.example.util.EnrichedThreadLocalRandom;
import org.example.util.MazeCanvasCoordinateMapping;
import org.example.util.sugar.SugarUtil;

public class Sugar implements Sprite, Subscriber {
    private final SpriteE[][] maze;
    private final EnrichedThreadLocalRandom enrichedRandom = new EnrichedThreadLocalRandom();

    public Sugar(SpriteE[][] maze) {
        this.maze = maze;

        for(int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[0].length; col++) {
                if (maze[row][col] == SpriteE.EMPTY) {
                    if (enrichedRandom.nextPercentage() <= Configs.SUPER_SUGAR_PERCENTAGE) {
                        maze[row][col] = SpriteE.SUPER_SUGAR;

                    } else {
                        maze[row][col] = SpriteE.SUGAR;
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
                if (maze[row][col] == SpriteE.SUGAR) {
                    final Coordinate cellTopLeftCornerCanvas = MazeCanvasCoordinateMapping.mazeCordToCanvasCord(row, col);
                    final Coordinate sugarCellTopLeftCornerCanvas = SugarUtil.getSugarTopLeftCornerCanvas(cellTopLeftCornerCanvas);
                    con.fillRect(sugarCellTopLeftCornerCanvas.getCol(), sugarCellTopLeftCornerCanvas.getRow(), Dimensions.SUGAR_CELL_SIZE_PIXELS, Dimensions.SUGAR_CELL_SIZE_PIXELS);

                    con.setStroke(Color.CYAN);
                    con.strokeRect(sugarCellTopLeftCornerCanvas.getCol(), sugarCellTopLeftCornerCanvas.getRow(), Dimensions.SUGAR_CELL_SIZE_PIXELS, Dimensions.SUGAR_CELL_SIZE_PIXELS);

                } else if (maze[row][col] == SpriteE.SUPER_SUGAR) {
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


    @Override
    public void update(Event event) {
        switch (event.getType()) {
            case PAC_MAN_SUGAR_COLLISION, PAC_MAN_SUPER_SUGAR_COLLISION:
                ((PacManSugarCollisionEvent)event).getEatenSugarCanvasRect().forEach(this::removeSugar);
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public void removeSugar(Coordinate sugarRectCanvasTopLeftCoordinate) {
        final Coordinate sugarCellMazeTopLeftCornerCoordinate = MazeCanvasCoordinateMapping.canvasCordToMazeCordFloored(sugarRectCanvasTopLeftCoordinate);
        maze[(int) sugarCellMazeTopLeftCornerCoordinate.getRow()][(int) sugarCellMazeTopLeftCornerCoordinate.getCol()] = SpriteE.EMPTY;
    }
}
