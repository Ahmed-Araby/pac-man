package org.example.util.pacman;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.constant.DirectionsE;
import org.example.entity.CanvasCoordinate;
import org.example.entity.CanvasRect;
import org.example.event.movement.PacManMovementRequestEvent;
import org.example.util.RectUtils;

@NoArgsConstructor
@Setter
public class TurnBuffer {

    private DirectionsE bufferedDirection;
    private CanvasRect pacManCanvasCanvasRectAtBufferingTime;
    // we can simplify the turn buffer using PixelStrideTracker, however I think the current approach is more accurate
    public boolean isBlockedTurn(DirectionsE currentPacManDirection, DirectionsE requestedDirection) {
        return currentPacManDirection != requestedDirection;
    }

    public boolean isThereBufferedTurn(CanvasRect currPacManCanvasCanvasRect, DirectionsE currentPacManDirection) {
        if (bufferedDirection == null) {
            return false;
        }

        if (bufferedDirection == currentPacManDirection || // without this condition, we get accelerated movement,
                // because on the turn cell, pac man didn't move beyond the next cell yet, and this cause the automatic movement and buffered turn to execute at the same time
                hasPacManMovedBeyondTheNextCell(currPacManCanvasCanvasRect, currentPacManDirection)) {
            discardTurnBuffer();
            return false;
        }

        return true;
    }

    public PacManMovementRequestEvent getBufferedPacManAutomatedMovementRequest() {
        // handle situations where there is no buffered turn, we shouldn't trust the client code.
        return new PacManMovementRequestEvent(bufferedDirection, this);
    }

    public void bufferTurn(DirectionsE bufferedTurn, CanvasRect pacManCanvasCanvasRectAtBufferingTime) {
        this.bufferedDirection = bufferedTurn;
        this.pacManCanvasCanvasRectAtBufferingTime = pacManCanvasCanvasRectAtBufferingTime;
    }

    public void discardTurnBuffer() {
        bufferedDirection = null;
        pacManCanvasCanvasRectAtBufferingTime = null;
    }

    private boolean hasPacManMovedBeyondTheNextCell(CanvasRect currPacManCanvasCanvasRect, DirectionsE currentPacManDirection) {
        // handle out of bounds situations
        final CanvasCoordinate topLeftCornerOfNextRect = RectUtils.getTopLeftCornerOfNextRect(pacManCanvasCanvasRectAtBufferingTime, currentPacManDirection);

        if (currentPacManDirection == DirectionsE.RIGHT) {
            return currPacManCanvasCanvasRect.getTopLeftCorner().getCol() > topLeftCornerOfNextRect.getCol();
        } else if (currentPacManDirection == DirectionsE.UP) {
            return currPacManCanvasCanvasRect.getTopLeftCorner().getRow() < topLeftCornerOfNextRect.getRow();
        } else if (currentPacManDirection == DirectionsE.DOWN) {
            return currPacManCanvasCanvasRect.getTopLeftCorner().getRow() > topLeftCornerOfNextRect.getRow();
        } else if (currentPacManDirection == DirectionsE.LEFT) {
            return currPacManCanvasCanvasRect.getTopLeftCorner().getCol() < topLeftCornerOfNextRect.getCol();
        } else if (currentPacManDirection == DirectionsE.STILL) {
            return true;
        }
        return true;
    }

}
