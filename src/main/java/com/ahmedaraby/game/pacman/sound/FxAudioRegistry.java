package com.ahmedaraby.game.pacman.sound;

import com.ahmedaraby.jengine.sprite.AssetRegistry;
import javafx.scene.media.Media;


public class FxAudioRegistry extends AssetRegistry<String, Media> {

    /**
     *
     * @param key a resource path relative to the class path
     * @return
     */
    @Override
    protected Media load(String key) {
        final String absolutePath = getClass().getResource(key).toString();
        return new Media(absolutePath);
    }
}
