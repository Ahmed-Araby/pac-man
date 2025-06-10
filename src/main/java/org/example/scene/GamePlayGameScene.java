package org.example.scene;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.example.collision.PacManToWallCollisionDetection;
import org.example.constant.ColorConstants;
import org.example.constant.Dimensions;
import org.example.entity.Maze;
import org.example.entity.PacMan;
import org.example.maze.Coordinate;

public class GamePlayGameScene implements GameScene {
    final PacMan pacMan;
    final Maze maze;

    final Pane pane;
    final Canvas canvas;
    final Scene scene;

    public GamePlayGameScene() {
        maze = new Maze();
        final Coordinate emptyCellPos = maze.getEmptyMazePosition();

        final PacManToWallCollisionDetection pacManToWallCollisionDetection = new PacManToWallCollisionDetection(maze.getGameMaze());

        pacMan = new PacMan(emptyCellPos.getCol(), emptyCellPos.getRow(), pacManToWallCollisionDetection);

        canvas = new Canvas(Dimensions.CANVAS_WIDTH_PIXELS, Dimensions.CANVAS_HEIGHT_PIXELS);
        final GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(ColorConstants.CANVAS_COLOR);
        context.fillRect(0, 0, Dimensions.CANVAS_WIDTH_PIXELS, Dimensions.CANVAS_HEIGHT_PIXELS);

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
        context.fillRect(0, 0, Dimensions.CANVAS_WIDTH_PIXELS, Dimensions.CANVAS_HEIGHT_PIXELS);


        maze.render(canvas);

        context.setFill(Color.GRAY);
        context.fillOval(0, 0, Dimensions.PAC_MAN_DIAMETER_PIXELS, Dimensions.PAC_MAN_DIAMETER_PIXELS);

        pacMan.render(canvas);




    }
}
