package org.example.input;

import org.example.constant.DirectionsE;
import org.example.event.manager.SyncEventManager;
import org.example.event.movement.PacManMovementRequestEvent;
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
