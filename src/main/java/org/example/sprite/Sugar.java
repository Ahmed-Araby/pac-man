package org.example.sprite;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.config.GameConfig;
import org.example.constant.*;
import org.example.entity.CanvasCoordinate;
import org.example.entity.MazeCoordinate;
import org.example.event.Event;
import org.example.event.PacManSugarCollisionEvent;
import org.example.event.Subscriber;
import org.example.util.CanvasUtil;
import org.example.util.EnrichedThreadLocalRandom;
import org.example.util.MazeUtil;
import org.example.util.debug.DebugUtil;
import org.example.util.SugarUtil;

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
                    final CanvasCoordinate cellTopLeftCornerCanvas = MazeUtil.getCanvasCord(row, col);
                    final CanvasCoordinate sugarCellTopLeftCornerCanvas = SugarUtil.getSugarTopLeftCornerCanvas(cellTopLeftCornerCanvas);
                    con.fillRect(sugarCellTopLeftCornerCanvas.getCol(), sugarCellTopLeftCornerCanvas.getRow(), DimensionsC.SUGAR_CELL_SIZE_PIXELS, DimensionsC.SUGAR_CELL_SIZE_PIXELS);
                    if(GameConfig.isDebugModeOn()) {
                        DebugUtil.drawVirtualRect(con, sugarCellTopLeftCornerCanvas.getCol(), sugarCellTopLeftCornerCanvas.getRow(), DimensionsC.SUGAR_CELL_SIZE_PIXELS, DimensionsC.SUGAR_CELL_SIZE_PIXELS, Color.CYAN);
                    }
                } else if (maze[row][col] == SpriteE.SUPER_SUGAR) {
                    final CanvasCoordinate cellTopLeftCornerCanvas = MazeUtil.getCanvasCord(row, col);
                    final CanvasCoordinate sugarCellTopLeftCornerCanvas = SugarUtil.getSuperSugarTopLeftCornerCanvas(cellTopLeftCornerCanvas);
                    con.fillOval(sugarCellTopLeftCornerCanvas.getCol(), sugarCellTopLeftCornerCanvas.getRow(), DimensionsC.SUPER_SUGAR_DIAMETER_PIXELS, DimensionsC.SUPER_SUGAR_DIAMETER_PIXELS);
                    if(GameConfig.isDebugModeOn()) {
                        DebugUtil.drawVirtualRect(con, sugarCellTopLeftCornerCanvas.getCol(), sugarCellTopLeftCornerCanvas.getRow(), DimensionsC.SUPER_SUGAR_DIAMETER_PIXELS, DimensionsC.SUPER_SUGAR_DIAMETER_PIXELS, Color.CYAN);
                    }
                }
            }
        }
    }

    @Override
    public void move(Event event) {
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

    public void removeSugar(CanvasCoordinate sugarRectCanvasTopLeftCanvasCoordinate) {
        final MazeCoordinate sugarCellMazeTopLeftCornerCoordinate = CanvasUtil.toMazeCoordinate(sugarRectCanvasTopLeftCanvasCoordinate, DirectionsE.STILL);
        maze[sugarCellMazeTopLeftCornerCoordinate.getRow()][sugarCellMazeTopLeftCornerCoordinate.getCol()] = SpriteE.EMPTY;
    }
}
