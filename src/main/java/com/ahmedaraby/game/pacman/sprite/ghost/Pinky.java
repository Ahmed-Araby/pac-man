package com.ahmedaraby.game.pacman.sprite.ghost;

import com.ahmedaraby.game.pacman.config.GhostModeActivePeriodsConf;
import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.game.pacman.event.Event;
import com.ahmedaraby.game.pacman.ghostmode.common.Eaten;
import com.ahmedaraby.game.pacman.ghostmode.common.Frightened;
import com.ahmedaraby.game.pacman.ghostmode.pinky.PinkyChaser;
import com.ahmedaraby.game.pacman.ghostmode.pinky.PinkyScattered;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.jengine.sprite.SpriteRegistry;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class Pinky extends Ghost {

    public Pinky(GameState gameState, ConfigsEx configs, SpriteRegistry<String, Image> spriteRegistry) {
        super(gameState, configs, SpriteE.GHOST, -1, -1, DirectionsE.STILL);
        scattered = new PinkyScattered(this, gameState, configs, spriteRegistry, GhostModeActivePeriodsConf.LEVEL_1_SCATTER_ACTIVE_PERIODS);
        chaser = new PinkyChaser(this, gameState, configs, spriteRegistry, GhostModeActivePeriodsConf.LEVEL_1_CHASE_ACTIVE_PERIODS);
        frightened = new Frightened(this, gameState, configs, spriteRegistry, GhostModeActivePeriodsConf.ALL_LEVELS_FRIGHTENED_MODE_ACTIVE_PERIODS);
        eaten = new Eaten(this, gameState, configs, spriteRegistry);

        this.activeMode = scattered;
        scattered.enter();
    }

    @Override
    public void init() {
        super.init();
        // put the ghost at it's position in the ghost house
        final double col = gameState.getGhostHouseS().getCol() + DimensionsC.MAZE_CELL_SIZE_PIXELS * 3;
        final double row = gameState.getGhostHouseS().getERow() - DimensionsC.MAZE_CELL_SIZE_PIXELS;
        setCol(col);
        setRow(row);
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
