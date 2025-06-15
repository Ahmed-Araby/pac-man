package org.example.sprite;

import javafx.scene.canvas.Canvas;
import org.example.event.Event;


public interface Sprite {

    void render(Canvas canvas);
    void move(Event event);
}
