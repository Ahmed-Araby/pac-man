package org.example.ghostmode;

import javafx.scene.canvas.Canvas;
import lombok.AllArgsConstructor;
import org.example.sprite.ghost.Ghost;

@AllArgsConstructor
public abstract class GhostMode {

    protected Ghost ghost;

    public abstract void render(Canvas canvas);
    public abstract  void move();

    public void init() {
        System.out.println("GhostMode.init() method is not implemented for sprite " + this.getClass().getSimpleName());
    }
    public abstract void enter();
    public abstract boolean ended();
}
