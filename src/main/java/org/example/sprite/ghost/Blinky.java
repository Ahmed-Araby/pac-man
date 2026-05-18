package org.example.sprite.ghost;

import javafx.scene.canvas.Canvas;
import org.example.config.GhostModeActivePeriodsConf;
import org.example.constant.*;
import org.example.entity.CanvasCoordinate;
import org.example.event.Event;
import org.example.event.Subscriber;
import org.example.ghostmode.*;
import org.example.ghostmode.blinky.BlinkyChaser;
import org.example.ghostmode.Frightened;
import org.example.ghostmode.blinky.BlinkyScattered;
import org.example.ghostmode.timer.RealTimer;
import org.example.model.GameState;

public class Blinky extends Ghost implements Subscriber {
    // [TODO] rename modes
    // ghost modes


    private final GameState gameState;
    @Override
    public CanvasCoordinate getPacManCanvasCord() {
        return gameState.getPacMan().getCurrCanvasCord();
    }

    public Blinky(GameState gameState) {
        super(SpriteE.GHOST, 0, 0, DirectionsE.STILL);

        this.realTimer = new RealTimer();

        // ghost modes
        this.chaser = new BlinkyChaser(GhostModeActivePeriodsConf.LEVEL_1_CHASE_ACTIVE_PERIODS);
        this.scattered = new BlinkyScattered(GhostModeActivePeriodsConf.LEVEL_1_SCATTER_ACTIVE_PERIODS);
        this.frightened = new Frightened();
        this.eaten = new Eaten(gameState);

        scattered.enter();
        this.activeMode = scattered;

        this.gameState = gameState;
    }

    @Override
    public void render(Canvas canvas) {
        System.out.println("blinky: row = " + row + " , col = " + col + " , Dir = " + dir);
        activeMode.render(canvas, this);
    }

    @Override
    public void move(Event event) {
        transitionMode(event);
        activeMode.move(this);
    }

    public void update(Event event) {
        switch (event.getType()) {
            case PAC_MAN_SUPER_SUGAR_COLLISION, PAC_MAN_GHOST_COLLISION -> transitionMode(event);
            default -> throw new IllegalArgumentException();
        }
    }
}
