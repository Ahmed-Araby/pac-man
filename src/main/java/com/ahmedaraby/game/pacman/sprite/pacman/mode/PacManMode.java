package com.ahmedaraby.game.pacman.sprite.pacman.mode;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.model.event.Event;
import com.ahmedaraby.game.pacman.model.event.EventType;
import com.ahmedaraby.game.pacman.sprite.pacman.PacMan;
import javafx.scene.canvas.Canvas;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public abstract class PacManMode {
    protected final PacMan pacMan;
    protected final GameState gameState;
    protected final ConfigsEx configs;

    public abstract void render(Canvas canvas);
    public abstract void update(Event<EventType> event);
    public boolean ended() {
        return false;
    }
}
