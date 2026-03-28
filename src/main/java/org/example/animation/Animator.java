package org.example.animation;

import javafx.scene.image.Image;

public interface Animator {

    void stride(double traveled);
    Image getFrame(double traveled);
    Image getFrame();
}
