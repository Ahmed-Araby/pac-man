package org.example.sprite.ghost;

import javafx.scene.canvas.Canvas;
import lombok.Getter;
import lombok.Setter;
import org.example.constant.*;
import org.example.entity.CanvasCoordinate;
import org.example.event.Event;
import org.example.ghostmode.*;
import org.example.ghostmode.blinky.BlinkyChaser;
import org.example.ghostmode.blinky.BlinkyScattered;
import org.example.ghostmode.navigation.ShortestPathNavigator;
import org.example.ghostmode.timer.ChaseScatterTimer;
import org.example.sprite.PacMan;
import org.example.sprite.Sprite;


public class Blinky extends Ghost implements Sprite{

    @Getter
    @Setter
    private double canvasCol = 0, canvasRow = 0;

    @Getter
    @Setter
    private DirectionsE directionsE = DirectionsE.STILL;

    // ghost modes
    private final GhostMode blinkyChaser;
    private final GhostMode blinkyScattered;
    private GhostMode activeMode;

    private final ChaseScatterTimer chaseScatterTimer;

    // [TODO] find a better way to pass this information
    private final PacMan pacMan;
    @Override
    public CanvasCoordinate getPacManCanvasCord() {
        return pacMan.getCurrCanvasCord();
    }

    public Blinky(PacMan pacMan, ShortestPathNavigator chaseMode) {
        this.pacMan = pacMan;
        this.chaseScatterTimer = new ChaseScatterTimer();

        // ghost modes
        this.blinkyChaser = new BlinkyChaser();
        this.blinkyScattered = new BlinkyScattered();
        this.activeMode = blinkyScattered;
    }

    @Override
    public void render(Canvas canvas) {
        System.out.println("blinky: row = " + canvasRow + " , col = " + canvasCol + " , Dir = " + directionsE);
        activeMode.render(canvas, this);
    }

    @Override
    public void move(Event event) {
        transitionMode();
        activeMode.move(this);
    }

    private void transitionMode() {
        if (activeMode instanceof BlinkyChaser) {
            if(chaseScatterTimer.up()) {
                activeMode = blinkyScattered;
            }
        } else if(activeMode instanceof BlinkyScattered) {
            if(chaseScatterTimer.up()){
                activeMode = blinkyChaser;
            }
        }
    }
}
