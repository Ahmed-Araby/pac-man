package org.example.ghostmode;

import javafx.scene.canvas.Canvas;
import org.example.sprite.ghost.Ghost;

public interface GhostMode {

    void render(Canvas canvas, Ghost ghost);
    void move(Ghost ghost);

    default void enter(Ghost ghost) {
    }
    default boolean end(Ghost ghost) {
        throw new IllegalStateException("ended(Ghost) method in not implemented for mode : " + this.getClass().getSimpleName());
    }
}
