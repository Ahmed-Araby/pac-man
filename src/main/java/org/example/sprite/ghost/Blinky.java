package org.example.sprite.ghost;

import javafx.scene.canvas.Canvas;
import org.example.config.GhostModeActivePeriodsConf;
import org.example.constant.*;
import org.example.entity.CanvasCoordinate;
import org.example.event.Event;
import org.example.event.EventType;
import org.example.event.Subscriber;
import org.example.ghostmode.*;
import org.example.ghostmode.blinky.BlinkyChaser;
import org.example.ghostmode.Frightened;
import org.example.ghostmode.blinky.BlinkyScattered;
import org.example.ghostmode.timer.ChaseScatterTimer;
import org.example.ghostmode.timer.RealTimer;
import org.example.model.GameState;

public class Blinky extends Ghost implements Subscriber {
    // [TODO] rename modes
    // ghost modes
    private final GhostMode blinkyChaser;
    private final GhostMode blinkyScattered;
    private final GhostMode frightened;
    private final GhostMode eaten;

    private GhostMode activeMode;
    private GhostMode previousMode;
    private final ChaseScatterTimer chaseScatterTimer;
    private final RealTimer realTimer;

    private final GameState gameState;
    @Override
    public CanvasCoordinate getPacManCanvasCord() {
        return gameState.getPacMan().getCurrCanvasCord();
    }

    public Blinky(GameState gameState) {
        super(SpriteE.GHOST, 0, 0, DirectionsE.STILL);

        this.chaseScatterTimer = new ChaseScatterTimer();
        this.realTimer = new RealTimer();

        // ghost modes
        this.blinkyChaser = new BlinkyChaser(GhostModeActivePeriodsConf.LEVEL_1_CHASE_ACTIVE_PERIODS);
        this.blinkyScattered = new BlinkyScattered(GhostModeActivePeriodsConf.LEVEL_1_SCATTER_ACTIVE_PERIODS);
        this.frightened = new Frightened();
        this.eaten = new Eaten(gameState);
        this.activeMode = blinkyScattered;

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

    private void transitionMode(Event event) {
        GhostMode nextMode = null;

        if (activeMode instanceof Chaser) {
            if (event != null && EventType.PAC_MAN_SUPER_SUGAR_COLLISION.equals(event.getType())) {
                previousMode = activeMode;
                nextMode = frightened;
                final float activePeriodSeconds = nextMode.getActivePeriodSeconds();
                realTimer.start(activePeriodSeconds);
            }
            else if(chaseScatterTimer.up()) {
                nextMode = blinkyScattered;
            }
        } else if(activeMode instanceof Scattered) {
            if (event != null && EventType.PAC_MAN_SUPER_SUGAR_COLLISION.equals(event.getType())) {
                previousMode = activeMode;
                nextMode = frightened;
                final float activePeriodSeconds = nextMode.getActivePeriodSeconds();
                realTimer.start(activePeriodSeconds);
            }
            else if(chaseScatterTimer.up()){
                nextMode = blinkyChaser;
            }
        } else if (activeMode instanceof Frightened) {
            if (event != null && EventType.PAC_MAN_GHOST_COLLISION.equals(event.getType())) {
                nextMode = eaten;
            } else if (realTimer.up()) {
                nextMode = previousMode;
                previousMode = null;
            }
        } else if (activeMode instanceof Eaten) {
            if (activeMode.end(this)) {
                nextMode = blinkyChaser;
            }
        }

        if(nextMode != null) {
            activeMode = nextMode;
            activeMode.enter(this);
        }
    }

    public void update(Event event) {
        switch (event.getType()) {
            case PAC_MAN_SUPER_SUGAR_COLLISION, PAC_MAN_GHOST_COLLISION -> transitionMode(event);
            default -> throw new IllegalArgumentException();
        }
    }
}
