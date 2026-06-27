package com.ahmedaraby.game.pacman.sprite.ghost;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.playground.GhostHouseS;
import com.ahmedaraby.jengine.sprite.SpriteRegistry;
import javafx.scene.canvas.Canvas;
import com.ahmedaraby.game.pacman.config.GhostModeActivePeriodsConf;
import com.ahmedaraby.game.pacman.event.Event;
import com.ahmedaraby.game.pacman.ghostmode.blinky.BlinkyChaser;
import com.ahmedaraby.game.pacman.ghostmode.common.Eaten;
import com.ahmedaraby.game.pacman.ghostmode.common.Frightened;
import com.ahmedaraby.game.pacman.ghostmode.blinky.BlinkyScattered;
import javafx.scene.image.Image;

public class Blinky extends Ghost {

    public Blinky(GameState gameState, ConfigsEx configs, SpriteRegistry<String, Image> spriteRegistry) {
        super(gameState, configs, SpriteE.GHOST, 0, 0, DirectionsE.STILL);

        // ghost modes
        this.chaser = new BlinkyChaser(this, gameState, configs, spriteRegistry, GhostModeActivePeriodsConf.LEVEL_1_CHASE_ACTIVE_PERIODS);
        this.scattered = new BlinkyScattered(this, gameState, configs, spriteRegistry, GhostModeActivePeriodsConf.LEVEL_1_SCATTER_ACTIVE_PERIODS);
        this.frightened = new Frightened(this, gameState, configs, spriteRegistry, GhostModeActivePeriodsConf.ALL_LEVELS_FRIGHTENED_MODE_ACTIVE_PERIODS);
        this.eaten = new Eaten(this, gameState, configs, spriteRegistry);

        scattered.enter();
        this.activeMode = scattered;
    }

    @Override
    public void init() {
        super.init();
        final GhostHouseS ghostHouseS = gameState.getGhostHouseS();
        final double col = ghostHouseS.getCol() + configs.PLAYGROUND_CELL_SIZE();
        final double row = ghostHouseS.getERow() - configs.PLAYGROUND_CELL_SIZE();
        setCol(col);
        setRow(row);
    }

    @Override
    public void render(Canvas canvas) {
        activeMode.render(canvas);
    }

    @Override
    public void move(Event event) {
        transitionMode(event);
        activeMode.move();
    }
}
