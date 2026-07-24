package com.ahmedaraby.game.pacman.ghostmode.pinky;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.constant.SpriteFileNameC;
import com.ahmedaraby.game.pacman.ghostmode.Chaser;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import com.ahmedaraby.jengine.entity.Coordinate;
import com.ahmedaraby.jengine.entity.Line;
import com.ahmedaraby.jengine.entity.Vector;
import com.ahmedaraby.jengine.sprite.SpriteRegistry;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class PinkyChaser extends Chaser {

    public PinkyChaser(Ghost ghost, GameState gameState, ConfigsEx configs, SpriteRegistry<String, Image> spriteRegistry, int[] activePeriodsSec) {
        super(ghost, gameState, configs, spriteRegistry, activePeriodsSec,
                new double[]{configs.GHOST_PINKY_FIRST_FRAME_DISTANCE(), configs.GHOST_PINKY_SECOND_FRAME_DISTANCE()});
    }

    @Override
    public void render(Canvas canvas) {
        GraphicsContext con = canvas.getGraphicsContext2D();
        con.drawImage(animator.getFrame(), ghost.getCol(), ghost.getRow());
    }

    @Override
    public void move() {
        // calculate target
        final Coordinate pacManCord = gameState.getPacMan().getTopLeftCorner();
        final Vector pacManDir = gameState.getPacMan().getDirV();
        // calculate the tile 4 steps ahead of pacman, and force it to be within the playground
        final Vector pacManDirScaled = pacManDir.scale(4 * configs.PLAYGROUND_CELL_SIZE());
        final Coordinate lookAheadCord = pacManCord.add(pacManDirScaled.getX(), pacManDirScaled.getY());
        final Line lookAheadLine = new Line(pacManCord, lookAheadCord).trim(gameState.getMaze().getRect());
        final Coordinate target = lookAheadLine.getEnd();

        moveTo(target);
    }

    @Override
    protected Image[] loadSprites() {
        final String frame1Path = String.format(SpriteFileNameC.GHOST_SPRITE_PATH_TEMPLATE, SpriteFileNameC.PINKY_FOLDER, SpriteFileNameC.PINKY_FRAME_1_FILE_NAME);
        final String frame2Path = String.format(SpriteFileNameC.GHOST_SPRITE_PATH_TEMPLATE, SpriteFileNameC.PINKY_FOLDER, SpriteFileNameC.PINKY_FRAME_2_FILE_NAME);
        final Image frame1 = spriteRegistry.get(frame1Path);
        final Image frame2 = spriteRegistry.get(frame2Path);
        return new Image[] {frame1, frame2};
    }
}
