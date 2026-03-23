package org.example.event.ghost;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.constant.DirectionsE;
import org.example.entity.Coordinate;

@AllArgsConstructor
@Getter
public class GhostMovementAttemptEvent {
    private final Coordinate ghostCurrCord;
    private final DirectionsE movementDir;
}
