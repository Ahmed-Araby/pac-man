package org.example.sprite;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;


public interface Sprite {

    void render(Canvas canvas);
    void move(KeyEvent event);
}
