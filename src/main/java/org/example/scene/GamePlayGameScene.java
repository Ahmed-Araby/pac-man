package org.example.scene;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import org.example.constant.ColorConstants;
import org.example.constant.Dimensions;
import org.example.entity.Maze;
import org.example.entity.PacMan;

public class GamePlayGameScene implements GameScene {
    final PacMan pacMan;
    final Maze maze;

    final Pane pane;
    final Canvas canvas;
    final Scene scene;

    public GamePlayGameScene() {
        pacMan = new PacMan();
        maze = new Maze();

        canvas = new Canvas(Dimensions.CANVAS_WIDTH, Dimensions.CANVAS_HEIGHT);
        final GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(ColorConstants.CANVAS_COLOR);
        context.fillRect(0, 0, Dimensions.CANVAS_WIDTH, Dimensions.CANVAS_HEIGHT);

        pane = new Pane(canvas);
        scene = new Scene(pane);


        scene.setOnKeyPressed((event) -> {
            pacMan.move(event);
        });
    }


    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public void render() {
        final GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(ColorConstants.CANVAS_COLOR);
        context.fillRect(0, 0, Dimensions.CANVAS_WIDTH, Dimensions.CANVAS_HEIGHT);

        maze.render(canvas);
        pacMan.render(canvas);
    }
}
