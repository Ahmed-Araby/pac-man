package org.example.model;

import lombok.*;
import org.example.sprite.GhostHouseS;
import org.example.sprite.ghost.Ghost;
import org.example.sprite.PacMan;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Getter
@Setter
public class GameState {
    private PacMan pacMan;
    @Setter(AccessLevel.NONE)
    private List<Ghost> ghosts = new ArrayList<>();
    private GhostHouseS ghostHouseS;

    public void addGhost(Ghost ghost) {
        ghosts.add(ghost);
    }
}
