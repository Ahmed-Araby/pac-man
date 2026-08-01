package com.ahmedaraby.game.pacman.model.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
public abstract class SceneTransitionException extends RuntimeException {
    @Getter
    private TransitionType type;
}
