package com.ahmedaraby.game.pacman.sprite.playground;

import com.ahmedaraby.game.pacman.constant.ColorC;
import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.game.pacman.event.collision.PacMan2SugarCollisionEvent;
import com.ahmedaraby.game.pacman.model.GameState;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import com.ahmedaraby.game.pacman.config.Configs;
import com.ahmedaraby.game.pacman.entity.CanvasCoordinate;
import com.ahmedaraby.game.pacman.entity.Rectangle;
import com.ahmedaraby.game.pacman.entity.MazeCell;
import com.ahmedaraby.game.pacman.event.Event;
import com.ahmedaraby.game.pacman.event.Subscriber;
import com.ahmedaraby.game.pacman.maze.Playground;
import com.ahmedaraby.game.pacman.sprite.Sprite;
import com.ahmedaraby.game.pacman.util.canvas.CanvasUtil;
import com.ahmedaraby.game.pacman.util.EnrichedThreadLocalRandom;
import com.ahmedaraby.game.pacman.util.MazeUtil;
import com.ahmedaraby.game.pacman.util.SugarUtil;

public class Sugar extends Sprite implements Subscriber {
    private final EnrichedThreadLocalRandom enrichedRandom = new EnrichedThreadLocalRandom();

    public Sugar(GameState gameState) {
        super(gameState, SpriteE.SUGAR, null, DimensionsC.SUGAR_CELL_SIZE_PIXELS, DimensionsC.SUGAR_CELL_SIZE_PIXELS);

        for(int row = 0; row < Playground.height(); row++) {
            for (int col = 0; col < Playground.width(); col++) {
                if (Playground.isEmpty(row, col)) {
                    if (enrichedRandom.nextPercentage() > Configs.SUPER_SUGAR_PERCENTAGE) {
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
                }
            }
        }

    }

    @Override
    public void update(Event event) {
        switch (event.getType()) {
            case PAC_MAN_SUGAR_COLLISION:
                removeSugar(((PacMan2SugarCollisionEvent)event).getSugarRect());
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public void removeSugar(Rectangle rect) {
        final MazeCell sugarCellMazeTopLeftCornerCoordinate = CanvasUtil.toMazeCoordinate(rect.getTopLeftCorner(), DirectionsE.STILL);
        Playground.set(sugarCellMazeTopLeftCornerCoordinate.getRow(), sugarCellMazeTopLeftCornerCoordinate.getCol(), SpriteE.EMPTY);
    }
}
