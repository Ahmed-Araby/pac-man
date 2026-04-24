package org.example.event.collision;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.example.entity.CanvasRect;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CollisionDetectionEvent {
    private final CanvasRect rectA;
}
