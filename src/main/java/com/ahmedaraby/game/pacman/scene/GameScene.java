package com.ahmedaraby.game.pacman.scene;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import lombok.Getter;


public abstract class GameScene {
    // javaFX
    protected Pane pane;
    protected Canvas canvas;
    @Getter
    protected Scene scene;

    public abstract void render();
    public abstract void update();
}
