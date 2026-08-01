package com.ahmedaraby.game.pacman.sprite.pacman.mode;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.constant.ColorC;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.model.event.Event;
import com.ahmedaraby.game.pacman.model.event.EventType;
import com.ahmedaraby.game.pacman.sprite.pacman.PacMan;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.ArcType;


public class BurstingPacManMode extends PacManMode {

    private final MediaPlayer mediaPlayer;
    public BurstingPacManMode(PacMan pacMan, GameState gameState, ConfigsEx configs) {
        super(pacMan, gameState, configs);
        mediaPlayer = gameState.getSoundPlayer().repeat(configs.AFTER_PAC_MAN_DEATH_CLIP_PATH(), 2);
    }

    @Override
    public void render(Canvas canvas) {
        final GraphicsContext con = canvas.getGraphicsContext2D();

        int arcCount = configs.PAC_MAN_BURSTING_ARC_COUNT();
        int filledArcExtent = configs.PAC_MAN_BURSTING_ARC_EXTENT_DEG();
        int emptyArcExtent = (360 - arcCount * filledArcExtent) / arcCount;

        con.setFill(ColorC.PAC_MAN_COLOR);
        for (int i=0; i<arcCount; i++) {
            final int startAngle = i * filledArcExtent + i * emptyArcExtent;
            con.fillArc(pacMan.getCol(), pacMan.getRow(), configs.PACMAN_DIAMETER(), configs.PACMAN_DIAMETER(),
                    startAngle, filledArcExtent, ArcType.ROUND);
        }
    }

    @Override
    public void update(Event<EventType> event) {
        // do nothing
    }

    @Override
    public boolean ended() {
        return mediaPlayer.getCurrentCount() == 2;
    }


}
