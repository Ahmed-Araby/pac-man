package com.ahmedaraby.game.pacman.ghostmode;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.ghostmode.navigation.TargetNavigator;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.entity.Vector;
import com.ahmedaraby.jengine.sprite.SpriteRegistry;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Scattered extends TemporalGhostMode {
    protected Coordinate target;
    private TargetNavigator targetNavigator;

    public Scattered(Ghost ghost, GameState gameState, ConfigsEx configs, SpriteRegistry<String, Image> spriteRegistry, int[] activePeriodsSec, double[] frameDistance) {
        super(ghost, gameState, configs, spriteRegistry, activePeriodsSec, frameDistance);
        targetNavigator = new TargetNavigator(configs);
    }

    @Override
    public void render(Canvas canvas) {
        final GraphicsContext con = canvas.getGraphicsContext2D();
        con.drawImage(animator.getFrame(), ghost.getCol(), ghost.getRow());
    }

    @Override
    public void move() {
        final Vector newDir = targetNavigator.calcDir(ghost, target);
        moveAt(newDir);
    }
}
