package com.ahmedaraby.game.pacman.sprite.pacman;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.game.pacman.ghostmode.Chaser;
import com.ahmedaraby.game.pacman.ghostmode.Scattered;
import com.ahmedaraby.game.pacman.model.event.EventType;
import com.ahmedaraby.game.pacman.ghostmode.navigation.StrideCalculator;
import com.ahmedaraby.game.pacman.model.event.collision.PacMan2GhostCollisionEvent;
import com.ahmedaraby.game.pacman.model.exception.GameOverException;
import com.ahmedaraby.game.pacman.sprite.MovingSprite;
import com.ahmedaraby.game.pacman.sprite.pacman.mode.DyingPacManMode;
import com.ahmedaraby.game.pacman.sprite.pacman.mode.ExplodingPacManMode;
import com.ahmedaraby.game.pacman.sprite.pacman.mode.PacManMode;
import com.ahmedaraby.game.pacman.sprite.pacman.mode.PlayerPacManMode;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.game.pacman.model.event.Event;
import com.ahmedaraby.jengine.entity.Vector;
import com.ahmedaraby.jengine.event.Subscriber;
import com.ahmedaraby.game.pacman.model.GameState;
import javafx.scene.canvas.Canvas;
import com.ahmedaraby.game.pacman.playground.Playground;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class PacMan extends MovingSprite implements Subscriber<EventType> {
    // pacman mouse geometric information
    private double arcStartAngle;
    private double arcExtent;
    private final Map<Vector, Integer> openMouthStartAngleByDir = new HashMap<>();
    private PacManMode activeMode;

    public PacMan(GameState gameState, ConfigsEx configs, StrideCalculator strideCalc) {
        super(gameState, configs, strideCalc,
                SpriteE.PAC_MAN, null,
                configs.PACMAN_DIAMETER(), configs.PACMAN_DIAMETER(),
                Vector.STILL
        );

        final Coordinate emptyCellPos = Playground.getEmptyMazePosition();
        setTopLeftCorner(emptyCellPos);

        arcStartAngle = configs.PACMAN_MOUTH_CLOSED_ARC_START_ANGLE_DEG();
        arcExtent = configs.PACMAN_MOUTH_CLOSED_ARC_EXTENT_DEG();

        openMouthStartAngleByDir.put(Vector.RIGHT, configs.PACMAN_MOUTH_OPEN_RIGHT_START_ANGLE());
        openMouthStartAngleByDir.put(Vector.UP, configs.PACMAN_MOUTH_OPEN_UP_START_ANGLE());
        openMouthStartAngleByDir.put(Vector.DOWN, configs.PACMAN_MOUTH_OPEN_DOWN_START_ANGLE());
        openMouthStartAngleByDir.put(Vector.LEFT, configs.PACMAN_MOUTH_OPEN_LEFT_START_ANGLE());

        activeMode = new PlayerPacManMode(this, gameState, configs);
    }


    @Override
    public double getSpeed() {
        return configs.PACMAN_SPEED();
    }


    @Override
    public void render(Canvas canvas) {
        activeMode.render(canvas);
    }


    @Override
    public void move(Event event) {
        transitionMode(event);
        activeMode.update(event);
    }


    @Override
    public void update(Event<EventType> event) {
        switch (event.getType()) {
            case PAC_MAN_MOVEMENT_REQUEST:
                move(event);
                break;
            case PAC_MAN_GHOST_COLLISION:
                transitionMode(event);
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    private void transitionMode(Event<EventType> event) {
        switch (activeMode) {
            case PlayerPacManMode playerPacManMode -> playerModeTransition(event);
            default -> throw new IllegalStateException("no available transition for pacman");
        }
    }

    private void playerModeTransition(Event<EventType> event) {
        if (event != null && EventType.PAC_MAN_GHOST_COLLISION == event.getType()) {
            final PacMan2GhostCollisionEvent collisionEvent = (PacMan2GhostCollisionEvent) event;
            if (collisionEvent.getGhost().getActiveMode() instanceof Chaser
                    || collisionEvent.getGhost().getActiveMode() instanceof Scattered) {
                activeMode = new DyingPacManMode(this, gameState, configs);
                throw new GameOverException(gameState, configs);
            }
        }

    }

}
