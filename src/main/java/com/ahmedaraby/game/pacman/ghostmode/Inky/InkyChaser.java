package com.ahmedaraby.game.pacman.ghostmode.Inky;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.jengine.animation.DistanceBasedAnimator;
import com.ahmedaraby.game.pacman.config.Configs;
import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.constant.SpriteFileNameC;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.entity.Rectangle;
import com.ahmedaraby.jengine.entity.Line;
import com.ahmedaraby.jengine.entity.Vector;
import com.ahmedaraby.game.pacman.ghostmode.navigation.GhostNavigator;
import com.ahmedaraby.game.pacman.ghostmode.navigation.ShortestPathNavigator;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.ghost.Blinky;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import com.ahmedaraby.game.pacman.util.ghost.GhostUtil;
import com.ahmedaraby.jengine.sprite.SpriteRegistry;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import com.ahmedaraby.game.pacman.ghostmode.Chaser;

public class InkyChaser extends Chaser {

    private final GhostNavigator navigator;
    private final DistanceBasedAnimator animator;

    public InkyChaser(Ghost ghost, GameState gameState, ConfigsEx configs, SpriteRegistry<String, Image> spriteRegistry, int[] activePeriodsSec) {
        super(ghost, gameState, configs, spriteRegistry, activePeriodsSec);
        final Image[] frames = loadSprites();
        navigator = new ShortestPathNavigator();
        animator = new DistanceBasedAnimator(
                new double[]{DimensionsC.GHOST_FIRST_LEG_MOVEMENT_DISTANCE_PIXELS, DimensionsC.GHOST_SECOND_LEG_MOVEMENT_DISTANCE_PIXELS}
                ,frames
        );
    }

    @Override
    public void render(Canvas canvas) {
        final GraphicsContext con = canvas.getGraphicsContext2D();
        con.drawImage(animator.getFrame(), ghost.getCol(), ghost.getRow());
    }

    @Override
    public void move() {
        // calculate the target coordinate
        final Coordinate interTile = calculateIntermediateTile();
        final Line blinky2InterTileLine = makeLineFromBlinkyToIntermediateTile(interTile);
        final Coordinate target = calculateTheTargetCoordinate(blinky2InterTileLine);

        // navigate the ghost to the target
        DirectionsE newDir = navigator.nextMoveDirection(ghost.getTopLeftCorner(), target);

        if (newDir != null) {
            animator.stride(DimensionsC.GHOST_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_STRIDE);

            final Coordinate newCord = GhostUtil.move(ghost.getTopLeftCorner(), newDir);

            ghost.setDir(newDir);
            ghost.setCol(newCord.getCol());
            ghost.setRow(newCord.getRow());
        }
    }

    private Coordinate calculateIntermediateTile() {
        final Coordinate pacManCord = gameState.getPacMan().getTopLeftCorner();
        final Vector pacManDir = gameState.getPacMan().getDir().toVector();
        return pacManCord.add(
                DimensionsC.PAC_MAN_STRIDE_PIXELS * 2 * pacManDir.getX(),
                DimensionsC.PAC_MAN_STRIDE_PIXELS * 2 * pacManDir.getY()
        );

    }

    private Line makeLineFromBlinkyToIntermediateTile(Coordinate interTile) {
        final Coordinate blinkyCord = gameState.getGhosts()
                .stream()
                .filter(ghost -> ghost instanceof Blinky)
                .findFirst()
                .get()
                .getTopLeftCorner();
        return new Line(blinkyCord, interTile);

    }

    private Coordinate calculateTheTargetCoordinate(Line blinky2InterTileLine) {
        final Rectangle wholePLayGround = new Rectangle(
                new Coordinate(0, 0), DimensionsC.CANVAS_WIDTH_PIXELS, DimensionsC.CANVAS_HEIGHT_PIXELS);
        return blinky2InterTileLine
                .scale(2) // double the line in the direction from blinky to the Intermediate tile
                .trim(wholePLayGround) // then trim the line such that the line endpoint are within the playground
                .getEnd();  // endpoint is the target coordinate for Inky
    }

    private Image[] loadSprites() {
        final String frame1Path = String.format(SpriteFileNameC.GHOST_SPRITE_PATH_TEMPLATE, SpriteFileNameC.INKY_FOLDER, SpriteFileNameC.INKY_FRAME_1_FILE_NAME);
        final String frame2Path = String.format(SpriteFileNameC.GHOST_SPRITE_PATH_TEMPLATE, SpriteFileNameC.INKY_FOLDER, SpriteFileNameC.INKY_FRAME_2_FILE_NAME);
        final Image frame1 = spriteRegistry.get(frame1Path);
        final Image frame2 = spriteRegistry.get(frame2Path);
        return new Image[] {frame1, frame2};
    }
}
