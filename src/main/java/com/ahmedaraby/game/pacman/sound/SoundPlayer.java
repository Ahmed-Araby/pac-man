package com.ahmedaraby.game.pacman.sound;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.model.event.Event;
import com.ahmedaraby.game.pacman.model.event.EventType;
import com.ahmedaraby.jengine.event.Subscriber;
import javafx.scene.media.MediaPlayer;

public class SoundPlayer implements Subscriber<EventType> {

    private final ConfigsEx configs;
    private final FxAudioRegistry registry;

    public SoundPlayer(ConfigsEx configs) {
        this.configs = configs;
        this.registry = new FxAudioRegistry();

        // load
        registry.preload(configs.PAC_MAN_EAT_SUGAR_CLIP_PATH());
        registry.preload(configs.PAC_MAN_DYING_CLIP_PATH());
        registry.preload(configs.AFTER_PAC_MAN_DEATH_CLIP_PATH());
    }

    public void play(String key) {
        registry.get(key).play();
    }

    public MediaPlayer repeat(String key, int count) {
        final MediaPlayer player = registry.get(key);
        player.setCycleCount(count);
        player.play();
        return player;
    }

    @Override
    public void update(Event<EventType> event) {
        switch (event.getType()) {
            case PAC_MAN_SUGAR_COLLISION, PAC_MAN_SUPER_SUGAR_COLLISION:
                play(configs.PAC_MAN_EAT_SUGAR_CLIP_PATH());
                break;
            default:
                throw new IllegalStateException("SoundPlayer doesn't understand event : " + event);
        }
    }
}
