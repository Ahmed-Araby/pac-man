package com.ahmedaraby.game.pacman.sprite.ghost;

import com.ahmedaraby.game.pacman.config.GhostModeActivePeriodsConf;
import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.game.pacman.event.Event;
import com.ahmedaraby.game.pacman.ghostmode.blinky.BlinkyChaser;
import com.ahmedaraby.game.pacman.ghostmode.clyde.ClydeScattered;
import com.ahmedaraby.game.pacman.ghostmode.common.Eaten;
import com.ahmedaraby.game.pacman.ghostmode.common.Frightened;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.playground.GhostHouseS;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.sprite.SpriteRegistry;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class Clyde extends Ghost {

    public Clyde(GameState gameState, SpriteRegistry<String, Image> spriteRegistry) {
        super(gameState, SpriteE.GHOST, -1, -1, DirectionsE.STILL);
        scattered = new ClydeScattered(this, gameState, spriteRegistry,
                GhostModeActivePeriodsConf.LEVEL_1_SCATTER_ACTIVE_PERIODS);
        // [TODO] use Clyde Chaser
        chaser = new BlinkyChaser(this, gameState, spriteRegistry,
                GhostModeActivePeriodsConf.LEVEL_1_CHASE_ACTIVE_PERIODS);
        frightened = new Frightened(this, gameState, spriteRegistry,
                GhostModeActivePeriodsConf.ALL_LEVELS_FRIGHTENED_MODE_ACTIVE_PERIODS);
        eaten = new Eaten(this, gameState, spriteRegistry);

        scattered.enter();
        activeMode = scattered;
    }

    @Override
    public void init() {
        super.init();
        final GhostHouseS ghostHouseS = gameState.getGhostHouseS();
        final double col = ghostHouseS.getCol() + 4 * DimensionsC.MAZE_CELL_SIZE_PIXELS;
        final double row = ghostHouseS.getERow() - DimensionsC.MAZE_CELL_SIZE_PIXELS;
        setTopLeftCorner(new Coordinate(row, col));
    }

    @Override
    public void move(Event event) {
        transitionMode(event);
        activeMode.move();
    }

    @Override
    public void render(Canvas canvas) {
        activeMode.render(canvas);
    }
}
