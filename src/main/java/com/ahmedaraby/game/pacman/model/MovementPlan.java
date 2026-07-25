package com.ahmedaraby.game.pacman.model;

import com.ahmedaraby.jengine.entity.Vector;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MovementPlan implements Comparable<MovementPlan>{
    private Vector dir;
    private double dist2Target;

    @Override
    public int compareTo(MovementPlan o) {
        return (int) (dist2Target - o.getDist2Target());
    }
}
