package com.ahmedaraby.game.pacman.scene;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public abstract class GameScene {
    // javaFX
    protected Canvas canvas;
    protected Pane pane;
    protected Scene scene;

    public abstract void render();
    public abstract void update();
}
