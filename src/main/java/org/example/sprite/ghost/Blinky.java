package org.example.sprite.ghost;

import javafx.scene.canvas.Canvas;
import lombok.Getter;
import lombok.Setter;
import org.example.constant.*;
import org.example.entity.CanvasCoordinate;
import org.example.event.Event;
import org.example.event.EventType;
import org.example.event.Subscriber;
import org.example.ghostmode.*;
import org.example.ghostmode.blinky.BlinkyChaser;
import org.example.ghostmode.blinky.BlinkyFrightened;
import org.example.ghostmode.blinky.BlinkyScattered;
import org.example.ghostmode.navigation.ShortestPathNavigator;
import org.example.ghostmode.timer.ChaseScatterTimer;
import org.example.ghostmode.timer.RealTimer;
import org.example.sprite.PacMan;
import org.example.sprite.Sprite;


public class Blinky extends Ghost implements Sprite, Subscriber {

    @Getter
    @Setter
    private double canvasCol = 0, canvasRow = 0;

    @Getter
    @Setter
    private DirectionsE directionsE = DirectionsE.STILL;

    // ghost modes
    private final GhostMode blinkyChaser;
    private final GhostMode blinkyScattered;
    private final GhostMode frightened;
    private GhostMode activeMode;
    private GhostMode previousMode;

    private final ChaseScatterTimer chaseScatterTimer;
    private final RealTimer realTimer;

    // [TODO] find a better way to pass this information
    private final PacMan pacMan;
    @Override
    public CanvasCoordinate getPacManCanvasCord() {
        return pacMan.getCurrCanvasCord();
    }

    public Blinky(PacMan pacMan, ShortestPathNavigator chaseMode) {
        this.pacMan = pacMan;
        this.chaseScatterTimer = new ChaseScatterTimer();
        this.realTimer = new RealTimer();

        // ghost modes
        this.blinkyChaser = new BlinkyChaser();
        this.blinkyScattered = new BlinkyScattered();
        this.frightened = new BlinkyFrightened();
        this.activeMode = blinkyScattered;
    }

    @Override
    public void render(Canvas canvas) {
        System.out.println("blinky: row = " + canvasRow + " , col = " + canvasCol + " , Dir = " + directionsE);
        activeMode.render(canvas, this);
    }

    @Override
    public void move(Event event) {
        transitionMode(event);
        activeMode.move(this);
    }

    private void transitionMode(Event event) {
        if (activeMode instanceof BlinkyChaser) {
            if (event != null && EventType.PAC_MAN_SUPER_SUGAR_COLLISION.equals(event.getType())) {
                previousMode = activeMode;
                activeMode = frightened;
                final float activePeriodSeconds = activeMode.getActivePeriodSeconds();
                realTimer.start(activePeriodSeconds);
            }
            else if(chaseScatterTimer.up()) {
                activeMode = blinkyScattered;
            }
        }

        else if(activeMode instanceof BlinkyScattered) {
            if (event != null && EventType.PAC_MAN_SUPER_SUGAR_COLLISION.equals(event.getType())) {
                previousMode = activeMode;
                activeMode = frightened;
                final float activePeriodSeconds = activeMode.getActivePeriodSeconds();
                realTimer.start(activePeriodSeconds);
            }
            if(chaseScatterTimer.up()){
                activeMode = blinkyChaser;
            }
        }

        else if (activeMode instanceof BlinkyFrightened) {
            if (realTimer.up()) {
                activeMode = previousMode;
                previousMode = null;
            }
        }
    }

    public void update(Event event) {
        switch (event.getType()) {
            case PAC_MAN_SUPER_SUGAR_COLLISION -> transitionMode(event);
            default -> throw new IllegalArgumentException();
        }
    }
}
