package com.ahmedaraby.game.pacman.ghostmode;

import com.ahmedaraby.game.pacman.constant.SpriteFileNameC;
import com.ahmedaraby.game.pacman.ghostmode.pinky.PinkyScattered;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.ghost.Ghost;
import com.sun.javafx.logging.jfr.JFRInputEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import lombok.AllArgsConstructor;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public abstract class GhostMode {

    protected Ghost ghost;
    protected GameState gameState;

    public abstract void render(Canvas canvas);
    public abstract  void move();

    public void init() {
        System.out.println("GhostMode.init() method is not implemented for sprite " + this.getClass().getSimpleName());
    }
    public abstract void enter();
    public abstract boolean ended();



    protected Image[] loadSprites(GhostMode mode) {
        final String framePath = SpriteFileNameC.FORWARD_SLASH + SpriteFileNameC.SPRITE_FILES_DIRECTORY + SpriteFileNameC.FORWARD_SLASH + SpriteFileNameC.GHOST_FOLDER + SpriteFileNameC.FORWARD_SLASH + "%s" + SpriteFileNameC.FORWARD_SLASH + "%s";
        final List<Image> frames = new ArrayList<>();
        if (mode instanceof PinkyScattered) {
            final URL frame1Url = getClass().getResource(String.format(framePath, SpriteFileNameC.PINKY_FOLDER, SpriteFileNameC.PINKY_FRAME_1_FILE_NAME));
            final URL frame2Url = getClass().getResource(String.format(framePath, SpriteFileNameC.PINKY_FOLDER, SpriteFileNameC.PINKY_FRAME_2_FILE_NAME));
            frames.add(new Image(frame1Url.toString()));
            frames.add(new Image(frame2Url.toString()));
        } else {
            throw new IllegalStateException("GhostMode.loadSprites only supports loading Pinky sprites for now. more to come");
        }
        return frames.toArray(new Image[0]);
    }
}
