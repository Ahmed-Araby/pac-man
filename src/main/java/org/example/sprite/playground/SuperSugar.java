package org.example.sprite.playground;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.example.constant.*;
import org.example.entity.CanvasCoordinate;
import org.example.entity.CanvasRect;
import org.example.entity.MazeCell;
import org.example.event.Event;
import org.example.event.collision.PacMan2SugarCollisionEvent;
import org.example.event.Subscriber;
import org.example.maze.Playground;
import org.example.model.GameState;
import org.example.sprite.Sprite;
import org.example.util.canvas.CanvasUtil;
import org.example.util.MazeUtil;
import org.example.util.SugarUtil;

public class SuperSugar extends Sprite implements Subscriber {

    public SuperSugar(GameState gameState) {
        super(gameState, SpriteE.SUPER_SUGAR, -1, -1);

        for(int row = 0; row < Playground.height(); row++) {
            for (int col = 0; col < Playground.width(); col++) {
                if (Playground.isEmpty(row, col)) {
                    Playground.set(row, col, SpriteE.SUPER_SUGAR);
                }
            }
        }
    }

    @Override
    public void render(Canvas canvas) {
        final GraphicsContext con = canvas.getGraphicsContext2D();

        con.setFill(ColorC.SUPER_SUGAR_COLOR);

        for (int row = 0; row < Playground.height(); row++) {
            for (int col = 0; col < Playground.width(); col++) {
                if (Playground.hasSuperSugar(row, col)) {
                    final CanvasCoordinate cellTopLeftCornerCanvas = MazeUtil.getCanvasCord(row, col);
                    final CanvasCoordinate sugarCellTopLeftCornerCanvas = SugarUtil.getSuperSugarTopLeftCornerCanvas(cellTopLeftCornerCanvas);
                    con.fillOval(sugarCellTopLeftCornerCanvas.getCol(), sugarCellTopLeftCornerCanvas.getRow(), DimensionsC.SUPER_SUGAR_DIAMETER_PIXELS, DimensionsC.SUPER_SUGAR_DIAMETER_PIXELS);
                }
            }
        }
    }

    @Override
    public void update(Event event) {
        switch (event.getType()) {
            case PAC_MAN_SUPER_SUGAR_COLLISION:
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
