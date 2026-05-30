package com.ahmedaraby.game.pacman.sprite.playground;

import com.ahmedaraby.game.pacman.constant.ColorC;
import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.entity.Rectangle;
import com.ahmedaraby.game.pacman.entity.MazeCell;
import com.ahmedaraby.game.pacman.event.Event;
import com.ahmedaraby.game.pacman.event.Subscriber;
import com.ahmedaraby.game.pacman.event.collision.PacMan2SugarCollisionEvent;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.Sprite;
import com.ahmedaraby.game.pacman.util.MazeUtil;
import com.ahmedaraby.game.pacman.util.SugarUtil;
import com.ahmedaraby.game.pacman.util.canvas.CanvasUtil;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import com.ahmedaraby.game.pacman.playground.Playground;

public class SuperSugar extends Sprite implements Subscriber {

    public SuperSugar(GameState gameState) {
        super(gameState, SpriteE.SUPER_SUGAR, null, DimensionsC.SUPER_SUGAR_DIAMETER_PIXELS, DimensionsC.SUPER_SUGAR_DIAMETER_PIXELS);

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
                    final Coordinate cellTopLeftCornerCanvas = MazeUtil.getCanvasCord(row, col);
                    final Coordinate sugarCellTopLeftCornerCanvas = SugarUtil.getSuperSugarTopLeftCornerCanvas(cellTopLeftCornerCanvas);
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

    public void removeSugar(Rectangle rect) {
        final MazeCell sugarCellMazeTopLeftCornerCoordinate = CanvasUtil.toMazeCoordinate(rect.getTopLeftCorner(), DirectionsE.STILL);
        Playground.set(sugarCellMazeTopLeftCornerCoordinate.getRow(), sugarCellMazeTopLeftCornerCoordinate.getCol(), SpriteE.EMPTY);
    }
}
