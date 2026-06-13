package com.ahmedaraby.game.pacman.util.pacman;

import com.ahmedaraby.jengine.animation.GPixelStrideTracker;

public class PacManMouthAnimationTracker extends GPixelStrideTracker {

    public PacManMouthAnimationTracker(double openMouthPixels, double closedMouthPixels) {
        super(new double[]{openMouthPixels, openMouthPixels + closedMouthPixels});
    }

    public boolean isClosed() {
        return ((int)getState()) == 1;
    }
}
