package com.ahmedaraby.game.pacman.sprite.pacman;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.constant.ColorC;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.game.pacman.ghostmode.Chaser;
import com.ahmedaraby.game.pacman.ghostmode.Scattered;
import com.ahmedaraby.game.pacman.model.event.EventType;
import com.ahmedaraby.game.pacman.ghostmode.navigation.StrideCalculator;
import com.ahmedaraby.game.pacman.model.event.collision.PacMan2GhostCollisionEvent;
import com.ahmedaraby.game.pacman.model.exception.GameOverException;
import com.ahmedaraby.game.pacman.model.exception.SceneTransitionException;
import com.ahmedaraby.game.pacman.sprite.MovingSprite;
import com.ahmedaraby.game.pacman.util.pacman.TurnBuffer;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.game.pacman.model.event.Event;
import com.ahmedaraby.jengine.entity.Vector;
import com.ahmedaraby.jengine.event.Subscriber;
import com.ahmedaraby.game.pacman.model.event.movement.PacManMovementRequestEvent;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.util.pacman.PacManMouthAnimationTracker;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import com.ahmedaraby.game.pacman.playground.Playground;
import javafx.scene.shape.ArcType;

import java.util.HashMap;
import java.util.Map;

public class PacMan extends MovingSprite implements Subscriber<EventType> {

    private final TurnBuffer turnBuffer;
    private final PacManMouthAnimationTracker mouthAnimationTracker;

    // pacman mouse geometric information
    private double arcStartAngle;
    private double arcExtent;
    private final Map<Vector, Integer> openMouthStartAngleByDir = new HashMap<>();

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

        arcStartAngle = configs.PACMAN_MOUTH_CLOSED_ARC_START_ANGLE_DEG();
        arcExtent = configs.PACMAN_MOUTH_CLOSED_ARC_EXTENT_DEG();

        openMouthStartAngleByDir.put(Vector.RIGHT, configs.PACMAN_MOUTH_OPEN_RIGHT_START_ANGLE());
        openMouthStartAngleByDir.put(Vector.UP, configs.PACMAN_MOUTH_OPEN_UP_START_ANGLE());
        openMouthStartAngleByDir.put(Vector.DOWN, configs.PACMAN_MOUTH_OPEN_DOWN_START_ANGLE());
        openMouthStartAngleByDir.put(Vector.LEFT, configs.PACMAN_MOUTH_OPEN_LEFT_START_ANGLE());
    }

    @Override
    public void render(Canvas canvas) {
        final GraphicsContext con = canvas.getGraphicsContext2D();

        // calculate effective angles
        double effectiveArcStartAngle = arcStartAngle;
        double effectiveArcExtent = arcExtent;
        if (mouthAnimationTracker.isClosed()) {
            effectiveArcStartAngle = configs.PACMAN_MOUTH_CLOSED_ARC_START_ANGLE_DEG();
            effectiveArcExtent = configs.PACMAN_MOUTH_CLOSED_ARC_EXTENT_DEG();
        }

        // render
        con.setFill(ColorC.PAC_MAN_COLOR);
        con.fillArc(getCol(), getRow(), configs.PACMAN_DIAMETER(), configs.PACMAN_DIAMETER(),
                effectiveArcStartAngle, effectiveArcExtent, ArcType.ROUND);
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
            if (moved || turnBuffer.exceededBufferDist()) {
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
            updateTurnBuffer(event.getDir(), event.getSource());

            mouthAnimationTracker.stride(stride);
            arcStartAngle = openMouthStartAngleByDir.get(event.getDir());
            arcExtent = configs.PACMAN_MOUTH_OPEN_ARC_EXTENT_DEG();
        } else if (event.getSource() instanceof Scene) {
            // buffer denied user movement
            turnBuffer.buffer(event.getDir());
        }
        return isPossible;
    }


    private void updateTurnBuffer(Vector dir, Object movementSource) {
        if (movementSource instanceof Scene || movementSource instanceof TurnBuffer) {
            // user input or turn buffer automated move
            // [TODO] this is duplicated at the move( method
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
            case PAC_MAN_GHOST_COLLISION:
                final PacMan2GhostCollisionEvent collisionEvent = (PacMan2GhostCollisionEvent) event;
                if (collisionEvent.getGhost().getActiveMode() instanceof Chaser
                        || collisionEvent.getGhost().getActiveMode() instanceof Scattered) {
                    throw new GameOverException(gameState, configs);
                }
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }
}
