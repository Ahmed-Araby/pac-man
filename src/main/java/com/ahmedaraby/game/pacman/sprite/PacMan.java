package com.ahmedaraby.game.pacman.sprite;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.game.pacman.event.EventType;
import com.ahmedaraby.game.pacman.util.pacman.TurnBuffer;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.entity.Rectangle;
import com.ahmedaraby.game.pacman.event.Event;
import com.ahmedaraby.jengine.entity.Vector;
import com.ahmedaraby.jengine.event.Subscriber;
import com.ahmedaraby.game.pacman.event.movement.PacManMovementAttemptApprovedEvent;
import com.ahmedaraby.game.pacman.event.movement.PacManMovementAttemptDeniedEvent;
import com.ahmedaraby.game.pacman.event.movement.PacManMovementRequestEvent;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.util.pacman.PacManGraphicsUtil;
import com.ahmedaraby.game.pacman.util.pacman.PacManMouthAnimationTracker;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.playground.Playground;

public class PacMan extends MovingSprite implements Subscriber<EventType> {

    private final TurnBuffer turnBuffer;
    private final PacManMouthAnimationTracker mouthAnimationTracker;
    private Vector dir; // [TODO] move this to MovingSprite later

    public PacMan(GameState gameState, ConfigsEx configs) {
        super(gameState, configs, SpriteE.PAC_MAN, null,
                configs.PACMAN_DIAMETER(),
                configs.PACMAN_DIAMETER(),
                DirectionsE.STILL
        );

        dir = Vector.STILL;

        final Coordinate emptyCellPos = Playground.getEmptyMazePosition();
        setTopLeftCorner(emptyCellPos);

        this.turnBuffer = new TurnBuffer(configs.PLAYGROUND_CELL_SIZE() * 2);
        this.mouthAnimationTracker = new PacManMouthAnimationTracker(
               configs.PACMAN_MOUTH_OPEN_DISTANCE(),
                configs.PACMAN_MOUTH_CLOSED_DISTANCE());
    }

    @Override
    public double getSpeed() {
        return configs.PACMAN_SPEED();
    }

    @Override
    public void render(Canvas canvas) {
        final GraphicsContext con = canvas.getGraphicsContext2D();

        final double col = getCol();
        final double row = getRow();

        if (mouthAnimationTracker.isClosed()) {
            PacManGraphicsUtil.drawClosedMousePacMan(con, col, row);
        } else if (dir == Vector.RIGHT) {
            PacManGraphicsUtil.drawRightOpenMousePacMan(con, col, row);
        } else if (dir == Vector.UP) {
            PacManGraphicsUtil.drawUpOpenMousePacMan(con, col, row);
        } else if (dir == Vector.LEFT) {
            PacManGraphicsUtil.drawLeftOpenMousePacMan(con, col, row);
        } else if (dir == Vector.DOWN) {
            PacManGraphicsUtil.drawDownOpenMousePacMan(con, col, row);
        } else if (dir == Vector.STILL) {
            PacManGraphicsUtil.drawClosedMousePacMan(con, col, row);
        }
    }


    @Override
    public void move(Event event) {
        if (event != null) {
            // movement attempt made by user
            attemptMovement((PacManMovementRequestEvent)event);
            return;
        }

        // buffered movement attempt
        if (!turnBuffer.isEmpty()) {
            boolean moved = attemptMovement(new PacManMovementRequestEvent(turnBuffer.getDir(), turnBuffer));
            if (turnBuffer.exceededBufferDist()) {
                turnBuffer.clear();
            }
            if (moved) {
                return;
            }
        }
        
        // automated movement attempt
        attemptMovement(new PacManMovementRequestEvent(dir, this));

    }

    private boolean attemptMovement(PacManMovementRequestEvent event) {
        if (Vector.STILL == event.getDir()) {
            dir = event.getDir();
            return false;
        }

        // frame independent calculation of the next coordinates
        final double originalStride = calculateStride();
        final double calibratedStride = calibrateStride(originalStride, event.getDir());
        if (calibratedStride == 0) {
            return false;
        }

        final double newCol = getCol() + event.getDir().getX() * calibratedStride;
        final double newRow = getRow() + event.getDir().getY() * calibratedStride;
        final Coordinate nextCord = new Coordinate(newRow, newCol);

        if (!isValidPosition(nextCord)) {
            final PacManMovementAttemptDeniedEvent deniedEvent = new PacManMovementAttemptDeniedEvent(nextCord, event.getDir(), event.getSource());
            handleDeniedMovementAttempt(deniedEvent);
            return false;
        } else {
            final PacManMovementAttemptApprovedEvent approvedEvent = new PacManMovementAttemptApprovedEvent(
                    getTopLeftCorner(), calibratedStride, nextCord, event.getDir(), event.getSource()
            );
            handleApprovedMovementAttempt(approvedEvent);
            return true;
        }
    }

    private boolean isValidPosition(Coordinate nextCord) {
        // only 1 dimension can have fraction at a time.
        final Coordinate nextCeildCord = new Coordinate(Math.ceil(nextCord.getRow()), Math.ceil(nextCord.getCol()));
        final Rectangle nextCeildPacManRect = new Rectangle(nextCeildCord, getWidth(), getHeight());
        return nextCeildPacManRect.within(gameState.getMaze().getRect()) && !isCollidingWithWallOrGhostHWall(nextCeildCord);
    }

    private void handleApprovedMovementAttempt(PacManMovementAttemptApprovedEvent event) {
        // apply move
        setRow(event.getRequestedPacManCanvasRectTopLeftCorner().getRow());
        setCol(event.getRequestedPacManCanvasRectTopLeftCorner().getCol());
        dir = event.getRequestedDir();


        mouthAnimationTracker.stride(event.getCalibratedStride());

        if (event.getMovementAttemptSource() instanceof Scene
                || event.getMovementAttemptSource() instanceof TurnBuffer) {
            // user input or turn buffer automated move
            turnBuffer.clear();
        } else if (event.getMovementAttemptSource() instanceof PacMan) {
            // automated straight line movement
            turnBuffer.stride(event.getCalibratedStride());
        }
    }

    private void handleDeniedMovementAttempt(PacManMovementAttemptDeniedEvent event) {
        if (!(event.getMovementAttemptSource() instanceof Scene)) {
            // do nothing for denied automated movements
            return;
        }

        // buffer denied user movement
        if(dir != event.getRequestedDir()) {
            turnBuffer.buffer(event.getRequestedDir());
        }
    }

    @Override
    public void update(Event<EventType> event) {
        switch (event.getType()) {
            case PAC_MAN_MOVEMENT_REQUEST:
                move(event);
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }
}
