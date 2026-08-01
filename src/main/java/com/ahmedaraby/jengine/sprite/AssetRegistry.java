package com.ahmedaraby.jengine.sprite;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AssetRegistry<K, V> {
    private final Map<K, V> registry = new ConcurrentHashMap<>();

    public V get(K key) throws IllegalStateException {
        // [TODO] fix, subject to thread race conditions
        V sprite = registry.get(key);
        if (sprite == null) {
            sprite = load(key);
        }
        if (sprite == null) {
            throw new IllegalStateException("failed to load Sprite with key = " + key);
        }
        return sprite;
    }

    public void remove(K key) {
        registry.remove(key);
    }

    public void preload(K key) {
        // [TODO] fix, subject to thread race conditions
        final V asset = load(key);
        registry.put(key, asset);
    }

    protected abstract V load(K key);
}
