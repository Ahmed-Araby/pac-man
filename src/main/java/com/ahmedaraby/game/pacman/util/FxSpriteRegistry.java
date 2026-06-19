package com.ahmedaraby.game.pacman.util;

import com.ahmedaraby.jengine.sprite.SpriteRegistry;
import javafx.scene.image.Image;

import java.net.URL;

public class FxSpriteRegistry extends SpriteRegistry<String, Image> {
    @Override
    protected Image load(String path) {
        final URL url = getClass().getResource(path);
        return new Image(url.toString());
    }
}
