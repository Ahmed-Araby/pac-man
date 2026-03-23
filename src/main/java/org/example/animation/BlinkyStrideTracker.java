package org.example.animation;

import org.example.config.Configs;
import org.example.constant.DimensionsC;
import org.example.constant.GhostSpriteFrameE;

public class BlinkyStrideTracker extends GPixelStrideTracker {

    private final double[] limits = {
            DimensionsC.BLINKY_FIRST_LEG_MOVEMENT_DISTANCE_PIXELS,
            DimensionsC.BLINKY_FIRST_LEG_MOVEMENT_DISTANCE_PIXELS + DimensionsC.BLINKY_SECOND_LEG_MOVEMENT_DISTANCE_PIXELS
    };

    public BlinkyStrideTracker() {
        super();
        setLimits(limits);
    }

    public void stride() {
        stride(DimensionsC.BLINKY_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_BLINKY_STRIDE);
    }

    public Object getState() {
        final int index = (int) super.getState();
        switch (index) {
            case 0:
                return GhostSpriteFrameE.FIRST;
            case 1:
                return GhostSpriteFrameE.SECOND;
        }
        return null;
    }
}
