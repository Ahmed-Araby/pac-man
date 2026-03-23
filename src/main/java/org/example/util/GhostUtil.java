package org.example.util;

import org.example.constant.Configs;
import org.example.constant.Dimensions;
import org.example.constant.DirectionsE;
import org.example.entity.CanvasCoordinate;
import org.example.event.ghost.GhostMovementAttemptEvent;

public class GhostUtil
{
    private GhostUtil() {}

    public static CanvasCoordinate move(GhostMovementAttemptEvent event) {
        return move(event.getGhostCurrCord(), event.getMovementDir());
    }

    public static CanvasCoordinate move(CanvasCoordinate cord, DirectionsE dir) {
        switch (dir) {
            case RIGHT:
                return new CanvasCoordinate(cord.getRow(),
                        cord.getCol() + Dimensions.BLINKY_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_BLINKY_STRIDE);
            case LEFT:
                return new CanvasCoordinate(cord.getRow(),
                        cord.getCol() - Dimensions.BLINKY_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_BLINKY_STRIDE);
            case UP:
                return new CanvasCoordinate(cord.getRow() - Dimensions.BLINKY_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_BLINKY_STRIDE,
                        cord.getCol());
            case DOWN:
                return new CanvasCoordinate(cord.getRow() + Dimensions.BLINKY_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_BLINKY_STRIDE,
                        cord.getCol());
            default:
                return cord;
        }
    }
}
