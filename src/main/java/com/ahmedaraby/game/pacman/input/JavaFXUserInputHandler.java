package com.ahmedaraby.game.pacman.input;

import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.event.movement.PacManMovementRequestEvent;
import com.ahmedaraby.jengine.entity.Vector;
import com.ahmedaraby.jengine.event.SyncEventManager;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;



public class JavaFXUserInputHandler implements JavaFXInputHandler{

    private SyncEventManager syncEventManager;

    public JavaFXUserInputHandler(SyncEventManager syncEventManager) {
        this.syncEventManager = syncEventManager;
    }

    @Override
    public void handleKeyPressedEvent(KeyEvent keyEvent) {
        try {
            final PacManMovementRequestEvent event = new PacManMovementRequestEvent(from(keyEvent.getCode()), keyEvent.getSource());
            syncEventManager.notify(event);
        } catch (Exception exc) {
            System.out.println("user input is not a valid pac man movement input");
        }
    }

    private Vector from(KeyCode code) {
        switch (code) {
            case RIGHT:
                return Vector.RIGHT;
            case UP:
                return Vector.UP;
            case DOWN:
                return Vector.DOWN;
            case LEFT:
                return Vector.LEFT;
            case SPACE:
                return Vector.STILL;
            default:
                throw new IllegalStateException();
        }
    }
}
