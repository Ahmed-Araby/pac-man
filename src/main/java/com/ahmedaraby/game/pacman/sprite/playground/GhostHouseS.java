package com.ahmedaraby.game.pacman.sprite.playground;

import com.ahmedaraby.game.pacman.constant.ColorC;
import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import lombok.Getter;
import lombok.Setter;
import com.ahmedaraby.game.pacman.entity.CanvasCoordinate;
import com.ahmedaraby.game.pacman.entity.MazeCell;
import com.ahmedaraby.game.pacman.maze.Playground;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.Sprite;
import com.ahmedaraby.game.pacman.util.canvas.CanvasUtil;

@Getter
@Setter
public class GhostHouseS extends Sprite {
    private final double eCol;
    private final double eRow;


    public GhostHouseS(GameState gameState) {
        super(gameState, SpriteE.GHOST_HOUSE, 0, 0);

        final int mazeWidth = (int) (DimensionsC.CANVAS_WIDTH_PIXELS / DimensionsC.MAZE_CELL_SIZE_PIXELS);
        final int mazeHeight = (int) (DimensionsC.CANVAS_HEIGHT_PIXELS / DimensionsC.MAZE_CELL_SIZE_PIXELS);

        final double sCol = (mazeWidth / 2 - 3) * DimensionsC.MAZE_CELL_SIZE_PIXELS;
        final double sRow = (mazeHeight / 2 - 3) * DimensionsC.MAZE_CELL_SIZE_PIXELS;
        setCol(sCol);
        setRow(sRow);

        this.eCol = (mazeWidth / 2 + 3) * DimensionsC.MAZE_CELL_SIZE_PIXELS;
        this.eRow = (mazeHeight / 2) * DimensionsC.MAZE_CELL_SIZE_PIXELS;
        putGhostHouse();
    }

    private void putGhostHouse() {
        final MazeCell doorCell = calcDoorCel();

        for (double i = row; i<=eRow; i+=DimensionsC.MAZE_CELL_SIZE_PIXELS) {
            for (double j = col; j<=eCol; j+=DimensionsC.MAZE_CELL_SIZE_PIXELS) {
                MazeCell cell = CanvasUtil.toMazeCoordinate(new CanvasCoordinate(i, j), DirectionsE.STILL);
                SpriteE spriteType = null;
                if (cell.equals(doorCell)) {
                    spriteType = SpriteE.GHOST_HOUSE_DOOR;
                } else if (i == row || i ==eRow || j==col || j==eCol) {
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

        for (double i = row; i<=eRow; i+=DimensionsC.MAZE_CELL_SIZE_PIXELS) {
            for (double j = col; j<=eCol; j+=DimensionsC.MAZE_CELL_SIZE_PIXELS) {
                MazeCell cell = CanvasUtil.toMazeCoordinate(new CanvasCoordinate(i, j), DirectionsE.STILL);
                if (cell.equals(doorCell)) {
//                    con.setFill(ColorC.GHOST_HOUSE_DOOR);
//                    con.fillRect(j, i, DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS);
                } else if (i == row || i ==eRow || j==col || j==eCol) {
                    con.setFill(ColorC.GHOST_HOUSE_WALL_COLOR);
                    con.fillRect(j, i, DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS);
                }
            }
        }
    }

    private double calcDoorRow() {
        return row;
    }

    private double calcDoorCol() {
        return (eCol - col) / 2 + col;
    }

    private MazeCell calcDoorCel() {
        final CanvasCoordinate cord = new CanvasCoordinate(calcDoorRow(), calcDoorCol());
        return CanvasUtil.toMazeCoordinate(cord, DirectionsE.STILL);
    }

}
