package com.ahmedaraby.game.pacman.sprite;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.game.pacman.event.Event;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.entity.Vector;
import javafx.scene.canvas.Canvas;

import java.util.ArrayList;
import java.util.List;

public abstract class MachineControlledSprite extends MovingSprite {
    public MachineControlledSprite(GameState gameState, ConfigsEx configs, SpriteE type, Coordinate cord, double width, double height, Vector dirV) {
        super(gameState, configs, type, cord, width, height, dirV);
    }

    public MovingSprite buildNextSprite(Vector dir) {
        final double stride = configs.PLAYGROUND_CELL_SIZE();
        final Coordinate nextCord = calcNextCord(stride, dir);
        return new MachineControlledSprite(gameState, configs, type,nextCord, getWidth(), getHeight(), dir) {
            @Override
            public void move(Event event) {
                throw new IllegalStateException("move behaviour is not implemented for the dummy NextSprite");
            }

            @Override
            public void render(Canvas canvas) {
                throw new IllegalStateException("render behaviour is not implemented for the dummy NextSprite");
            }
        };
    }

    private List<Vector> get90Directions() {
        if (Vector.RIGHT == dirV || Vector.LEFT == dirV) {
            return List.of(Vector.UP, Vector.DOWN);
        } else if (Vector.UP == dirV || Vector.DOWN == dirV) {
            return List.of(Vector.RIGHT, Vector.LEFT);
        }

        // in case the sprite is still
        return List.of(Vector.RIGHT, Vector.UP, Vector.DOWN, Vector.LEFT);
    }

    public List<Vector> getPossibleDirections() {
        List<Vector> possibleDirections = new ArrayList<>();
        if (isPossibleToMoveInSameDir()) {
            possibleDirections.add(getDirV());
        }
        get90Directions()
                .stream()
                .filter(this::isPossibleToMoveInNewDir)
                .forEach(possibleDirections::add);

        // if it is not possible to move in any same or perpendicular directions, then turn around.
        return possibleDirections.isEmpty() ? List.of(getDirV().flip180()) : possibleDirections;
    }
}
