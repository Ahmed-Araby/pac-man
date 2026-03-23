package org.example.event.ghost;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.constant.DirectionsE;
import org.example.entity.CanvasCoordinate;

@AllArgsConstructor
@Getter
public class GhostMovementAttemptEvent {
    private final CanvasCoordinate ghostCurrCord;
    private final DirectionsE movementDir;
}
