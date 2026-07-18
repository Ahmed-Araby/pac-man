package com.ahmedaraby.game.pacman.ghostmode.common;

import com.ahmedaraby.game.pacman.collision.M2SSpriteCollisionDetector;
import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.game.pacman.model.CollisionReport;
import com.ahmedaraby.jengine.entity.Rectangle;
import com.ahmedaraby.jengine.entity.Vector;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import com.ahmedaraby.jengine.sprite.SpriteRegistry;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import com.ahmedaraby.jengine.animation.Animator;
import com.ahmedaraby.jengine.animation.DistanceBasedAnimator;
import com.ahmedaraby.game.pacman.constant.SpriteFileNameC;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.game.pacman.ghostmode.TemporalGhostMode;
import com.ahmedaraby.game.pacman.util.EnrichedThreadLocalRandom;

import java.util.List;


public class Frightened extends TemporalGhostMode {

    private final Animator animator;
    private final EnrichedThreadLocalRandom random;

    public Frightened(Ghost ghost, GameState gameState, ConfigsEx configs, SpriteRegistry<String, Image> spriteRegistry, int[] activePeriodsSec) {
        super(ghost, gameState, configs, spriteRegistry, activePeriodsSec);

        Image[] frames = loadSprites();
        this.animator = new DistanceBasedAnimator(new double[]{configs.GHOST_FIRST_FRAME_DISTANCE(), configs.GHOST_SECOND_FRAME_DISTANCE()}, frames);
        this.random = new EnrichedThreadLocalRandom();
    }

    @Override
    public void enter() {
        super.enter();
        turnAround(ghost);
    }

    @Override
    public void render(Canvas canvas) {
        canvas.getGraphicsContext2D().drawImage(animator.getFrame(), ghost.getCol(), ghost.getRow());
    }

    @Override
    public void move() {
        final Vector currDir = ghost.getDirV();

        final List<Vector> possibleDirections = ghost.getPossibleDirections();
        Vector newDirV;
        if (possibleDirections.isEmpty()) {
            newDirV = currDir.flip180();
        } else {
            final int randIndex = random.nextIntStartInclEndExcl(0, possibleDirections.size());
            newDirV = possibleDirections.get(randIndex);
        }

        final double stride = ghost.calcStride(newDirV);
        ghost.move(stride, newDirV);
        animator.stride(stride);
    }

    @Override
    public void exit() {
        // explicitly do nothing
    }

    private Image[] loadSprites() {
        final String frame1Path = String.format(SpriteFileNameC.GHOST_SPRITE_PATH_TEMPLATE, SpriteFileNameC.GHOST_FRIGHTENED_FOLDER, SpriteFileNameC.GHOST_FRIGHTENED_FRAME_1_FILE_NAME);
        final String frame2Path = String.format(SpriteFileNameC.GHOST_SPRITE_PATH_TEMPLATE, SpriteFileNameC.GHOST_FRIGHTENED_FOLDER, SpriteFileNameC.GHOST_FRIGHTENED_FRAME_2_FILE_NAME);
        final Image frame1 = spriteRegistry.get(frame1Path);
        final Image frame2 = spriteRegistry.get(frame2Path);
        return new Image[]{frame1, frame2};
    }

    private void turnAround(Ghost ghost) {
        final Vector dir = ghost.getDirV();
        final Vector oppositeDir = dir.flip180();
        ghost.setDirV(oppositeDir);
    }
}
