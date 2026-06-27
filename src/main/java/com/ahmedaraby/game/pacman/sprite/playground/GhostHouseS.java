package com.ahmedaraby.game.pacman.sprite.playground;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import lombok.Getter;
import lombok.Setter;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.game.pacman.entity.MazeCell;
import com.ahmedaraby.game.pacman.playground.Playground;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.Sprite;

@Getter
@Setter
public class GhostHouseS extends Sprite {
    private final double eCol;
    private final double eRow;


    public GhostHouseS(GameState gameState, ConfigsEx configs) {
        super(gameState, configs, SpriteE.GHOST_HOUSE);

        final int mazeWidth = (int) (configs.CANVAS_WIDTH() / configs.PLAYGROUND_CELL_SIZE());
        final int mazeHeight = (int) (configs.CANVAS_HEIGHT() / configs.PLAYGROUND_CELL_SIZE());

        final double sCol = (mazeWidth / 2 - 3) * configs.PLAYGROUND_CELL_SIZE();
        final double sRow = (mazeHeight / 2 - 3) * configs.PLAYGROUND_CELL_SIZE();

        this.eCol = (mazeWidth / 2 + 3) * configs.PLAYGROUND_CELL_SIZE();
        this.eRow = (mazeHeight / 2) * configs.PLAYGROUND_CELL_SIZE();

        setTopLeftCorner(new Coordinate(sRow, sCol));
        setWidth(eCol - sCol + 1);
        setHeight(eRow - sRow + 1);

        putGhostHouse();
    }

    private void putGhostHouse() {
        final MazeCell doorCell = calcDoorCel();
        final double sCol = getCol();
        final double sRow = getRow();
        for (double i = sRow; i<=eRow; i+=configs.PLAYGROUND_CELL_SIZE()) {
            for (double j = sCol; j<=eCol; j+=configs.PLAYGROUND_CELL_SIZE()) {
                MazeCell cell = new Coordinate(i, j).toCell(DirectionsE.STILL.toVector());
                SpriteE spriteType;
                if (cell.equals(doorCell)) {
                    spriteType = SpriteE.GHOST_HOUSE_DOOR;
                } else if (i == sRow || i ==eRow || j == sCol || j==eCol) {
                    spriteType = SpriteE.GHOST_HOUSE_WALL;
                } else {
                    spriteType = SpriteE.GHOST_HOUSE_EMPTY;
                }
                Playground.set(cell.getRow(), cell.getCol(), spriteType);
            }
        }
    }

    @Override
    public void render(Canvas canvas) {
        final GraphicsContext con = canvas.getGraphicsContext2D();

        final MazeCell doorCell = calcDoorCel();
        final double sCol = getCol();
        final double sRow = getRow();

        for (double i = sRow; i<=eRow; i+=configs.PLAYGROUND_CELL_SIZE()) {
            for (double j = sCol; j<=eCol; j+=configs.PLAYGROUND_CELL_SIZE()) {
                MazeCell cell = new Coordinate(i, j).toCell(DirectionsE.STILL.toVector());
                if (cell.equals(doorCell)) {
                    continue;
                }
                if (i == sRow || i == eRow || j == sCol || j == eCol) {
                    con.setFill(configs.GHOST_HOUSE_WALL_COLOR());
                    con.fillRect(j, i, configs.PLAYGROUND_CELL_SIZE(), configs.PLAYGROUND_CELL_SIZE());
                }
            }
        }
    }

    private double calcDoorRow() {
        return getRow();
    }

    private double calcDoorCol() {
        return (eCol - getCol()) / 2 + getCol();
    }

    private MazeCell calcDoorCel() {
        final Coordinate cord = new Coordinate(calcDoorRow(), calcDoorCol());
        return cord.toCell(DirectionsE.STILL.toVector());
    }

}
