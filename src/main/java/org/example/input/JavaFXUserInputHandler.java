package org.example.input;

import org.example.constant.DirectionsE;
import org.example.event.EventManager;
import org.example.event.EventType;
import org.example.event.PacManMovementEvent;
import javafx.scene.input.KeyEvent;



public class JavaFXUserInputHandler implements JavaFXInputHandler{

    private EventManager eventManager;

    public JavaFXUserInputHandler(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void handleKeyPressedEvent(KeyEvent keyEvent) {
        try {
            final PacManMovementEvent event = new PacManMovementEvent(EventType.PAC_MAN_MOVEMENT, DirectionsE.from(keyEvent.getCode()), keyEvent.getSource());
            eventManager.notifySubscribers(event);
        } catch (Exception exc) {
            System.out.println("user input is not a valid pac man movement input");
        }
    }
}
