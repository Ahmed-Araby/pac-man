package org.example.animation;

import javafx.scene.image.Image;

public class DistanceBasedAnimator implements Animator {

    private double traveledPixels = 0;
    private double[] switchPoints;
    private final Image[] frames;

    public DistanceBasedAnimator(double[] frameSpanInPixels, Image[] frames) {
        switchPoints = new double[frameSpanInPixels.length];
        switchPoints[0] = frameSpanInPixels[0];
        for (int i=1; i<frameSpanInPixels.length; i++) {
            switchPoints[i] = switchPoints[i-1] + frameSpanInPixels[i];
        }
        this.frames = frames;
    }

    @Override
    public void stride(double pixels) {
        traveledPixels = (traveledPixels + pixels) % switchPoints[switchPoints.length - 1];
    }

    @Override
    public Image getFrame(double pixels) {
        traveledPixels = (traveledPixels + pixels) % switchPoints[switchPoints.length - 1];
        for(int i=0; i<switchPoints.length; i++) {
            if (traveledPixels <= switchPoints[i]) {
                return frames[i];
            }
        }
        return null;
    }

    @Override
    public Image getFrame() {
        for(int i=0; i<switchPoints.length; i++) {
            if (traveledPixels <= switchPoints[i]) {
                return frames[i];
            }
        }
        return null;
    }
}
