package org.example;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Main extends Application
{
    public static void main(String[] args) {
        System.out.println("main " + Thread.currentThread().threadId());
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Pac Man Game");
        final double[] counter = new double[1];
        counter[0] = 1;
        final Label label = new Label("start 1");
        final Scene rootNode = new Scene(label, 500, 500);
        stage.setScene(rootNode);
        stage.show();

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                counter[0] += ( (double) 1)/60;
                label.setText("start " + (int)counter[0]);
            }
        };

        gameLoop.start();
    }
}