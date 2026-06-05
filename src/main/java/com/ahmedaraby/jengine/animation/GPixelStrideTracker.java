package com.ahmedaraby.jengine.animation;

import lombok.Setter;

@Setter
public abstract class GPixelStrideTracker {

    private double[] limits;
    private double accumulatedStridePixels = 0;

    public GPixelStrideTracker(double[] limits) {
        this.limits = limits;
    }

    public void stride(double amountPixels) {
        accumulatedStridePixels += amountPixels;
    }

    public Object getState() {
        while(true) {
            for(int i=0; i<limits.length; i++) {
                if(limits[i] >= accumulatedStridePixels) {
                    return i;
                }
            }
            final double carry = accumulatedStridePixels - limits[limits.length - 1];
            reset(carry);
        }
    }

    public void reset(double carry) {
        accumulatedStridePixels = carry;
    }

}
