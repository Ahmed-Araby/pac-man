package org.example.event.collision;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.example.entity.CanvasRect;

@EqualsAndHashCode
@Getter
@ToString
public class M2MCollisionDetectionEvent extends CollisionDetectionEvent {
    private final CanvasRect rectB;

    public M2MCollisionDetectionEvent(CanvasRect rectA, CanvasRect rectB) {
        super(rectA);
        this.rectB = rectB;
    }
}
