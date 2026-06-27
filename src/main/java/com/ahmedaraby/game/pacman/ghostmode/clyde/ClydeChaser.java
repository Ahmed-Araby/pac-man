package com.ahmedaraby.game.pacman.ghostmode.clyde;

import com.ahmedaraby.game.pacman.config.Configs;
import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.constant.SpriteFileNameC;
import com.ahmedaraby.game.pacman.ghostmode.Chaser;
import com.ahmedaraby.game.pacman.ghostmode.navigation.GhostNavigator;
import com.ahmedaraby.game.pacman.ghostmode.navigation.ShortestPathNavigator;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import com.ahmedaraby.game.pacman.util.ghost.GhostUtil;
import com.ahmedaraby.jengine.animation.Animator;
import com.ahmedaraby.jengine.animation.DistanceBasedAnimator;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.sprite.SpriteRegistry;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ClydeChaser extends Chaser {

    private final GhostNavigator navigator;
    private final Animator animator;

    public ClydeChaser(Ghost ghost, GameState gameState, ConfigsEx configs, SpriteRegistry<String, Image> spriteRegistry, int[] activePeriodsSec) {
        super(ghost, gameState, configs, spriteRegistry, activePeriodsSec);

        final String frame1Path = String.format(SpriteFileNameC.GHOST_SPRITE_PATH_TEMPLATE, SpriteFileNameC.CLYDE_FOLDER, SpriteFileNameC.CLYDE_FRAME_1_FILE_NAME);
        final String frame2Path = String.format(SpriteFileNameC.GHOST_SPRITE_PATH_TEMPLATE, SpriteFileNameC.CLYDE_FOLDER, SpriteFileNameC.CLYDE_FRAME_2_FILE_NAME);
        final Image[] frames = loadSprites(new String[]{frame1Path, frame2Path});
        animator = new DistanceBasedAnimator(new double[]{
                configs.GHOST_CLYDE_FIRST_FRAME_DISTANCE(),
                configs.GHOST_CLYDE_SECOND_FRAME_DISTANCE()
        }, frames);
        navigator = new ShortestPathNavigator();
    }

    @Override
    public void render(Canvas canvas) {
        final GraphicsContext con = canvas.getGraphicsContext2D();
        con.drawImage(animator.getFrame(), ghost.getCol(), ghost.getRow());
    }

    @Override
    public void move() {
        final Coordinate targetCord = gameState.getPacMan().getTopLeftCorner();
        final Coordinate ghostCord = ghost.getTopLeftCorner();

        final DirectionsE newDir = navigator.nextMoveDirection(ghostCord, targetCord);

        final Coordinate ghostNewCord = GhostUtil.move(ghostCord, newDir.toVector());
        ghost.setTopLeftCorner(ghostNewCord);
        ghost.setDir(newDir);

        if (newDir != DirectionsE.STILL) {
            animator.stride(configs.GHOST_CLYDE_SPEED() / Configs.FRAMES_PER_SEC_FOR_GHOST_STRIDE);
        }
    }


}
