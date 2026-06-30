package com.ahmedaraby.game.pacman.sprite;

import com.ahmedaraby.game.pacman.collision.M2SSpriteCollisionDetector;
import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.util.SpriteUtil;
import lombok.Getter;
import lombok.Setter;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.entity.Rectangle;
import com.ahmedaraby.game.pacman.event.Event;
import com.ahmedaraby.game.pacman.model.CollisionReport;
import com.ahmedaraby.game.pacman.model.GameState;

import java.util.List;


@Getter
@Setter
public abstract class MovingSprite extends Sprite {

    protected DirectionsE dir;

    public MovingSprite(GameState gameState, ConfigsEx configs, SpriteE type, Coordinate cord, double width, double height, DirectionsE dir) {
        super(gameState, configs, type, cord, width, height);
        this.dir = dir;
    }


    public abstract void move(Event event);

    protected boolean isCollidingWithWallOrGhostHWall(Coordinate topLeftCorner) {
        final Rectangle rect = SpriteUtil.toRect(topLeftCorner, type);
        List<CollisionReport> collisionReports = M2SSpriteCollisionDetector.detect(rect, List.of(SpriteE.WALL, SpriteE.GHOST_HOUSE_WALL));
        return !collisionReports.isEmpty();
    }
}
