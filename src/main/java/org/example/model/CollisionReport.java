package org.example.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.example.entity.CanvasCoordinate;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class CollisionReport {

    private CanvasCoordinate collidingSpriteTopLeftCorner;
}
