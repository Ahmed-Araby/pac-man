package com.ahmedaraby.game.pacman.sprite.ghost;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.game.pacman.event.Event;
import com.ahmedaraby.game.pacman.ghostmode.common.Eaten;
import com.ahmedaraby.game.pacman.ghostmode.common.Frightened;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.playground.GhostHouseS;
import com.ahmedaraby.jengine.sprite.SpriteRegistry;
import javafx.scene.canvas.Canvas;
import com.ahmedaraby.game.pacman.config.GhostModeActivePeriodsConf;
import com.ahmedaraby.game.pacman.ghostmode.Inky.InkyChaser;
import com.ahmedaraby.game.pacman.ghostmode.Inky.InkyScattered;
import javafx.scene.image.Image;

public class Inky extends Ghost {


    public Inky(GameState gameState, ConfigsEx configs, SpriteRegistry<String, Image> spriteRegistry) {
        super(gameState, configs, SpriteE.GHOST , 0, 0, DirectionsE.STILL);
        scattered = new InkyScattered(this, gameState, configs, spriteRegistry, GhostModeActivePeriodsConf.LEVEL_1_SCATTER_ACTIVE_PERIODS);
        chaser = new InkyChaser(this, gameState, configs, spriteRegistry, GhostModeActivePeriodsConf.LEVEL_1_CHASE_ACTIVE_PERIODS);
        frightened = new Frightened(this, gameState, configs, spriteRegistry, GhostModeActivePeriodsConf.ALL_LEVELS_FRIGHTENED_MODE_ACTIVE_PERIODS);
        eaten = new Eaten(this, gameState, configs, spriteRegistry);

        this.activeMode = scattered;
        scattered.enter();
    }

    @Override
    public void init() {
        super.init();
        final GhostHouseS ghostHouseS = gameState.getGhostHouseS();
        final double col = ghostHouseS.getCol() + 2 * configs.PLAYGROUND_CELL_SIZE();
        final double row = ghostHouseS.getERow() - configs.PLAYGROUND_CELL_SIZE();
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
