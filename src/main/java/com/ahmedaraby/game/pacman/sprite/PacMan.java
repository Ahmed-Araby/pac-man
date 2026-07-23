package com.ahmedaraby.game.pacman.sprite;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.game.pacman.event.EventType;
import com.ahmedaraby.game.pacman.ghostmode.navigation.StrideCalculator;
import com.ahmedaraby.game.pacman.util.pacman.TurnBuffer;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.game.pacman.event.Event;
import com.ahmedaraby.jengine.entity.Vector;
import com.ahmedaraby.jengine.event.Subscriber;
import com.ahmedaraby.game.pacman.event.movement.PacManMovementRequestEvent;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.util.pacman.PacManGraphicsUtil;
import com.ahmedaraby.game.pacman.util.pacman.PacManMouthAnimationTracker;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import com.ahmedaraby.game.pacman.playground.Playground;

public class PacMan extends MovingSprite implements Subscriber<EventType> {

    private final TurnBuffer turnBuffer;
    private final PacManMouthAnimationTracker mouthAnimationTracker;

    public PacMan(GameState gameState, ConfigsEx configs, StrideCalculator strideCalc) {
        super(gameState, configs, strideCalc,
                SpriteE.PAC_MAN, null,
                configs.PACMAN_DIAMETER(), configs.PACMAN_DIAMETER(),
                Vector.STILL
        );

        final Coordinate emptyCellPos = Playground.getEmptyMazePosition();
        setTopLeftCorner(emptyCellPos);

        this.turnBuffer = new TurnBuffer(configs.PLAYGROUND_CELL_SIZE() * 2);
        this.mouthAnimationTracker = new PacManMouthAnimationTracker(
               configs.PACMAN_MOUTH_OPEN_DISTANCE(),
                configs.PACMAN_MOUTH_CLOSED_DISTANCE());
    }

    @Override
    public void render(Canvas canvas) {
        final GraphicsContext con = canvas.getGraphicsContext2D();

        final double col = getCol();
        final double row = getRow();

        if (mouthAnimationTracker.isClosed()) {
            PacManGraphicsUtil.drawClosedMousePacMan(con, col, row);
        } else if (dirV == Vector.RIGHT) {
            PacManGraphicsUtil.drawRightOpenMousePacMan(con, col, row);
        } else if (dirV == Vector.UP) {
            PacManGraphicsUtil.drawUpOpenMousePacMan(con, col, row);
        } else if (dirV == Vector.LEFT) {
            PacManGraphicsUtil.drawLeftOpenMousePacMan(con, col, row);
        } else if (dirV == Vector.DOWN) {
            PacManGraphicsUtil.drawDownOpenMousePacMan(con, col, row);
        } else if (dirV == Vector.STILL) {
            PacManGraphicsUtil.drawClosedMousePacMan(con, col, row);
        }
    }


    @Override
    public void move(Event event) {
        System.out.println("pacManTopLeftCorner = " + topLeftCorner);
        if (event != null) {
            // movement attempt made by user
            if (((PacManMovementRequestEvent)event).getDir() != dirV) {
                attemptMovementInNewDir((PacManMovementRequestEvent) event);
            } else {
                attemptMovementInSameDir((PacManMovementRequestEvent) event);
            }
            return;
        }

        // automated and buffered movement attempt
        if (!turnBuffer.isEmpty()) {
            boolean moved = attemptMovementInNewDir(new PacManMovementRequestEvent(turnBuffer.getDir(), turnBuffer));
            if (turnBuffer.exceededBufferDist()) {
                turnBuffer.clear();
            }
            if (moved) {
                return;
            }
        }
        attemptMovementInSameDir(new PacManMovementRequestEvent(dirV, this));
    }

    @Override
    public double getSpeed() {
        return configs.PACMAN_SPEED();
    }

    private boolean attemptMovementInSameDir(PacManMovementRequestEvent event) {
        final boolean isPossible = isPossibleToMoveInSameDir();
        if (isPossible) {
            final double stride = calcCalibratedStride(event.getDir());
            move(stride, dirV);
            mouthAnimationTracker.stride(stride);
            updateTurnBuffer(event.getDir(), event.getSource());
        }
        return isPossible;
    }

    private boolean attemptMovementInNewDir(PacManMovementRequestEvent event) {
        if (Vector.STILL == event.getDir()) {
            dirV = event.getDir();
            return false;
        }

        final boolean isPossible = isPossibleToMoveInNewDir(event.getDir());

        if (isPossible) {
            final double stride = calcCalibratedStride(event.getDir());
            move(stride, event.getDir());
            mouthAnimationTracker.stride(stride);
            updateTurnBuffer(event.getDir(), event.getSource());
        } else if (event.getSource() instanceof Scene) {
            // buffer denied user movement
            turnBuffer.buffer(event.getDir());
        }
        return isPossible;
    }


    private void updateTurnBuffer(Vector dir, Object movementSource) {
        if (movementSource instanceof Scene || movementSource instanceof TurnBuffer) {
            // user input or turn buffer automated move
            turnBuffer.clear();
        } else if (movementSource instanceof PacMan) {
            // automated straight line movement
            final double stride = calcCalibratedStride(dir);
            turnBuffer.stride(stride);
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
