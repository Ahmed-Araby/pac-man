package com.ahmedaraby.game.pacman.util.pacman;

import com.ahmedaraby.game.pacman.entity.CanvasCoordinate;
import com.ahmedaraby.game.pacman.entity.Rectangle;
import com.ahmedaraby.game.pacman.event.movement.PacManMovementRequestEvent;
import com.ahmedaraby.game.pacman.util.canvas.CanvasRectUtils;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.ahmedaraby.game.pacman.constant.DirectionsE;

@NoArgsConstructor
@Setter
public class TurnBuffer {

    private DirectionsE bufferedDirection;
    private Rectangle pacManCanvasRectangleAtBufferingTime;
    // we can simplify the turn buffer using PixelStrideTracker, however I think the current approach is more accurate
    public boolean isBlockedTurn(DirectionsE currentPacManDirection, DirectionsE requestedDirection) {
        return currentPacManDirection != requestedDirection;
    }

    public boolean isThereBufferedTurn(Rectangle currPacManCanvasRectangle, DirectionsE currentPacManDirection) {
        if (bufferedDirection == null) {
            return false;
        }

        if (bufferedDirection == currentPacManDirection || // without this condition, we get accelerated movement,
                // because on the turn cell, pac man didn't move beyond the next cell yet, and this cause the automatic movement and buffered turn to execute at the same time
                hasPacManMovedBeyondTheNextCell(currPacManCanvasRectangle, currentPacManDirection)) {
            discardTurnBuffer();
            return false;
        }

        return true;
    }

    public PacManMovementRequestEvent getBufferedPacManAutomatedMovementRequest() {
        // handle situations where there is no buffered turn, we shouldn't trust the client code.
        return new PacManMovementRequestEvent(bufferedDirection, this);
    }

    public void bufferTurn(DirectionsE bufferedTurn, Rectangle pacManCanvasRectangleAtBufferingTime) {
        this.bufferedDirection = bufferedTurn;
        this.pacManCanvasRectangleAtBufferingTime = pacManCanvasRectangleAtBufferingTime;
    }

    public void discardTurnBuffer() {
        bufferedDirection = null;
        pacManCanvasRectangleAtBufferingTime = null;
    }

    private boolean hasPacManMovedBeyondTheNextCell(Rectangle currPacManCanvasRectangle, DirectionsE currentPacManDirection) {
        // handle out of bounds situations
        final CanvasCoordinate topLeftCornerOfNextRect = CanvasRectUtils.getTopLeftCornerOfNextRect(pacManCanvasRectangleAtBufferingTime, currentPacManDirection);

        if (currentPacManDirection == DirectionsE.RIGHT) {
            return currPacManCanvasRectangle.getTopLeftCorner().getCol() > topLeftCornerOfNextRect.getCol();
        } else if (currentPacManDirection == DirectionsE.UP) {
            return currPacManCanvasRectangle.getTopLeftCorner().getRow() < topLeftCornerOfNextRect.getRow();
        } else if (currentPacManDirection == DirectionsE.DOWN) {
            return currPacManCanvasRectangle.getTopLeftCorner().getRow() > topLeftCornerOfNextRect.getRow();
        } else if (currentPacManDirection == DirectionsE.LEFT) {
            return currPacManCanvasRectangle.getTopLeftCorner().getCol() < topLeftCornerOfNextRect.getCol();
        } else if (currentPacManDirection == DirectionsE.STILL) {
            return true;
        }
        return true;
    }

}
