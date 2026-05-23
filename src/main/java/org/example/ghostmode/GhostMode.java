package org.example.ghostmode;

import javafx.scene.canvas.Canvas;
import lombok.AllArgsConstructor;
import org.example.sprite.ghost.Ghost;

@AllArgsConstructor
public abstract class GhostMode {

    protected Ghost ghost;

    public abstract void render(Canvas canvas);
    public abstract  void move();

    public void enter(Ghost ghost) {
    }
    public boolean end(Ghost ghost) {
        throw new IllegalStateException("ended(Ghost) method in not implemented for mode : " + this.getClass().getSimpleName());
    }
}
