package org.example.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.example.sprite.PacMan;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class GameState {
    private final PacMan pacMan;
}
