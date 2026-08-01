package com.ahmedaraby.game.pacman.sprite.pacman.mode;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.constant.ColorC;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.model.event.Event;
import com.ahmedaraby.game.pacman.model.event.movement.PacManMovementRequestEvent;
import com.ahmedaraby.game.pacman.sprite.pacman.PacMan;
import com.ahmedaraby.game.pacman.util.pacman.PacManMouthAnimationTracker;
import com.ahmedaraby.game.pacman.util.pacman.TurnBuffer;
import com.ahmedaraby.jengine.entity.Vector;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;

public class PlayerPacManMode extends PacManMode{

    private final TurnBuffer turnBuffer;
    private final PacManMouthAnimationTracker mouthAnimationTracker;

    public PlayerPacManMode(PacMan pacMan, GameState gameState, ConfigsEx configs) {
        super(pacMan, gameState, configs);
        this.turnBuffer = new TurnBuffer(configs.PLAYGROUND_CELL_SIZE() * 2);
        this.mouthAnimationTracker = new PacManMouthAnimationTracker(
                configs.PACMAN_MOUTH_OPEN_DISTANCE(),
                configs.PACMAN_MOUTH_CLOSED_DISTANCE());
    }

    @Override
    public void render(Canvas canvas) {
        final GraphicsContext con = canvas.getGraphicsContext2D();

        // calculate effective angles
        double effectiveArcStartAngle = pacMan.getArcStartAngle();
        double effectiveArcExtent = pacMan.getArcExtent();
        if (mouthAnimationTracker.isClosed()) {
            effectiveArcStartAngle = configs.PACMAN_MOUTH_CLOSED_ARC_START_ANGLE_DEG();
            effectiveArcExtent = configs.PACMAN_MOUTH_CLOSED_ARC_EXTENT_DEG();
        }

        // render
        con.setFill(ColorC.PAC_MAN_COLOR);
        con.fillArc(pacMan.getCol(), pacMan.getRow(), configs.PACMAN_DIAMETER(), configs.PACMAN_DIAMETER(),
                effectiveArcStartAngle, effectiveArcExtent, ArcType.ROUND);
    }

    @Override
    public void update(Event event) {
        if (event != null) {
            // movement attempt made by user
            if (((PacManMovementRequestEvent)event).getDir() != pacMan.getDirV()) {
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
        attemptMovementInSameDir(new PacManMovementRequestEvent(pacMan.getDirV(), this));
    }

    private boolean attemptMovementInSameDir(PacManMovementRequestEvent event) {
        final boolean isPossible = pacMan.isPossibleToMoveInSameDir();
        if (isPossible) {
            final double stride = pacMan.calcCalibratedStride(event.getDir());
            pacMan.move(stride, pacMan.getDirV());
            mouthAnimationTracker.stride(stride);
            updateTurnBuffer(event.getDir(), event.getSource());
        }
        return isPossible;
    }

    private boolean attemptMovementInNewDir(PacManMovementRequestEvent event) {
        if (Vector.STILL == event.getDir()) {
            pacMan.setDirV(event.getDir());
            return false;
        }

        final boolean isPossible = pacMan.isPossibleToMoveInNewDir(event.getDir());

        if (isPossible) {
            final double stride = pacMan.calcCalibratedStride(event.getDir());
            pacMan.move(stride, event.getDir());
            updateTurnBuffer(event.getDir(), event.getSource());

            mouthAnimationTracker.stride(stride);
            final int newArcStartAngle = pacMan.getOpenMouthStartAngleByDir().get(event.getDir());
            final int newArcExtent = configs.PACMAN_MOUTH_OPEN_ARC_EXTENT_DEG();
            pacMan.setArcStartAngle(newArcStartAngle);
            pacMan.setArcExtent(newArcExtent);
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
            final double stride = pacMan.calcCalibratedStride(dir);
            turnBuffer.stride(stride);
        }
    }
}
