package org.example.scene;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.example.collision.PacManToSugarCollisionDetection;
import org.example.collision.PacManToSuperSugarCollisionDetection;
import org.example.collision.PacManToWallCollisionDetection;
import org.example.constant.ColorConstants;
import org.example.constant.Dimensions;
import org.example.constant.DirectionsE;
import org.example.event.EventManager;
import org.example.event.EventType;
import org.example.event.PacManMovementEvent;
import org.example.sound.SoundPlayer;
import org.example.sprite.Maze;
import org.example.sprite.PacMan;
import org.example.entity.Coordinate;
import org.example.sprite.Sugar;

public class GamePlayGameScene implements GameScene {
    final PacMan pacMan;
    final Maze maze;
    final Sugar sugar;

    final Pane pane;
    final Canvas canvas;
    final Scene scene;

    final EventManager eventManager;
    final SoundPlayer soundPlayer;

    public GamePlayGameScene() {
        maze = new Maze();
        eventManager = new EventManager();

        final Coordinate emptyCellPos = maze.getEmptyMazePosition();

        final PacManToWallCollisionDetection pacManToWallCollisionDetection = new PacManToWallCollisionDetection(maze.getGameMaze());
        final PacManToSugarCollisionDetection pacManToSugarCollisionDetection = new PacManToSugarCollisionDetection(maze.getGameMaze(), eventManager);
        final PacManToSuperSugarCollisionDetection pacManToSuperSugarCollisionDetection = new PacManToSuperSugarCollisionDetection(maze.getGameMaze(), eventManager);
        pacMan = new PacMan(emptyCellPos.getCol(), emptyCellPos.getRow(), pacManToWallCollisionDetection, pacManToSugarCollisionDetection, pacManToSuperSugarCollisionDetection);

        sugar = new Sugar(maze.getGameMaze());
        soundPlayer = new SoundPlayer();

        // event subscription
        eventManager.subscribe(EventType.PAC_MAN_SUGAR_COLLISION, soundPlayer);
        eventManager.subscribe(EventType.PAC_MAN_SUGAR_COLLISION, sugar);

        eventManager.subscribe(EventType.PAC_MAN_SUPER_SUGAR_COLLISION, soundPlayer);
        eventManager.subscribe(EventType.PAC_MAN_SUPER_SUGAR_COLLISION, sugar);

        eventManager.subscribe(EventType.PAC_MAN_MOVEMENT, pacMan);

        canvas = new Canvas(Dimensions.CANVAS_WIDTH_PIXELS, Dimensions.CANVAS_HEIGHT_PIXELS);
        final GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(ColorConstants.CANVAS_COLOR);
        context.fillRect(0, 0, Dimensions.CANVAS_WIDTH_PIXELS, Dimensions.CANVAS_HEIGHT_PIXELS);

        pane = new Pane(canvas);
        scene = new Scene(pane);

        scene.setOnKeyPressed((event) -> {
            publishEvent(event);
        });
    }

    private void publishEvent(KeyEvent keyEvent) {
        try {
            final PacManMovementEvent event = new PacManMovementEvent(EventType.PAC_MAN_MOVEMENT, DirectionsE.from(keyEvent.getCode()), keyEvent.getSource());
            eventManager.notifySubscribers(event);
        } catch (Exception exc) {
            System.out.println("user input is not a valid pac man movement input");
        }
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
        sugar.render(canvas);
        context.setFill(Color.GRAY);
        context.fillOval(0, 0, Dimensions.PAC_MAN_DIAMETER_PIXELS, Dimensions.PAC_MAN_DIAMETER_PIXELS);

        pacMan.render(canvas);
    }
}
