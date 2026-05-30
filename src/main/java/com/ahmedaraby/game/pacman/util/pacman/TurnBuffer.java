package com.ahmedaraby.game.pacman.util.pacman;

import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.entity.Rectangle;
import com.ahmedaraby.game.pacman.event.movement.PacManMovementRequestEvent;
import com.ahmedaraby.game.pacman.util.canvas.CanvasRectUtils;
import com.ahmedaraby.jengine.entity.Vector;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.ahmedaraby.game.pacman.constant.DirectionsE;

@NoArgsConstructor
@Setter
public class TurnBuffer {

    private Vector bufferedTurn;
    private Rectangle pacManCanvasRectangleAtBufferingTime;
    // we can simplify the turn buffer using PixelStrideTracker, however I think the current approach is more accurate
    public boolean isBlockedTurn(Vector currentPacManDirection, Vector requestedDirection) {
        return currentPacManDirection != requestedDirection;
    }

    public boolean isThereBufferedTurn(Rectangle currPacManCanvasRectangle, Vector currentPacManDirection) {
        if (bufferedTurn == null) {
            return false;
        }

        if (bufferedTurn == currentPacManDirection || // without this condition, we get accelerated movement,
                // because on the turn cell, pac man didn't move beyond the next cell yet, and this cause the automatic movement and buffered turn to execute at the same time
                hasPacManMovedBeyondTheNextCell(currPacManCanvasRectangle, currentPacManDirection)) {
            discardTurnBuffer();
            return false;
        }

        return true;
    }

    public PacManMovementRequestEvent getBufferedPacManAutomatedMovementRequest() {
        // handle situations where there is no buffered turn, we shouldn't trust the client code.
        return new PacManMovementRequestEvent(bufferedTurn, this);
    }

    public void bufferTurn(Vector bufferedTurn, Rectangle pacManCanvasRectangleAtBufferingTime) {
        this.bufferedTurn = bufferedTurn;
        this.pacManCanvasRectangleAtBufferingTime = pacManCanvasRectangleAtBufferingTime;
    }

    public void discardTurnBuffer() {
        bufferedTurn = null;
        pacManCanvasRectangleAtBufferingTime = null;
    }

    private boolean hasPacManMovedBeyondTheNextCell(Rectangle currPacManCanvasRectangle, Vector currentPacManDirection) {
        // handle out of bounds situations
        final Coordinate topLeftCornerOfNextRect = CanvasRectUtils.getTopLeftCornerOfNextRect(pacManCanvasRectangleAtBufferingTime, currentPacManDirection);

        if (currentPacManDirection == Vector.RIGHT) {
            return currPacManCanvasRectangle.getTopLeftCorner().getCol() > topLeftCornerOfNextRect.getCol();
        } else if (currentPacManDirection == Vector.UP) {
            return currPacManCanvasRectangle.getTopLeftCorner().getRow() < topLeftCornerOfNextRect.getRow();
        } else if (currentPacManDirection == Vector.DOWN) {
            return currPacManCanvasRectangle.getTopLeftCorner().getRow() > topLeftCornerOfNextRect.getRow();
        } else if (currentPacManDirection == Vector.LEFT) {
            return currPacManCanvasRectangle.getTopLeftCorner().getCol() < topLeftCornerOfNextRect.getCol();
        } else if (currentPacManDirection == Vector.STILL) {
            return true;
        }
        return true;
    }

}
