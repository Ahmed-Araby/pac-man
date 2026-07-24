package com.ahmedaraby.game.pacman.model;

import com.ahmedaraby.jengine.entity.Vector;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class MazeMove {
    private Cell from;
    private Cell to;
    private Vector dir;

}
