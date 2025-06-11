package org.example.util.pacman;

import javafx.scene.input.KeyEvent;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.constant.DirectionsE;
import org.example.entity.Coordinate;

@NoArgsConstructor
@Setter
public class TurnBuffer {

    private DirectionsE bufferedTurn;
    private Coordinate pacManCanvasCoordAtBufferingTime;

    public boolean isBlockedTurn(boolean collisionDetected, DirectionsE currentPacManDirection, DirectionsE requestedDirection) {
        return collisionDetected && currentPacManDirection != requestedDirection;
    }

    public boolean isThereBufferedTurn(Coordinate currPacManCanvasCoord, DirectionsE currentPacManDirection, double canvasCellWidth, double canvasCellHeight) {
        if (bufferedTurn == null) {
            return false;
        }

        if (bufferedTurn == currentPacManDirection || hasPacManMovedBeyondToNextCell(currPacManCanvasCoord, currentPacManDirection, canvasCellWidth, canvasCellHeight)) {
            discardTurnBuffer();
            return false;
        }

        return true;
    }

    public KeyEvent getBufferedTurnKeyEvent() {
        // handle situations where there is no buffered turn, we shouldn't trust the client code.
        return new KeyEvent(this, null, null, null, null, bufferedTurn.toKeyCode(), false, false, false, false);
    }

    public void bufferTurn(DirectionsE bufferedTurn, Coordinate pacManCanvasCoordAtBufferingTime) {
        this.bufferedTurn = bufferedTurn;
        this.pacManCanvasCoordAtBufferingTime = pacManCanvasCoordAtBufferingTime;
    }

    public void discardTurnBuffer() {
        bufferedTurn = null;
        pacManCanvasCoordAtBufferingTime = null;
    }

    private boolean hasPacManMovedBeyondToNextCell(Coordinate currPacManCanvasCoord, DirectionsE currentPacManDirection, double canvasCellWidth, double canvasCellHeight) {
        // handle out of bounds situations
        if (currentPacManDirection == DirectionsE.RIGHT) {
            final double nextCanvasCellCol = pacManCanvasCoordAtBufferingTime.getCol() + canvasCellWidth - pacManCanvasCoordAtBufferingTime.getCol() % canvasCellWidth;
            return currPacManCanvasCoord.getCol() > nextCanvasCellCol;
        } else if (currentPacManDirection == DirectionsE.UP) {
            final double prevCanvasCellRow = pacManCanvasCoordAtBufferingTime.getRow() - canvasCellHeight - pacManCanvasCoordAtBufferingTime.getRow() % canvasCellHeight;
            return currPacManCanvasCoord.getRow() < prevCanvasCellRow;
        } else if (currentPacManDirection == DirectionsE.DOWN) {
            final double nextCanvasCellRow = pacManCanvasCoordAtBufferingTime.getRow() + canvasCellHeight - pacManCanvasCoordAtBufferingTime.getRow() % canvasCellHeight;
            return currPacManCanvasCoord.getRow() > nextCanvasCellRow;
        } else if (currentPacManDirection == DirectionsE.LEFT) {
            final double prevCanvasColCell = pacManCanvasCoordAtBufferingTime.getCol() - canvasCellWidth - pacManCanvasCoordAtBufferingTime.getCol() % canvasCellWidth;
            return currPacManCanvasCoord.getCol() < prevCanvasColCell;
        } else if (currentPacManDirection == DirectionsE.STILL) {
            return true;
        }
        return true;
    }

}
