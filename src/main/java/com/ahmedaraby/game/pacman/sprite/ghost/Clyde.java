package com.ahmedaraby.game.pacman.sprite.ghost;

import com.ahmedaraby.game.pacman.config.GhostModeActivePeriodsConf;
import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
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
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.playground.GhostHouseS;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.sprite.SpriteRegistry;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class Clyde extends Ghost {

    private final ClydeScaredChaser scaredChaser;
    private final ShortestPathNavigator navigator;

    public Clyde(GameState gameState, SpriteRegistry<String, Image> spriteRegistry) {
        super(gameState, SpriteE.GHOST, -1, -1, DirectionsE.STILL);

        scattered = new ClydeScattered(this, gameState, spriteRegistry,
                GhostModeActivePeriodsConf.LEVEL_1_SCATTER_ACTIVE_PERIODS);
        chaser = new ClydeChaser(this, gameState, spriteRegistry,
                GhostModeActivePeriodsConf.LEVEL_1_CHASE_ACTIVE_PERIODS);
        frightened = new Frightened(this, gameState, spriteRegistry,
                GhostModeActivePeriodsConf.ALL_LEVELS_FRIGHTENED_MODE_ACTIVE_PERIODS);
        eaten = new Eaten(this, gameState, spriteRegistry);
        scaredChaser = new ClydeScaredChaser(this, gameState, spriteRegistry);

        navigator = new ShortestPathNavigator();

        scattered.enter();
        activeMode = scattered;
    }

    @Override
    public void init() {
        super.init();
        // place yourself in the ghost house
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

    private void scaredChaserTransition(Event event) {
        if (event != null && EventType.PAC_MAN_SUPER_SUGAR_COLLISION.equals(event.getType())) {
            chaser.pause();
            previousMode = chaser;

            frightened.enter();
            activeMode = frightened;
        } else {
            final Coordinate pacManCord = gameState.getPacMan().getTopLeftCorner();
            final double distToPacManInPixels = navigator.calcDist(getTopLeftCorner(), pacManCord);
            // [TODO] provide the number 8 as a configuration
            if (distToPacManInPixels >= 8 * DimensionsC.MAZE_CELL_SIZE_PIXELS) {
                chaser.enter();
                activeMode = chaser;
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

        final Coordinate pacManCord = gameState.getPacMan().getTopLeftCorner();
        final double distToPacManInPixels = navigator.calcDist(getTopLeftCorner(), pacManCord);
        // [TODO] provide the number 8 as a configuration
        if (distToPacManInPixels < 8 * DimensionsC.MAZE_CELL_SIZE_PIXELS) {
            activeMode = scaredChaser;
        }
    }
}
