package com.ahmedaraby.game.pacman.sprite.ghost;

import com.ahmedaraby.game.pacman.config.GhostModeActivePeriodsConf;
import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.game.pacman.model.event.Event;
import com.ahmedaraby.game.pacman.ghostmode.common.Eaten;
import com.ahmedaraby.game.pacman.ghostmode.common.Frightened;
import com.ahmedaraby.game.pacman.ghostmode.navigation.StrideCalculator;
import com.ahmedaraby.game.pacman.ghostmode.pinky.PinkyChaser;
import com.ahmedaraby.game.pacman.ghostmode.pinky.PinkyScattered;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.jengine.entity.Vector;
import com.ahmedaraby.jengine.sprite.AssetRegistry;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class Pinky extends Ghost {

    public Pinky(GameState gameState, ConfigsEx configs, StrideCalculator strideCalc,
                 AssetRegistry<String, Image> assetRegistry) {
        super(gameState, configs, strideCalc,
                SpriteE.GHOST, -1, -1, Vector.STILL);
        scattered = new PinkyScattered(this, gameState, configs, assetRegistry, GhostModeActivePeriodsConf.LEVEL_1_SCATTER_ACTIVE_PERIODS);
        chaser = new PinkyChaser(this, gameState, configs, assetRegistry, GhostModeActivePeriodsConf.LEVEL_1_CHASE_ACTIVE_PERIODS);
        frightened = new Frightened(this, gameState, configs, assetRegistry, GhostModeActivePeriodsConf.ALL_LEVELS_FRIGHTENED_MODE_ACTIVE_PERIODS);
        eaten = new Eaten(this, gameState, configs, assetRegistry);

        this.activeMode = scattered;
        scattered.enter();
    }

    @Override
    public void init() {
        super.init();
        // put the ghost at it's position in the ghost house
        final double col = gameState.getGhostHouseS().getCol() + configs.PLAYGROUND_CELL_SIZE() * 3;
        final double row = gameState.getGhostHouseS().getERow() - configs.PLAYGROUND_CELL_SIZE();
        setCol(col);
        setRow(row);
    }

    @Override
    public double getSpeed() {
        return configs.GHOST_PINKY_SPEED();
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
