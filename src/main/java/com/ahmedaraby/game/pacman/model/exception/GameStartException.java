package com.ahmedaraby.game.pacman.model.exception;

public class GameStartException extends SceneTransitionException {
    public GameStartException() {
        super(TransitionType.GAME_START);
    }
}
