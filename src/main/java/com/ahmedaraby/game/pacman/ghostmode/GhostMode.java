package com.ahmedaraby.game.pacman.ghostmode;

import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import javafx.scene.canvas.Canvas;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class GhostMode {

    protected Ghost ghost;
    protected GameState gameState;

    public abstract void render(Canvas canvas);
    public abstract  void move();

    public void init() {
        System.out.println("GhostMode.init() method is not implemented for sprite " + this.getClass().getSimpleName());
    }
    public abstract void enter();
    public abstract boolean ended();
}
