package com.ahmedaraby.game.pacman.sound;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import javafx.scene.media.AudioClip;

public class DemolishingSoundPlayer {

    private AudioClip pacManDying;

    public DemolishingSoundPlayer(ConfigsEx configs) {
        // load pacman dying audio clip
        final String pacManDyingClipRelativePath = configs.PAC_MAN_DYING_CLIP_PATH();
        final String pacManDyingClipAbsolutePath = getClass().getResource(pacManDyingClipRelativePath).toString();
        pacManDying = new AudioClip(pacManDyingClipAbsolutePath);
    }

    public void playPacManDyingSound() {
        pacManDying.play();
    }
}
