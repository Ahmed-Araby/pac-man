package org.example.util.pacman;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.constant.DirectionsE;
import org.example.entity.Coordinate;
import org.example.entity.Rect;
import org.example.event.movement.PacManMovementRequestEvent;
import org.example.util.RectUtils;

@NoArgsConstructor
@Setter
public class TurnBuffer {

    private DirectionsE bufferedDirection;
    private Rect pacManCanvasRectAtBufferingTime;
    // we can simplify the turn buffer using PixelStrideTracker, however I think the current approach is more accurate
    public boolean isBlockedTurn(DirectionsE currentPacManDirection, DirectionsE requestedDirection) {
        return currentPacManDirection != requestedDirection;
    }

    public boolean isThereBufferedTurn(Rect currPacManCanvasRect, DirectionsE currentPacManDirection) {
        if (bufferedDirection == null) {
            return false;
        }

        if (bufferedDirection == currentPacManDirection || // without this condition, we get accelerated movement,
                // because on the turn cell, pac man didn't move beyond the next cell yet, and this cause the automatic movement and buffered turn to execute at the same time
                hasPacManMovedBeyondTheNextCell(currPacManCanvasRect, currentPacManDirection)) {
            discardTurnBuffer();
            return false;
        }

        return true;
    }

    public PacManMovementRequestEvent getBufferedPacManAutomatedMovementRequest() {
        // handle situations where there is no buffered turn, we shouldn't trust the client code.
        return new PacManMovementRequestEvent(bufferedDirection, this);
    }

    public void bufferTurn(DirectionsE bufferedTurn, Rect pacManCanvasRectAtBufferingTime) {
        this.bufferedDirection = bufferedTurn;
        this.pacManCanvasRectAtBufferingTime = pacManCanvasRectAtBufferingTime;
    }

    public void discardTurnBuffer() {
        bufferedDirection = null;
        pacManCanvasRectAtBufferingTime = null;
    }

    private boolean hasPacManMovedBeyondTheNextCell(Rect currPacManCanvasRect, DirectionsE currentPacManDirection) {
        // handle out of bounds situations
        final Coordinate topLeftCornerOfNextRect = RectUtils.getTopLeftCornerOfNextRect(pacManCanvasRectAtBufferingTime, currentPacManDirection);

        if (currentPacManDirection == DirectionsE.RIGHT) {
            return currPacManCanvasRect.getTopLeftCorner().getCol() > topLeftCornerOfNextRect.getCol();
        } else if (currentPacManDirection == DirectionsE.UP) {
            return currPacManCanvasRect.getTopLeftCorner().getRow() < topLeftCornerOfNextRect.getRow();
        } else if (currentPacManDirection == DirectionsE.DOWN) {
            return currPacManCanvasRect.getTopLeftCorner().getRow() > topLeftCornerOfNextRect.getRow();
        } else if (currentPacManDirection == DirectionsE.LEFT) {
            return currPacManCanvasRect.getTopLeftCorner().getCol() < topLeftCornerOfNextRect.getCol();
        } else if (currentPacManDirection == DirectionsE.STILL) {
            return true;
        }
        return true;
    }

}
