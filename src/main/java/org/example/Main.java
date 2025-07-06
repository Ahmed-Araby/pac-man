package org.example;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import org.example.config.GameConfig;
import org.example.scene.GamePlayGameScene;


public class Main extends Application
{
    public static void main(String[] args) {
        System.out.println("main " + Thread.currentThread().threadId());
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        GameConfig.load();

        stage.setTitle("Pac Man Game");
        GamePlayGameScene gamePlayGameScene = new GamePlayGameScene();
        stage.setScene(gamePlayGameScene.getScene());
        stage.show();

        final AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                // clear the canvas
                gamePlayGameScene.render();
            }
        };
        gameLoop.start();
    }
}