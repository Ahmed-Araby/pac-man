package org.example;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.constant.ColorConstants;
import org.example.constant.Dimensions;
import org.example.entity.Maze;
import org.example.entity.PacMan;


public class Main extends Application
{
    public static void main(String[] args) {
        System.out.println("main " + Thread.currentThread().threadId());
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Pac Man Game");
        final Canvas canvas = new Canvas(Dimensions.CANVAS_WIDTH, Dimensions.CANVAS_HEIGHT);

        final GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(ColorConstants.CANVAS_COLOR);
        context.fillRect(0, 0, Dimensions.CANVAS_WIDTH, Dimensions.CANVAS_HEIGHT);

        final Pane rootNode = new Pane(canvas);
        final Scene gamePlayScene = new Scene(rootNode);

        stage.setScene(gamePlayScene);
        stage.show();
        final PacMan pacMan = new PacMan();
        final Maze maze = new Maze();

        final AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                // clear the canvas
                context.setFill(ColorConstants.CANVAS_COLOR);
                context.fillRect(0, 0, Dimensions.CANVAS_WIDTH, Dimensions.CANVAS_HEIGHT);

                maze.render(canvas);
                pacMan.render(canvas);
            }
        };
        gameLoop.start();

        gamePlayScene.setOnKeyPressed((event) -> {
            pacMan.move(event);
        });
    }
}