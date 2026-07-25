package com.ahmedaraby.game.pacman.ghostmode.navigation;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.MovingSprite;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.entity.Vector;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StrideCalculator {
    final GameState gameState;
    final ConfigsEx configs;

    public double calcCalibratedStride(MovingSprite sprite, Vector newDir) {
        if (newDir.equals(Vector.STILL)) {
            return 0;
        }
        final double originalStride = calcStride(sprite, newDir);
        return calibrateStride(sprite, originalStride, newDir);
    }

    private double calcStride(MovingSprite sprite, Vector newDir) {
        if (newDir.equals(Vector.STILL)) {
            return 0;
        }
        final double elapsedTime = Math.abs(gameState.getPrevFrameEndedAt() - gameState.getCurrFrameStartedAt()) / 1_000_000_000.0;
        return sprite.getSpeed() * elapsedTime;
    }

    private double calibrateStride(MovingSprite sprite, double stride, Vector newDir) {
        double calibratedStride = stride;
        if (sprite.isGoingOutOfCanvas(stride, newDir)) {
            calibratedStride = stride + calcStrideCorrectiveOffsetToPreventGoingOut(sprite, stride, newDir);
        } else if (sprite.isCollidingWithWallOrGhostHWall(stride, newDir)) {
            calibratedStride = stride + calcStrideCorrectiveOffsetToUnblockWallStuck(sprite, stride, newDir);
        }
        return calibratedStride;
    }

    private double calcStrideCorrectiveOffsetToPreventGoingOut(MovingSprite sprite, double stride, Vector dir) {
        final Coordinate nextCord = sprite.calcNextCord(stride, dir);
        if (Vector.RIGHT == dir) {
            return (configs.CANVAS_WIDTH() - sprite.getWidth()) - nextCord.getCol();
        } else if (Vector.LEFT == dir) {
            return nextCord.getCol();
        } else if (Vector.UP == dir) {
            return nextCord.getRow();
        } else if (Vector.DOWN == dir) {
            return (configs.CANVAS_HEIGHT() - sprite.getHeight()) - nextCord.getRow();
        }
        return 0;
    }

    private double calcStrideCorrectiveOffsetToUnblockWallStuck(MovingSprite sprite, double stride, Vector dir) {
        final Coordinate nextCord = sprite.calcNextCord(stride, dir);
        double offset = 0;
        if (Vector.RIGHT == dir) {
            offset = nextCord.getCol() % configs.PLAYGROUND_CELL_SIZE();
        } else if (Vector.LEFT == dir) {
            offset = configs.PLAYGROUND_CELL_SIZE() - (nextCord.getCol() % configs.PLAYGROUND_CELL_SIZE());
        } else if (Vector.UP == dir) {
            offset = configs.PLAYGROUND_CELL_SIZE() - (nextCord.getRow() % configs.PLAYGROUND_CELL_SIZE());
        } else if (Vector.DOWN == dir) {
            offset = nextCord.getRow() % configs.PLAYGROUND_CELL_SIZE();
        }
        return -offset;
    }
}
