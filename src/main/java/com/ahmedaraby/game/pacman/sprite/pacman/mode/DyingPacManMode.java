package com.ahmedaraby.game.pacman.sprite.pacman.mode;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.constant.ColorC;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.model.event.Event;
import com.ahmedaraby.game.pacman.model.event.EventType;
import com.ahmedaraby.game.pacman.sprite.pacman.PacMan;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;

import javafx.scene.canvas.Canvas;


public class DyingPacManMode extends PacManMode {
    private final double originalArcExtent;

    public DyingPacManMode(PacMan pacMan, GameState gameState, ConfigsEx configs) {
        super(pacMan, gameState, configs);
        originalArcExtent = pacMan.getArcExtent();
        gameState.getSoundPlayer().play(configs.PAC_MAN_DYING_CLIP_PATH());
    }

    @Override
    public void render(Canvas canvas) {
        final GraphicsContext con = canvas.getGraphicsContext2D();
        con.setFill(ColorC.PAC_MAN_COLOR);
        con.fillArc(pacMan.getCol(), pacMan.getRow(), configs.PACMAN_DIAMETER(), configs.PACMAN_DIAMETER(),
                pacMan.getArcStartAngle(), pacMan.getArcExtent(), ArcType.ROUND);
    }

    @Override
    public void update(Event<EventType> event) {
        // calculate the new mouth geometry
        final double elapsedTime = Math.abs(gameState.getCurrFrameStartedAt() - gameState.getPrevFrameEndedAt()) / 1_000_000_000.0;
        final double elapsed2DeathTimeRatio = elapsedTime / configs.PACMAN_DEATH_PERIOD_SEC();
        final double demolishedDeg = elapsed2DeathTimeRatio * originalArcExtent;

        // set the new mouth geometry
        final double newArcStartAngle = pacMan.getArcStartAngle() + demolishedDeg / 2;
        final double newArcExtent = pacMan.getArcExtent() - demolishedDeg;
        pacMan.setArcStartAngle(newArcStartAngle);
        pacMan.setArcExtent(newArcExtent);
    }

    @Override
    public boolean ended() {
        return pacMan.getArcExtent() < 0;
    }
}
