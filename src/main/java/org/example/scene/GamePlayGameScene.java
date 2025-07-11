package org.example.scene;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.example.collision.PacManToSugarCollisionDetection;
import org.example.collision.PacManToSuperSugarCollisionDetection;
import org.example.collision.PacManToWallCollisionDetection;
import org.example.config.GameConfig;
import org.example.constant.ColorConstants;
import org.example.constant.Dimensions;
import org.example.event.manager.EventManager;
import org.example.event.EventType;
import org.example.event.manager.SyncEventManager;
import org.example.input.JavaFXInputHandler;
import org.example.input.JavaFXUserInputHandler;
import org.example.sound.SoundPlayer;
import org.example.sprite.Maze;
import org.example.sprite.PacMan;
import org.example.entity.Coordinate;
import org.example.sprite.Sugar;
import org.example.util.debug.DebugUtil;

public class GamePlayGameScene implements GameScene {
    // sprites
    final PacMan pacMan;
    final Maze maze;
    final Sugar sugar;

    // javaFX
    final Pane pane;
    final Canvas canvas;
    final Scene scene;

    // game engine
    final EventManager eventManager;
    final SyncEventManager syncEventManager;

    final SoundPlayer soundPlayer;
    final JavaFXInputHandler javaFXInputHandler;

    // collision detection
    private final PacManToSugarCollisionDetection pacManToSugarCollisionDetection;
    private final PacManToSuperSugarCollisionDetection pacManToSuperSugarCollisionDetection;
    private final PacManToWallCollisionDetection pacManToWallCollisionDetection;

    public GamePlayGameScene() {
        // game engine
        eventManager = new EventManager();
        syncEventManager = new SyncEventManager();
        soundPlayer = new SoundPlayer();
        javaFXInputHandler = new JavaFXUserInputHandler(syncEventManager);

        // sprites
        maze = new Maze();
        final Coordinate emptyCellPos = maze.getEmptyMazePosition();
        pacMan = new PacMan(emptyCellPos.getCol(), emptyCellPos.getRow(), eventManager, syncEventManager);
        sugar = new Sugar(maze.getGameMaze());

        // collision detection
        pacManToWallCollisionDetection = new PacManToWallCollisionDetection(maze.getGameMaze(), syncEventManager);
        pacManToSugarCollisionDetection = new PacManToSugarCollisionDetection(maze.getGameMaze(), eventManager);
        pacManToSuperSugarCollisionDetection = new PacManToSuperSugarCollisionDetection(maze.getGameMaze(), eventManager);


        // javaFX setup
        canvas = new Canvas(Dimensions.CANVAS_WIDTH_PIXELS, Dimensions.CANVAS_HEIGHT_PIXELS);
        pane = new Pane(canvas);
        scene = new Scene(pane);
        scene.setOnKeyPressed((event) -> {
            javaFXInputHandler.handleKeyPressedEvent(event);
        });

        // game engine
        registerEventSubscribers();
        registerSubscribersForSyncEvents();
    }

    private void registerEventSubscribers() {
        if (soundPlayer == null || sugar == null || pacMan == null) {
            throw new IllegalStateException("can't register null sprite for event subscription, SoundPlayer, Sugar and PacMan sprites must be defined");
        }

        eventManager.subscribe(EventType.PAC_MAN_SUGAR_COLLISION, soundPlayer);
        eventManager.subscribe(EventType.PAC_MAN_SUGAR_COLLISION, sugar);

        eventManager.subscribe(EventType.PAC_MAN_SUPER_SUGAR_COLLISION, soundPlayer);
        eventManager.subscribe(EventType.PAC_MAN_SUPER_SUGAR_COLLISION, sugar);

        eventManager.subscribe(EventType.PAC_MAN_CURRENT_LOCATION, pacManToSugarCollisionDetection);
        eventManager.subscribe(EventType.PAC_MAN_CURRENT_LOCATION, pacManToSuperSugarCollisionDetection);
    }

    private void registerSubscribersForSyncEvents() {
        if (pacMan == null || pacManToWallCollisionDetection == null) {
            throw new IllegalStateException("can't register null objects for sync event subscription, PacMan, and pacManToWallCollisionDetection must be defined");

        }
        syncEventManager.subscribe(EventType.PAC_MAN_MOVEMENT_REQUEST, pacMan);
        syncEventManager.subscribe(EventType.PAC_MAN_MOVEMENT_ATTEMPT, pacManToWallCollisionDetection);
        syncEventManager.subscribe(EventType.PAC_MAN_MOVEMENT_ATTEMPT_APPROVED, pacMan);
        syncEventManager.subscribe(EventType.PAC_MAN_MOVEMENT_ATTEMPT_DENIED, pacMan);
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

        if(GameConfig.isDebugModeOn()) {
            DebugUtil.drawDummyPacman(context, 0, 0, Dimensions.PAC_MAN_DIAMETER_PIXELS, Dimensions.PAC_MAN_DIAMETER_PIXELS, Color.GRAY);
        }

        pacMan.render(canvas);
    }
}
