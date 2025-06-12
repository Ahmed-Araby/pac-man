package org.example.sound;

import javafx.scene.media.AudioClip;
import org.example.constant.SoundFileNameConstants;
import org.example.event.Event;
import org.example.event.Subscriber;

public class SoundPlayer implements Subscriber {

    private final AudioClip eatSugar;
    public SoundPlayer() {
        // load eat sugar sound
        final String PAC_MAN_EAT_SUGAR_SOUND_FILE_ABSOLUTE_PATH = getClass().getResource(SoundFileNameConstants.PAC_MAN_EAT_SUGAR_SOUND_FILE_RESOURCES_RELATIVE_PATH).toString();
        System.out.println("PAC_MAN_EAT_SUGAR_SOUND_FILE_ABSOLUTE_PATH = " + PAC_MAN_EAT_SUGAR_SOUND_FILE_ABSOLUTE_PATH);
        eatSugar = new AudioClip(PAC_MAN_EAT_SUGAR_SOUND_FILE_ABSOLUTE_PATH);
    }

    public void playPacManSugarCollisionSound() {
        eatSugar.play();
    }

    @Override
    public void update(Event event) {
        switch (event.getType()) {
            case PAC_MAN_SUGAR_COLLISION:
                playPacManSugarCollisionSound();
                break;
            default:
                throw new IllegalArgumentException();
        }
    }
}
