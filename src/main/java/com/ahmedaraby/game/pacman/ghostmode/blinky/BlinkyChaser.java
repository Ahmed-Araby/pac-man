package com.ahmedaraby.game.pacman.ghostmode.blinky;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
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
import com.ahmedaraby.game.pacman.ghostmode.Chaser;
import com.ahmedaraby.game.pacman.ghostmode.navigation.GhostNavigator;
import com.ahmedaraby.game.pacman.ghostmode.navigation.ShortestPathNavigator;
import com.ahmedaraby.game.pacman.util.ghost.GhostUtil;

public class BlinkyChaser extends Chaser {

    private final Animator animator;
    private final GhostNavigator navigator;

    public BlinkyChaser(Ghost ghost, GameState gameState, ConfigsEx configs, SpriteRegistry<String, Image> spriteRegistry, int[] activePeriodsSec) {
        super(ghost, gameState, configs, spriteRegistry, activePeriodsSec);

        final Image[] frames = loadSprites();
        this.animator = new DistanceBasedAnimator(
                new double[]{configs.GHOST_BLINK_FIRST_FRAME_DISTANCE(), configs.GHOST_BLINK_SECOND_FRAME_DISTANCE()}, frames);
        this.navigator = new ShortestPathNavigator();
    }

    @Override
    public void render(Canvas canvas) {
        canvas.getGraphicsContext2D().drawImage(animator.getFrame(), ghost.getCol(), ghost.getRow());
    }

    @Override
    public void move() {
        final Coordinate ghostCurrCord = new Coordinate(ghost.getRow(), ghost.getCol());
        final Coordinate pacmanCurrCord = gameState.getPacMan().getTopLeftCorner();
        final DirectionsE directionsE = navigator.nextMoveDirection(ghostCurrCord, pacmanCurrCord);
        final Coordinate ghostNewCord = GhostUtil.move(ghostCurrCord, directionsE);

        ghost.setDir(directionsE);
        ghost.setRow(ghostNewCord.getRow());
        ghost.setCol(ghostNewCord.getCol());

        if(directionsE != DirectionsE.STILL) {
            animator.stride(configs.GHOST_BLINKY_SPEED() / Configs.FRAMES_PER_SEC_FOR_GHOST_STRIDE);
        }

    }


    private Image[] loadSprites() {
        final String frame1Path = String.format(SpriteFileNameC.GHOST_SPRITE_PATH_TEMPLATE, SpriteFileNameC.BLINKY_FOLDER, SpriteFileNameC.BLINKY_FRAME_1_FILE_NAME);
        final String frame2Path = String.format(SpriteFileNameC.GHOST_SPRITE_PATH_TEMPLATE, SpriteFileNameC.BLINKY_FOLDER, SpriteFileNameC.BLINKY_FRAME_2_FILE_NAME);
        final Image frame1 = spriteRegistry.get(frame1Path);
        final Image frame2 = spriteRegistry.get(frame2Path);
        return new Image[]{frame1, frame2};
    }
}
