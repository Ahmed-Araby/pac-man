package com.ahmedaraby.game.pacman.ghostmode.common;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.jengine.entity.Vector;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import com.ahmedaraby.jengine.sprite.SpriteRegistry;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import com.ahmedaraby.jengine.animation.Animator;
import com.ahmedaraby.jengine.animation.DistanceBasedAnimator;
import com.ahmedaraby.game.pacman.config.Configs;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.constant.SpriteFileNameC;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.game.pacman.ghostmode.TemporalGhostMode;
import com.ahmedaraby.game.pacman.util.EnrichedThreadLocalRandom;
import com.ahmedaraby.game.pacman.util.ghost.GhostUtil;
import com.ahmedaraby.game.pacman.util.VectorUtil;
import com.ahmedaraby.game.pacman.util.ghost.FrightenedGhostUtil;

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
        final Coordinate sCord = new Coordinate(ghost.getRow(), ghost.getCol());
        final Vector currDir = VectorUtil.toVector(ghost.getDir());

        final List<Vector> eligibleDirections = FrightenedGhostUtil.getEligibleDirections(sCord, currDir);
        Vector newDirV;
        if (eligibleDirections.isEmpty()) {
            newDirV = VectorUtil.flip180(currDir);
        } else {
            final int randIndex = random.nextIntStartInclEndExcl(0, eligibleDirections.size());
            newDirV = eligibleDirections.get(randIndex);
        }

        final DirectionsE newDirE = VectorUtil.toDirection(newDirV);
        final Coordinate nCord = GhostUtil.move(sCord, newDirE);

        ghost.setDir(newDirE);
        ghost.setRow(nCord.getRow());
        ghost.setCol(nCord.getCol());

        animator.stride(configs.GHOST_SPEED() / Configs.FRAMES_PER_SEC_FOR_GHOST_STRIDE);
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
        final DirectionsE currDirE = ghost.getDir();
        final Vector dir = VectorUtil.toVector(currDirE);
        final Vector oppositeDir = VectorUtil.flip180(dir);
        final DirectionsE oppositeDirE = VectorUtil.toDirection(oppositeDir);
        ghost.setDir(oppositeDirE);
    }
}
