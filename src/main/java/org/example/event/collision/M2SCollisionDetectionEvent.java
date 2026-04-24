package org.example.event.collision;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.example.constant.SpriteE;
import org.example.entity.CanvasRect;

@EqualsAndHashCode
@Getter
@ToString
public class M2SCollisionDetectionEvent extends CollisionDetectionEvent {
    private final SpriteE target;

    public M2SCollisionDetectionEvent(CanvasRect rectA, SpriteE target) {
        super(rectA);
        this.target = target;
    }
}
