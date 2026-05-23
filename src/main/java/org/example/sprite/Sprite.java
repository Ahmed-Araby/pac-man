package org.example.sprite;

import javafx.scene.canvas.Canvas;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.constant.DimensionsC;
import org.example.constant.SpriteE;
import org.example.entity.CanvasCoordinate;
import org.example.entity.CanvasRect;
import org.example.event.Event;
import org.example.model.GameState;
import org.example.util.SpriteUtil;


@Getter
@Setter
@AllArgsConstructor
public abstract class Sprite {
    protected GameState gameState;
    protected SpriteE type;
    protected double col;
    protected double row;

    public abstract void render(Canvas canvas);

    public void init() {
        System.out.println("Sprite.init() method is not implemented for sprite " + this.getClass().getSimpleName());
    }

    public CanvasCoordinate getTopLeftCorner() {
        return new CanvasCoordinate(row, col);
    }
}
