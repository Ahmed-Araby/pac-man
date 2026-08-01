package com.ahmedaraby.game.pacman;

import com.ahmedaraby.game.pacman.scene.GameLoop;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application
{
    public static void main(String[] args) {
        System.out.println("main " + Thread.currentThread().threadId());
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        final AnimationTimer gameLoop = new GameLoop(stage);
        gameLoop.start();
    }
}