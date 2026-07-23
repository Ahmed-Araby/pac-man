package com.ahmedaraby.game.pacman.sprite;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.entity.Cell;
import com.ahmedaraby.jengine.entity.Rectangle;
import com.ahmedaraby.game.pacman.model.GameState;
import javafx.scene.canvas.Canvas;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.jengine.entity.Coordinate;


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

    public double calcHEmptySpaceInPlaygroundCell() {
        final double spriteToCellSizeDiff = configs.PLAYGROUND_CELL_SIZE() - getWidth();
        final double leftGutterSize = 1; // almost
        final double rightGutterSize = 1; // almost
        return Math.ceil(leftGutterSize + rightGutterSize + spriteToCellSizeDiff);
    }

    public double calcVEmptySpaceInPlaygroundCell() {
        final double spriteToCellSizeDiff = configs.PLAYGROUND_CELL_SIZE() - getHeight();
        final double topGutterSize = 1; // almost
        final double bottomGutterSize = 1; // almost
        return Math.ceil(topGutterSize + bottomGutterSize + spriteToCellSizeDiff);
    }

    /**
     * playground cell to which the sprite belongs is the cell that contains the center point of the sprite.
     * if the center point lies at the sub pixels between 2 cells, flooring is used,
     * (i.e. the left cell will be chosen over the right cell and the top cell will be chosen over the bottom cell).
     *
     * @return Cell
     */
    public Cell calcCell() {
        final Coordinate centerCord = calcCenterCord();
        final int cellCol = (int) (centerCord.getCol() / configs.PLAYGROUND_CELL_SIZE());
        final int cellRow = (int) (centerCord.getRow() / configs.PLAYGROUND_CELL_SIZE());
        return new Cell(cellRow, cellCol);
    }

    protected Coordinate calcCenterCord() {
        final double centerCol = getCol() + getWidth() / 2;
        final double centerRow = getRow() + getHeight() / 2;
        return new Coordinate(centerRow, centerCol);
    }

    public static Sprite buildVirtualSprite(Coordinate topLeftCorner, double width, double height, ConfigsEx configs) {
        return new Sprite(null, configs, SpriteE.VIRTUAL, topLeftCorner, width, height) {
            @Override
            public void render(Canvas canvas) {
                throw new IllegalStateException("virtual target sprite is not supposed to be rendered");
            }
        };
    }
}
