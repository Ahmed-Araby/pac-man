package com.ahmedaraby.game.pacman.sprite;

import com.ahmedaraby.game.pacman.model.GameState;
import javafx.scene.canvas.Canvas;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.game.pacman.entity.CanvasCoordinate;


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
