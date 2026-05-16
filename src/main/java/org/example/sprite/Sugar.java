package org.example.sprite;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.config.Configs;
import org.example.config.GameConfig;
import org.example.constant.*;
import org.example.entity.CanvasCoordinate;
import org.example.entity.CanvasRect;
import org.example.entity.MazeCell;
import org.example.event.Event;
import org.example.event.collision.PacMan2SugarCollisionEvent;
import org.example.event.Subscriber;
import org.example.maze.Playground;
import org.example.util.canvas.CanvasUtil;
import org.example.util.EnrichedThreadLocalRandom;
import org.example.util.MazeUtil;
import org.example.util.debug.DebugUtil;
import org.example.util.SugarUtil;

public class Sugar extends Sprite implements Subscriber {
    private final EnrichedThreadLocalRandom enrichedRandom = new EnrichedThreadLocalRandom();

    public Sugar() {
        super(SpriteE.SUGAR, -1, -1);

        for(int row = 0; row < Playground.height(); row++) {
            for (int col = 0; col < Playground.width(); col++) {
                if (Playground.isEmpty(row, col)) {
                    if (enrichedRandom.nextPercentage() <= Configs.SUPER_SUGAR_PERCENTAGE) {
                        Playground.set(row, col, SpriteE.SUPER_SUGAR);

                    } else {
                        Playground.set(row, col, SpriteE.SUGAR);
                    }
                }
            }
        }
    }

    @Override
    public void render(Canvas canvas) {
        final GraphicsContext con = canvas.getGraphicsContext2D();

        con.setFill(ColorC.SUGAR_COLOR);

        for (int row = 0; row < Playground.height(); row++) {
            for (int col = 0; col < Playground.width(); col++) {
                if (Playground.hasSugar(row, col)) {
                    final CanvasCoordinate cellTopLeftCornerCanvas = MazeUtil.getCanvasCord(row, col);
                    final CanvasCoordinate sugarCellTopLeftCornerCanvas = SugarUtil.getSugarTopLeftCornerCanvas(cellTopLeftCornerCanvas);
                    con.fillRect(sugarCellTopLeftCornerCanvas.getCol(), sugarCellTopLeftCornerCanvas.getRow(), DimensionsC.SUGAR_CELL_SIZE_PIXELS, DimensionsC.SUGAR_CELL_SIZE_PIXELS);
                    if(GameConfig.isDebugModeOn()) {
                        DebugUtil.drawVirtualRect(con, sugarCellTopLeftCornerCanvas.getCol(), sugarCellTopLeftCornerCanvas.getRow(), DimensionsC.SUGAR_CELL_SIZE_PIXELS, DimensionsC.SUGAR_CELL_SIZE_PIXELS, Color.CYAN);
                    }
                } else if (Playground.hasSuperSugar(row, col)) {
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
    public void update(Event event) {
        switch (event.getType()) {
            case PAC_MAN_SUGAR_COLLISION, PAC_MAN_SUPER_SUGAR_COLLISION:
                removeSugar(((PacMan2SugarCollisionEvent)event).getSugarRect());
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public void removeSugar(CanvasRect rect) {
        final MazeCell sugarCellMazeTopLeftCornerCoordinate = CanvasUtil.toMazeCoordinate(rect.getTopLeftCorner(), DirectionsE.STILL);
        Playground.set(sugarCellMazeTopLeftCornerCoordinate.getRow(), sugarCellMazeTopLeftCornerCoordinate.getCol(), SpriteE.EMPTY);
    }
}
