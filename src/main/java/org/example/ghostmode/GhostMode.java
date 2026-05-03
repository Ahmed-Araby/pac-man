package org.example.ghostmode;

import javafx.scene.canvas.Canvas;
import org.example.sprite.ghost.Ghost;

public interface GhostMode {

    void enter(Ghost ghost);
    float getActivePeriodSeconds();
    void render(Canvas canvas, Ghost ghost);
    void move(Ghost ghost);
}
