package com.ahmedaraby.game.pacman.sprite.ghost;

import com.ahmedaraby.game.pacman.config.GhostModeActivePeriodsConf;
import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.game.pacman.event.Event;
import com.ahmedaraby.game.pacman.event.EventType;
import com.ahmedaraby.game.pacman.ghostmode.Chaser;
import com.ahmedaraby.game.pacman.ghostmode.clyde.ClydeChaser;
import com.ahmedaraby.game.pacman.ghostmode.clyde.ClydeScaredChaser;
import com.ahmedaraby.game.pacman.ghostmode.clyde.ClydeScattered;
import com.ahmedaraby.game.pacman.ghostmode.common.Eaten;
import com.ahmedaraby.game.pacman.ghostmode.common.Frightened;
import com.ahmedaraby.game.pacman.ghostmode.navigation.ShortestPathNavigator;
import com.ahmedaraby.game.pacman.ghostmode.navigation.StrideCalculator;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.playground.GhostHouseS;
import com.ahmedaraby.game.pacman.util.PlaygroundShortestPathNav;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.entity.Vector;
import com.ahmedaraby.jengine.sprite.SpriteRegistry;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class Clyde extends Ghost {

    private final ClydeScaredChaser scaredChaser;
    private final ShortestPathNavigator navigator;

    public Clyde(GameState gameState, ConfigsEx configs, StrideCalculator strideCalc,
                 SpriteRegistry<String, Image> spriteRegistry) {
        super(gameState, configs, strideCalc,
                SpriteE.GHOST, -1, -1, Vector.STILL);

        scattered = new ClydeScattered(this, gameState, configs, spriteRegistry,
                GhostModeActivePeriodsConf.LEVEL_1_SCATTER_ACTIVE_PERIODS);
        chaser = new ClydeChaser(this, gameState, configs, spriteRegistry,
                GhostModeActivePeriodsConf.LEVEL_1_CHASE_ACTIVE_PERIODS);
        frightened = new Frightened(this, gameState, configs, spriteRegistry,
                GhostModeActivePeriodsConf.ALL_LEVELS_FRIGHTENED_MODE_ACTIVE_PERIODS);
        eaten = new Eaten(this, gameState, configs, spriteRegistry);
        scaredChaser = new ClydeScaredChaser(this, gameState, configs, spriteRegistry);

        PlaygroundShortestPathNav playgroundShortestPathNav = new PlaygroundShortestPathNav();
        navigator = new ShortestPathNavigator(configs, playgroundShortestPathNav);

        scattered.enter();
        activeMode = scattered;
    }

    @Override
    public void init() {
        super.init();
        // place yourself in the ghost house
        final GhostHouseS ghostHouseS = gameState.getGhostHouseS();
        final double col = ghostHouseS.getCol() + 4 * configs.PLAYGROUND_CELL_SIZE();
        final double row = ghostHouseS.getERow() - configs.PLAYGROUND_CELL_SIZE();
        setTopLeftCorner(new Coordinate(row, col));
    }

    @Override
    public double getSpeed() {
        return configs.GHOST_CLYDE_SPEED();
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

    @Override
    protected void transitionMode(Event event) {
        if (activeMode instanceof ClydeScaredChaser) {
            scaredChaserTransition(event);
        } else if (activeMode instanceof Chaser) {
            chaserTransition(event);
        } else {
            super.transitionMode(event);
        }
    }


    // [TODO] debug, Clyde never leaves teh scared chaser mode
    private void scaredChaserTransition(Event event) {
        if (event != null && EventType.PAC_MAN_SUPER_SUGAR_COLLISION.equals(event.getType())) {
            chaser.pause();
            previousMode = chaser;

            frightened.enter();
            activeMode = frightened;
        } else {
            final double distToPacManInPixels = navigator.calcDist(this, gameState.getPacMan());
            // [TODO] provide the number 8 as a configuration
            if (distToPacManInPixels >= 11 * configs.PLAYGROUND_CELL_SIZE()) {
                activeMode = chaser;
            } else if (chaser.ended()) {
                super.chaserTransition(event);
            }
        }
    }

    @Override
    protected void chaserTransition(Event event) {
        final EventType eventType = event != null ? (EventType) event.getType() : null;
        if (EventType.PAC_MAN_SUPER_SUGAR_COLLISION.equals(eventType) || activeMode.ended()) {
            super.chaserTransition(event);
            return;
        }

        final double distToPacManInPixels = navigator.calcDist(this, gameState.getPacMan());
        // [TODO] provide the number 8 as a configuration
        if (distToPacManInPixels < 8 * configs.PLAYGROUND_CELL_SIZE()) {
            activeMode = scaredChaser;
        }
    }
}
