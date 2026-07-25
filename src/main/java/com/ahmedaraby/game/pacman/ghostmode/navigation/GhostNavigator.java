package com.ahmedaraby.game.pacman.ghostmode.navigation;

import com.ahmedaraby.game.pacman.sprite.MovingSprite;
import com.ahmedaraby.game.pacman.sprite.Sprite;

public interface GhostNavigator {

    double calcDist(MovingSprite sprite, Sprite targetSprite);
}
