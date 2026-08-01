package com.ahmedaraby.game.pacman.sound;

import com.ahmedaraby.jengine.sprite.AssetRegistry;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class FxAudioRegistry extends AssetRegistry<String, MediaPlayer> {

    /**
     *
     * @param key a resource path relative to the class path
     * @return
     */
    @Override
    protected MediaPlayer load(String key) {
        final String absolutePath = getClass().getResource(key).toString();
        final Media media = new Media(absolutePath);
        return new MediaPlayer(media);
    }
}
