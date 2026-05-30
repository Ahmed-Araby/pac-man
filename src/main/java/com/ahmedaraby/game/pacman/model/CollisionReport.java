package com.ahmedaraby.game.pacman.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import com.ahmedaraby.jengine.entity.Rectangle;

import java.util.List;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class CollisionReport {

    // this is stupid, I need to find a smarter names
    private final Rectangle collidingObject;
    private final List<Rectangle> collidingObjects;
}
