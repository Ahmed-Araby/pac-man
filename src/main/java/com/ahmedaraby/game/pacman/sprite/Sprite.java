package com.ahmedaraby.game.pacman.sprite;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.entity.MazeCell;
import com.ahmedaraby.game.pacman.playground.Playground;
import com.ahmedaraby.jengine.entity.Rectangle;
import com.ahmedaraby.game.pacman.model.GameState;
import javafx.scene.canvas.Canvas;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.jengine.entity.Coordinate;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public abstract class Sprite {
    protected GameState gameState;
    protected ConfigsEx configs;

    protected SpriteE type;
    protected Coordinate topLeftCorner;
    protected double width;
    protected double height;

    public Sprite(GameState gameState, ConfigsEx configs, SpriteE type) {
        this.gameState = gameState;
        this.configs = configs;

        this.type = type;
    }

    public abstract void render(Canvas canvas);

    public void init() {
        System.out.println("Sprite.init() method is not implemented for sprite " + this.getClass().getSimpleName());
    }

    public Rectangle getRect() {
        return new Rectangle(topLeftCorner, width, height);
    }

    public double getCol() {
        return topLeftCorner.getCol();
    }

    public double getRow() {
        return topLeftCorner.getRow();
    }

    public void setCol(double col) {
        topLeftCorner = new Coordinate(topLeftCorner.getRow(), col);
    }

    public void setRow(double row) {
        topLeftCorner = new Coordinate(row, topLeftCorner.getCol());
    }
}
