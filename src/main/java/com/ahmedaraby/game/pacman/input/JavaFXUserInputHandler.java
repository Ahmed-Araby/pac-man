package com.ahmedaraby.game.pacman.input;

import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.event.movement.PacManMovementRequestEvent;
import com.ahmedaraby.game.pacman.event.manager.SyncEventManager;
import javafx.scene.input.KeyEvent;



public class JavaFXUserInputHandler implements JavaFXInputHandler{

    private SyncEventManager syncEventManager;

    public JavaFXUserInputHandler(SyncEventManager syncEventManager) {
        this.syncEventManager = syncEventManager;
    }

    @Override
    public void handleKeyPressedEvent(KeyEvent keyEvent) {
        try {
            final PacManMovementRequestEvent event = new PacManMovementRequestEvent(DirectionsE.from(keyEvent.getCode()), keyEvent.getSource());
            syncEventManager.notifySubscribers(event);
        } catch (Exception exc) {
            System.out.println("user input is not a valid pac man movement input");
        }
    }
}
