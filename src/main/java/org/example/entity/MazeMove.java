package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class MazeMove implements Comparable {
    private MazeCell source;
    private int dist2Target;

    @Override
    public int compareTo(Object o) {
        final MazeMove otherObj = (MazeMove) o;
        return dist2Target - otherObj.dist2Target;
    }
}
