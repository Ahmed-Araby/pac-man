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
import com.ahmedaraby.game.pacman.config.Configs;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.constant.SpriteFileNameC;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.game.pacman.ghostmode.TemporalGhostMode;
import com.ahmedaraby.game.pacman.util.EnrichedThreadLocalRandom;
import com.ahmedaraby.game.pacman.util.ghost.GhostUtil;

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
        final Coordinate currCord = ghost.getTopLeftCorner();
        final Vector currDir = ghost.getDir().toVector();

        final List<Vector> eligibleDirections = getEligibleDirections(currDir);
        Vector newDirV;
        if (eligibleDirections.isEmpty()) {
            newDirV = currDir.flip180();
        } else {
            final int randIndex = random.nextIntStartInclEndExcl(0, eligibleDirections.size());
            newDirV = eligibleDirections.get(randIndex);
        }

        final DirectionsE newDirE =  DirectionsE.fromVector(newDirV);
        final Coordinate nCord = GhostUtil.move(currCord, newDirE);

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
        final Vector dir = ghost.getDir().toVector();
        final Vector oppositeDir = dir.flip180();
        final DirectionsE oppositeDirE = DirectionsE.fromVector(oppositeDir);
        ghost.setDir(oppositeDirE);
    }

    private List<Vector> getEligibleDirections(Vector dir) {
        final List<Vector> allowedDirections = getAllowedDirections(dir);
        return allowedDirections
                .stream()
                .filter(this::isValidDir)
                .toList();
    }

    private boolean isValidDir(Vector dir) {
        final DirectionsE dirE = DirectionsE.fromVector(dir);
        final Coordinate candidateNextCord = GhostUtil.move(ghost.getTopLeftCorner(), dirE);
        final Rectangle gVRect = new Rectangle(candidateNextCord, ghost.getWidth(), ghost.getHeight());
        if (!gVRect.within(gameState.getMaze().getRect()))  {
            return false;
        }
        final List<CollisionReport> collisionReportOpt = M2SSpriteCollisionDetector.detect(gVRect, List.of(SpriteE.WALL, SpriteE.GHOST_HOUSE_WALL));
        return collisionReportOpt.isEmpty();
    }

    private List<Vector> getAllowedDirections(Vector currDir) {
        return Vector.fourD
                .stream()
                .filter(dir -> !currDir.isOpposite(dir))
                .toList();
    }
}
