package com.ahmedaraby.game.pacman.util.pacman;

import com.ahmedaraby.jengine.entity.Vector;
import lombok.Getter;

public class TurnBuffer {

    @Getter
    private Vector dir;
    private double traveledPixels;
    private double bufferDistInPixels;
    private double customBufferDistInPixels = 0;

    public TurnBuffer(double bufferDistInPixels) {
        this.bufferDistInPixels = bufferDistInPixels;
    }

    public void buffer(Vector dir) {
        this.dir = dir;
        traveledPixels = 0;
    }

    public void bufferFor(Vector dir, double pixels) {
        this.dir = dir;
        this.customBufferDistInPixels = pixels;
    }

    public void stride(double pixels) {
        traveledPixels += pixels;
    }

    public boolean exceededBufferDist() {
        final double effectiveBufferDistInPixels =
                customBufferDistInPixels != 0 ? customBufferDistInPixels : bufferDistInPixels;
        return traveledPixels > effectiveBufferDistInPixels;
    }

    public boolean isEmpty() {
        return dir == null;
    }

    public void clear() {
        dir = null;
        traveledPixels = 0;
        customBufferDistInPixels = 0;
    }
}
