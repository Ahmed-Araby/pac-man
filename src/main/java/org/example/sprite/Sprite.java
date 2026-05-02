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
import org.example.util.SpriteUtil;


@Getter
@Setter
@AllArgsConstructor
public abstract class Sprite {
    protected SpriteE type;
    protected double col;
    protected double row;

    public abstract void render(Canvas canvas);


    public CanvasCoordinate getTopLeftCorner() {
        return new CanvasCoordinate(row, col);
    }

    public CanvasRect getRect() {
        return SpriteUtil.toRect(getTopLeftCorner(), type);
    }
}
