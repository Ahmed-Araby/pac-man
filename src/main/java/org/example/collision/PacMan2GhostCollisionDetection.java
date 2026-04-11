package org.example.collision;

import org.example.entity.CanvasCoordinate;
import org.example.entity.MazeCell;
import org.example.event.*;
import org.example.event.manager.EventManager;
import org.example.ghostmode.Ghost;
import org.example.util.canvas.CanvasUtil;

import java.util.List;

public class PacMan2GhostCollisionDetection implements Subscriber {

    private final EventManager eventManager;
    private final List<Ghost> ghosts;

    public PacMan2GhostCollisionDetection(EventManager eventManager, Ghost ...ghosts) {
        this.eventManager = eventManager;
        this.ghosts = List.of(ghosts);
    }

    public boolean isCollision(CanvasCoordinate pacManCord, CanvasCoordinate ghostCord) {
        final List<MazeCell> pacManInterctingMazeCells = CanvasUtil.getIntersectingMazeCells(pacManCord);
        final List<MazeCell> ghostCordInterctingMazeCells = CanvasUtil.getIntersectingMazeCells(ghostCord);
        return pacManInterctingMazeCells.stream()
                .anyMatch(cell -> ghostCordInterctingMazeCells.contains(cell));
    }

    private void checkCollision(CanvasCoordinate pacManCord) {
        for (Ghost ghost: ghosts) {
            final CanvasCoordinate ghostCord = new CanvasCoordinate(ghost.getCanvasRow(), ghost.getCanvasCol());
            if (isCollision(pacManCord, ghostCord)) {
                final PacMan2GhostCollisionEvent event = new PacMan2GhostCollisionEvent(ghost);
                eventManager.notifySubscribers(event);
            }
        }
    }

    @Override
    public void update(Event event) {
        switch (event.getType()) {
            case PAC_MAN_CURRENT_LOCATION -> checkCollision(((PacManCurrentLocationEvent)event).getPacManCanvasRectTopLeftCorner());
            default -> throw new IllegalArgumentException();
        }
    }
}
