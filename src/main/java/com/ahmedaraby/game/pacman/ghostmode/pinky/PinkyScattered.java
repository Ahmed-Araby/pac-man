package com.ahmedaraby.game.pacman.ghostmode.pinky;

import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.constant.SpriteFileNameC;
import com.ahmedaraby.game.pacman.ghostmode.Scattered;
import com.ahmedaraby.game.pacman.ghostmode.navigation.ShortestPathNavigator;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import com.ahmedaraby.jengine.animation.DistanceBasedAnimator;
import com.ahmedaraby.jengine.entity.Coordinate;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.net.URL;

public class PinkyScattered extends Scattered {

    public PinkyScattered(Ghost ghost, GameState gameState, int[] activePeriodsSec) {
        super(ghost, gameState, activePeriodsSec);

        this.target = new Coordinate(0, 0); // top left corner
        final Image[] frames = loadSprites();
        this.animator = new DistanceBasedAnimator(new double[]{
                DimensionsC.GHOST_FIRST_LEG_MOVEMENT_DISTANCE_PIXELS,
                DimensionsC.GHOST_SECOND_LEG_MOVEMENT_DISTANCE_PIXELS
        }, frames);
        this.navigator = new ShortestPathNavigator();
    }

    @Override
    public void render(Canvas canvas) {
        GraphicsContext con = canvas.getGraphicsContext2D();
        con.drawImage(animator.getFrame(), ghost.getCol(), ghost.getRow());
    }

    private Image[] loadSprites() {
        final URL frame1Url = getClass().getResource(String.format(SpriteFileNameC.GHOST_SPRITE_PATH_TEMPLATE, SpriteFileNameC.PINKY_FOLDER, SpriteFileNameC.PINKY_FRAME_1_FILE_NAME));
        final URL frame2Url = getClass().getResource(String.format(SpriteFileNameC.GHOST_SPRITE_PATH_TEMPLATE, SpriteFileNameC.PINKY_FOLDER, SpriteFileNameC.PINKY_FRAME_2_FILE_NAME));
        final Image frame1 = new Image(frame1Url.toString());
        final Image frame2 = new Image(frame2Url.toString());
        return new Image[] {frame1, frame2};
    }
}
