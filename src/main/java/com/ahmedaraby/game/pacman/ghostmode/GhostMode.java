package com.ahmedaraby.game.pacman.ghostmode;

import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import com.ahmedaraby.jengine.sprite.SpriteRegistry;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public abstract class GhostMode {

    protected final Ghost ghost;
    protected final GameState gameState;
    protected final SpriteRegistry<String, Image> spriteRegistry;

    public abstract void render(Canvas canvas);
    public abstract  void move();

    public void init() {
        System.out.println("GhostMode.init() method is not implemented for sprite " + this.getClass().getSimpleName());
    }
    public abstract void enter();
    public abstract boolean ended();

    protected Image[] loadSprites(String[] frameRelativePaths) {
        final List<Image> spriteList = new ArrayList<>();
        for (String path : frameRelativePaths) {
            spriteList.add(spriteRegistry.get(path));
        }
        return spriteList.toArray(new Image[0]);
    }
}
