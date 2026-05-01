package org.example.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.example.ghostmode.Ghost;
import org.example.sprite.PacMan;

import java.util.List;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class GameState {
    private final PacMan pacMan;
    private final List<Ghost> ghosts;
}
