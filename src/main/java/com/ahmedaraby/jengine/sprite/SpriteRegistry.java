package com.ahmedaraby.jengine.sprite;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class SpriteRegistry<K, V> {
    private final Map<K, V> registry = new ConcurrentHashMap<>();

    public V get(K key) throws IllegalStateException {
        V sprite = registry.get(key);
        if (sprite == null) {
            sprite = load(key);
        }
        if (sprite == null) {
            throw new IllegalStateException("failed to load Sprite with key = " + key);
        }
        return sprite;
    }


    protected abstract V load(K key);
}
