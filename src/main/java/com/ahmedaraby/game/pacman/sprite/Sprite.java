package com.ahmedaraby.game.pacman.sprite;

import com.ahmedaraby.game.pacman.entity.CanvasRect;
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
    protected CanvasCoordinate topLeftCorner;
    protected double width;
    protected double height;

    public Sprite(GameState gameState, SpriteE type) {
        this.gameState = gameState;
        this.type = type;
    }

    public abstract void render(Canvas canvas);

    public void init() {
        System.out.println("Sprite.init() method is not implemented for sprite " + this.getClass().getSimpleName());
    }

    public CanvasRect getRect() {
        return new CanvasRect(topLeftCorner, width, height);
    }

    public double getCol() {
        return topLeftCorner.getCol();
    }

    public double getRow() {
        return topLeftCorner.getRow();
    }

    public void setCol(double col) {
        topLeftCorner = new CanvasCoordinate(topLeftCorner.getRow(), col);
    }

    public void setRow(double row) {
        topLeftCorner = new CanvasCoordinate(row, topLeftCorner.getCol());
    }
}
