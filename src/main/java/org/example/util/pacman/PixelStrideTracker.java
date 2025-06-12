package org.example.util.pacman;

public class PixelStrideTracker {
    private final double desiredPixelStride;
    private final double restPixelStride;
    private double accumulatedPixelStride;

    public PixelStrideTracker(double desiredPixelStride, double restPixelStride) {
        this.desiredPixelStride = desiredPixelStride;
        this.restPixelStride = restPixelStride;
        this.accumulatedPixelStride = 0;
    }

    public void stride(double stride) {
        accumulatedPixelStride += stride;
    }

    public boolean isDesiredPixelStrideAchieved() {
        return accumulatedPixelStride >= desiredPixelStride;
    }

    public boolean isRestPixelStrideAchieved() {
        return accumulatedPixelStride >= restPixelStride;
    }

    public void reset() {
        accumulatedPixelStride = 0;
    }
}
