package com.ahmedaraby.game.pacman.sound;

import com.ahmedaraby.game.pacman.constant.SoundFileNameC;
import com.ahmedaraby.game.pacman.event.Event;
import com.ahmedaraby.game.pacman.event.EventType;
import javafx.scene.media.AudioClip;
import com.ahmedaraby.jengine.event.Subscriber;

public class SoundPlayer implements Subscriber<EventType> {

    private AudioClip eatSugar;
    public SoundPlayer() {
        // load eat sugar sound
        final String PAC_MAN_EAT_SUGAR_SOUND_FILE_ABSOLUTE_PATH = getClass().getResource(SoundFileNameC.PAC_MAN_EAT_SUGAR_SOUND_FILE_RESOURCES_RELATIVE_PATH).toString();
        System.out.println("PAC_MAN_EAT_SUGAR_SOUND_FILE_ABSOLUTE_PATH = " + PAC_MAN_EAT_SUGAR_SOUND_FILE_ABSOLUTE_PATH);
        eatSugar = new AudioClip(PAC_MAN_EAT_SUGAR_SOUND_FILE_ABSOLUTE_PATH);
    }

    public void playPacManSugarCollisionSound() {
        if(!eatSugar.isPlaying()) {
            eatSugar.play();
        }
    }

    public void playPacManSuperSugarCollisionSound() {
        if(!eatSugar.isPlaying()) {
            eatSugar.play();
        }
    }

    @Override
    public void update(Event<EventType> event) {
        switch (event.getType()) {
            case PAC_MAN_SUGAR_COLLISION:
                playPacManSugarCollisionSound();
                break;
            case PAC_MAN_SUPER_SUGAR_COLLISION:
                playPacManSuperSugarCollisionSound();
                break;
            default:
                throw new IllegalArgumentException();
        }
    }
}
