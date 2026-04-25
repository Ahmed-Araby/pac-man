package org.example.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.example.entity.CanvasCoordinate;
import org.example.entity.CanvasRect;

import java.util.List;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class CollisionReport {

    private final CanvasRect collidingObject;
    private final List<CanvasRect> collidingObjects;
}
