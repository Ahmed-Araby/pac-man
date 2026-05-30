package com.ahmedaraby.game.pacman.model;

import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import com.ahmedaraby.game.pacman.sprite.playground.GhostHouseS;
import com.ahmedaraby.game.pacman.sprite.playground.Maze;
import lombok.*;
import com.ahmedaraby.game.pacman.sprite.PacMan;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Getter
@Setter
public class GameState {
    private Maze maze;
    private PacMan pacMan;
    @Setter(AccessLevel.NONE)
    private List<Ghost> ghosts = new ArrayList<>();
    private GhostHouseS ghostHouseS;

    public void addGhost(Ghost ghost) {
        ghosts.add(ghost);
    }
}
