package com.ahmedaraby.game.pacman.sprite;

import com.ahmedaraby.game.pacman.config.Configs;
import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.game.pacman.event.EventType;
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
import com.ahmedaraby.game.pacman.util.pacman.PixelStrideTracker;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.playground.Playground;
import com.ahmedaraby.game.pacman.util.pacman.TurnBuffer;

/**
 * notes:
 * - PacManMovementAttemptEvents has to be processed sequentially, otherwise some user inputs or buffered turns can be lost
 */
public class PacMan extends MovingSprite implements Subscriber<EventType> {

    private final TurnBuffer turnBuffer;
    private final PixelStrideTracker closedMousePixelStrideTracker;
    private Vector dir; // [TODO] move this to MovingSprite later

    public PacMan(GameState gameState) {
        super(gameState, SpriteE.PAC_MAN, null, DimensionsC.PAC_MAN_DIAMETER_PIXELS, DimensionsC.PAC_MAN_DIAMETER_PIXELS, DirectionsE.STILL);
        dir = Vector.STILL;

        final Coordinate emptyCellPos = Playground.getEmptyMazePosition();
        setTopLeftCorner(emptyCellPos);

        this.turnBuffer = new TurnBuffer();
        this.closedMousePixelStrideTracker = new PixelStrideTracker(DimensionsC.PAC_MAN_CLOSED_MOUSE_DISTANCE_PIXELS,
                DimensionsC.PAC_MAN_CLOSED_MOUSE_DISTANCE_PIXELS + DimensionsC.PAC_MAN_OPEN_MOUSE_DISTANCE_PIXELS // just the same as DimensionsC.PAC_MAN_COMPLETE_MOUSE_MOVEMENT_DISTANCE_PIXELS
        );
    }

    @Override
    public void render(Canvas canvas) {
        final GraphicsContext con = canvas.getGraphicsContext2D();

        final double col = getCol();
        final double row = getRow();

        if (!closedMousePixelStrideTracker.isDesiredPixelStrideAchieved()) {
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
        if (event == null) {
            attemptMovement(new PacManMovementRequestEvent(dir, this));
        } else {
            attemptMovement((PacManMovementRequestEvent)event);
        }
    }

    private void attemptMovement(PacManMovementRequestEvent event) {
        if (Vector.STILL == event.getDir()) {
            dir = event.getDir();
            return;
        }

        final double newCol = getCol() + event.getDir().getX() * DimensionsC.PAC_MAN_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_STRIDE;
        final double newRow = getRow() + event.getDir().getY() * DimensionsC.PAC_MAN_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_STRIDE;

        final Coordinate nextCord = new Coordinate(newRow, newCol);
        final Rectangle virtualPacManRect = new Rectangle(nextCord, DimensionsC.PAC_MAN_DIAMETER_PIXELS, DimensionsC.PAC_MAN_DIAMETER_PIXELS);

        if (!virtualPacManRect.within(gameState.getMaze().getRect())
                || isCollidingWithWallOrGhostHWall(nextCord)
        ) {
            final PacManMovementAttemptDeniedEvent deniedEvent = new PacManMovementAttemptDeniedEvent(nextCord, event.getDir(), event.getSource());
            handleDeniedMovementAttempt(deniedEvent);
            return;
        } else {
            final PacManMovementAttemptApprovedEvent approvedEvent = new PacManMovementAttemptApprovedEvent(
                    getTopLeftCorner(), nextCord, event.getDir(), event.getSource()
            );
            handleApprovedMovementAttempt(approvedEvent);
            // pacman animation related
            closedMousePixelStrideTracker.stride(DimensionsC.PAC_MAN_COMPLETE_MOUSE_MOVEMENT_DISTANCE_PIXELS / Configs.FRAMES_PER_SEC_FOR_PAC_MAN_MOUSE_OPEN_CLOSED_ANIMATION);
            if (closedMousePixelStrideTracker.isRestPixelStrideAchieved()) {
                closedMousePixelStrideTracker.reset();
            }
        }
    }

    private void handleApprovedMovementAttempt(PacManMovementAttemptApprovedEvent event) {
        System.out.println("approved move");
        setRow(event.getRequestedPacManCanvasRectTopLeftCorner().getRow());
        setCol(event.getRequestedPacManCanvasRectTopLeftCorner().getCol());
        dir = event.getRequestedDir();

        if (event.getMovementAttemptSource() instanceof Scene || event.getMovementAttemptSource() instanceof TurnBuffer) {
            // user input or turn buffer automated move
            this.turnBuffer.discardTurnBuffer();
        } else if (event.getMovementAttemptSource() instanceof PacMan) {
            // automated straight line movement
            final Rectangle pacManCanvasRectangle = new Rectangle(event.getRequestedPacManCanvasRectTopLeftCorner(), DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS);
            if (turnBuffer.isThereBufferedTurn(pacManCanvasRectangle, event.getRequestedDir())) {
                attemptMovement(turnBuffer.getBufferedPacManAutomatedMovementRequest());
            }
        }
    }

    private void handleDeniedMovementAttempt(PacManMovementAttemptDeniedEvent event) {
        System.out.println("denied move");
        if (!(event.getMovementAttemptSource() instanceof Scene)) {
            // do nothing for denied automated movements
            return;
        }

        // buffer denied user movement
        if(turnBuffer.isBlockedTurn(dir, event.getRequestedDir())) {
            final Rectangle pacManCanvasRectangle = new Rectangle(new Coordinate(getRow(), getCol()), DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS);
            turnBuffer.bufferTurn(event.getRequestedDir(), pacManCanvasRectangle);
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
