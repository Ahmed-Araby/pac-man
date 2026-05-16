package org.example.ghostmode;

import javafx.scene.canvas.Canvas;
import org.example.sprite.ghost.Ghost;

public interface GhostMode {

    void enter(Ghost ghost);
    float getActivePeriodSeconds();
    void render(Canvas canvas, Ghost ghost);
    void move(Ghost ghost);

    default boolean end(Ghost ghost) {
        throw new IllegalStateException("end method in not implemented for mode : " + this.getClass().getSimpleName());
    }
}
