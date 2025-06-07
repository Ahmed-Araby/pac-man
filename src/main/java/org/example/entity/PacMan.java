package org.example.entity;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.example.PacManSpritePath;


public class PacMan implements Sprite{
    final Image pacOM = new Image(getClass().getResource(PacManSpritePath.OPEN_MOUSE).toExternalForm());
    final ImageView pacOMView = new ImageView(pacOM);

    @Override
    public void render(Pane rootPane) {
        pacOMView.setTranslateX(0);
        pacOMView.setTranslateY(0);
        rootPane.getChildren().add(pacOMView);
    }

    @Override
    public void move() {
        pacOMView.setTranslateX(200);
    }
}
