package org.example.util;

import org.example.constant.Configs;
import org.example.constant.Dimensions;
import org.example.constant.DirectionsE;
import org.example.entity.Coordinate;
import org.example.event.ghost.GhostMovementAttemptEvent;

public class GhostUtil
{
    private GhostUtil() {}

    public static Coordinate move(GhostMovementAttemptEvent event) {
        return move(event.getGhostCurrCord(), event.getMovementDir());
    }

    public static Coordinate move(Coordinate cord, DirectionsE dir) {
        switch (dir) {
            case RIGHT:
                return new Coordinate(cord.getRow(),
                        cord.getCol() + Dimensions.BLINKY_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_BLINKY_STRIDE);
            case LEFT:
                return new Coordinate(cord.getRow(),
                        cord.getCol() - Dimensions.BLINKY_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_BLINKY_STRIDE);
            case UP:
                return new Coordinate(cord.getRow() - Dimensions.BLINKY_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_BLINKY_STRIDE,
                        cord.getCol());
            case DOWN:
                return new Coordinate(cord.getRow() + Dimensions.BLINKY_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_BLINKY_STRIDE,
                        cord.getCol());
            default:
                return cord;
        }
    }
}
