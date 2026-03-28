package org.example.ghostmode;

import javafx.scene.canvas.Canvas;

public interface GhostMode {

    void render(Canvas canvas, Ghost ghost);
    void move(Ghost ghost);
}
