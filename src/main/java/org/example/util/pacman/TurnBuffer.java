package org.example.util.pacman;

import javafx.scene.input.KeyEvent;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.constant.DirectionsE;
import org.example.entity.Coordinate;
import org.example.event.EventType;
import org.example.event.PacManMovementEvent;
import org.example.util.RectUtils;

@NoArgsConstructor
@Setter
public class TurnBuffer {

    private DirectionsE bufferedDirection;
    private Coordinate pacManCanvasCoordAtBufferingTime;
    // we can simplify the turn buffer using PixelStrideTracker, however I think the current approach is more accurate
    public boolean isBlockedTurn(boolean collisionDetected, DirectionsE currentPacManDirection, DirectionsE requestedDirection) {
        return collisionDetected && currentPacManDirection != requestedDirection;
    }

    public boolean isThereBufferedTurn(Coordinate currPacManCanvasCoord, DirectionsE currentPacManDirection, double canvasCellWidth, double canvasCellHeight) {
        if (bufferedDirection == null) {
            return false;
        }

        if (bufferedDirection == currentPacManDirection || // without this condition, we get accelerated movement,
                // because on the turn cell, pac man didn't move beyond the next cell yet, and this cause the automatic movement and buffered turn to execute at the same time
                hasPacManMovedBeyondTheNextCell(currPacManCanvasCoord, currentPacManDirection, canvasCellWidth, canvasCellHeight)) {
            discardTurnBuffer();
            return false;
        }

        return true;
    }

    public PacManMovementEvent getBufferedTurnKeyEvent() {
        // handle situations where there is no buffered turn, we shouldn't trust the client code.
        return new PacManMovementEvent(EventType.PAC_MAN_MOVEMENT, bufferedDirection, this);
    }

    public void bufferTurn(DirectionsE bufferedTurn, Coordinate pacManCanvasCoordAtBufferingTime) {
        this.bufferedDirection = bufferedTurn;
        this.pacManCanvasCoordAtBufferingTime = pacManCanvasCoordAtBufferingTime;
    }

    public void discardTurnBuffer() {
        bufferedDirection = null;
        pacManCanvasCoordAtBufferingTime = null;
    }

    private boolean hasPacManMovedBeyondTheNextCell(Coordinate currPacManCanvasCoord, DirectionsE currentPacManDirection, double canvasCellWidth, double canvasCellHeight) {
        // handle out of bounds situations
        final Coordinate topLeftCornerOfNextRect = RectUtils.getTopLeftCornerOfNextRect(pacManCanvasCoordAtBufferingTime, canvasCellWidth, canvasCellHeight, currentPacManDirection);

        if (currentPacManDirection == DirectionsE.RIGHT) {
            return currPacManCanvasCoord.getCol() > topLeftCornerOfNextRect.getCol();
        } else if (currentPacManDirection == DirectionsE.UP) {
            return currPacManCanvasCoord.getRow() < topLeftCornerOfNextRect.getRow();
        } else if (currentPacManDirection == DirectionsE.DOWN) {
            return currPacManCanvasCoord.getRow() > topLeftCornerOfNextRect.getRow();
        } else if (currentPacManDirection == DirectionsE.LEFT) {
            return currPacManCanvasCoord.getCol() < topLeftCornerOfNextRect.getCol();
        } else if (currentPacManDirection == DirectionsE.STILL) {
            return true;
        }
        return true;
    }

}
