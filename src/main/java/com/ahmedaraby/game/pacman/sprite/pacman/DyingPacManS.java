package com.ahmedaraby.game.pacman.sprite.pacman;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.constant.ColorC;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.model.event.Event;
import com.ahmedaraby.game.pacman.model.exception.GameStartException;
import com.ahmedaraby.game.pacman.sound.SoundPlayer;
import com.ahmedaraby.game.pacman.sprite.MovingSprite;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;

public class DyingPacManS extends MovingSprite {
    // mouth geometric information
    private double arcStartAngle;
    private double arcExtent;
    private final double originalArcExtent;
    private final SoundPlayer soundPlayer;

    public DyingPacManS(PacMan pacMan, GameState gameState, SoundPlayer soundPlayer, ConfigsEx configs) {
        super(gameState, configs, null, SpriteE.DEMOLISHING_PAC_MAN,
                pacMan.getTopLeftCorner(), pacMan.getWidth(), pacMan.getHeight(), pacMan.getDirV());
        this.soundPlayer = soundPlayer;

        // mouth geometric information
        arcStartAngle = pacMan.getArcStartAngle();
        arcExtent = pacMan.getArcExtent();
        originalArcExtent = pacMan.getArcExtent();
        soundPlayer.play(configs.PAC_MAN_DYING_CLIP_PATH());
    }

    @Override
    public void render(Canvas canvas) {
        final GraphicsContext con = canvas.getGraphicsContext2D();
        con.setFill(ColorC.PAC_MAN_COLOR);
        con.fillArc(getCol(), getRow(), configs.PACMAN_DIAMETER(), configs.PACMAN_DIAMETER(),
                arcStartAngle, arcExtent, ArcType.ROUND);
    }

    @Override
    public void move(Event event) {
        final double elapsedTime = Math.abs(gameState.getCurrFrameStartedAt() - gameState.getPrevFrameEndedAt()) / 1_000_000_000.0;
        final double elapsed2DeathTimeRatio = elapsedTime / configs.PACMAN_DEATH_PERIOD_SEC();
        final double demolishedDeg = elapsed2DeathTimeRatio * originalArcExtent;
        arcStartAngle += demolishedDeg / 2;
        arcExtent -= demolishedDeg;
        if (arcExtent <= 0) {
            throw new GameStartException();
        }
    }
}
