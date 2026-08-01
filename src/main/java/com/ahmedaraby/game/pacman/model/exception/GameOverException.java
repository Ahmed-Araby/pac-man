package com.ahmedaraby.game.pacman.model.exception;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.model.GameState;
import lombok.Getter;

@Getter
public class GameOverException extends SceneTransitionException {
    private final GameState gameState;
    private final ConfigsEx configs;

    public GameOverException(GameState gameState, ConfigsEx configs) {
        super(TransitionType.GAME_OVER);
        this.gameState = gameState;
        this.configs = configs;
    }
}
