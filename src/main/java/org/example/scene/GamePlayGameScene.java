package org.example.scene;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.example.collision.sprite.CollisionSystem;
import org.example.config.GameConfig;
import org.example.constant.ColorC;
import org.example.constant.DimensionsC;
import org.example.event.manager.EventManager;
import org.example.event.EventType;
import org.example.event.manager.SyncEventManager;
import org.example.input.JavaFXInputHandler;
import org.example.input.JavaFXUserInputHandler;
import org.example.maze.Playground;
import org.example.model.GameState;
import org.example.sound.SoundPlayer;
import org.example.sprite.playground.GhostHouseS;
import org.example.sprite.playground.Maze;
import org.example.sprite.PacMan;
import org.example.sprite.playground.Sugar;
import org.example.sprite.ghost.Blinky;
import org.example.sprite.ghost.Inky;
import org.example.sprite.playground.SuperSugar;
import org.example.util.debug.DebugUtil;


public class GamePlayGameScene implements GameScene {
    private final GameState gameState = new GameState();

    // sprites
    GhostHouseS ghostHouseS;
    Maze maze;
    Sugar sugar;
    SuperSugar superSugar;
    PacMan pacMan;

    // ghosts
    private Blinky blinky;
    private Inky inky;

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
    private final CollisionSystem collisionSystem;

    public GamePlayGameScene() {
        // init
        Playground.init();

        // game engine
        eventManager = new EventManager();
        syncEventManager = new SyncEventManager();
        soundPlayer = new SoundPlayer();
        javaFXInputHandler = new JavaFXUserInputHandler(syncEventManager);
        collisionSystem = new CollisionSystem(gameState, eventManager);

        createNonGhostSprites();
        createGhostsSprites();

        setGameState();

        registerEventSubscribers();
        registerSubscribersForSyncEvents();

        initNonGhostSprites();
        initGhostSprites();

        // javaFX setup
        canvas = new Canvas(DimensionsC.CANVAS_WIDTH_PIXELS, DimensionsC.CANVAS_HEIGHT_PIXELS);
        pane = new Pane(canvas);
        scene = new Scene(pane);
        scene.setOnKeyPressed((event) -> {
            javaFXInputHandler.handleKeyPressedEvent(event);
        });
    }

    private void createNonGhostSprites() {
        ghostHouseS = new GhostHouseS(gameState);
        maze = new Maze(gameState);
        pacMan = new PacMan(gameState);
        // sugar Sprite has to be instantiated before SuperSugar Sprite
        sugar = new Sugar(gameState);
        superSugar = new SuperSugar(gameState);
    }

    private void createGhostsSprites() {
        blinky = new Blinky(gameState);
        inky = new Inky(gameState);
    }

    private void setGameState() {
        gameState.setPacMan(pacMan);
        gameState.setGhostHouseS(ghostHouseS);
        gameState.addGhost(blinky);
        gameState.addGhost(inky);
    }

    private void registerEventSubscribers() {
        if (soundPlayer == null || sugar == null || pacMan == null) {
            throw new IllegalStateException("can't register null sprite for event subscription, SoundPlayer, Sugar and PacMan sprites must be defined");
        }

        eventManager.subscribe(EventType.PAC_MAN_SUGAR_COLLISION, soundPlayer);
        eventManager.subscribe(EventType.PAC_MAN_SUGAR_COLLISION, sugar);

        eventManager.subscribe(EventType.PAC_MAN_SUPER_SUGAR_COLLISION, soundPlayer);
        eventManager.subscribe(EventType.PAC_MAN_SUPER_SUGAR_COLLISION, sugar);

        eventManager.subscribe(EventType.PAC_MAN_SUPER_SUGAR_COLLISION, blinky);
        eventManager.subscribe(EventType.PAC_MAN_SUPER_SUGAR_COLLISION, inky);

        eventManager.subscribe(EventType.PAC_MAN_GHOST_COLLISION, blinky);
        eventManager.subscribe(EventType.PAC_MAN_GHOST_COLLISION, inky);
    }

    private void registerSubscribersForSyncEvents() {
        if (pacMan == null) {
            throw new IllegalStateException("can't register null objects for sync event subscription, PacMan, and pacManToWallCollisionDetection must be defined");

        }
        syncEventManager.subscribe(EventType.PAC_MAN_MOVEMENT_REQUEST, pacMan);
    }

    private void initNonGhostSprites() {
        ghostHouseS.init();
        maze.init();
        pacMan.init();
        sugar.init();
    }

    private void initGhostSprites() {
        blinky.init();
        inky.init();
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public void render() {
        final GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(ColorC.CANVAS_COLOR);
        context.fillRect(0, 0, DimensionsC.CANVAS_WIDTH_PIXELS, DimensionsC.CANVAS_HEIGHT_PIXELS);


        maze.render(canvas);
        ghostHouseS.render(canvas);
        sugar.render(canvas);
        superSugar.render(canvas);

        if(GameConfig.isDebugModeOn()) {
            DebugUtil.drawDummyPacman(context, 0, 0, DimensionsC.PAC_MAN_DIAMETER_PIXELS, DimensionsC.PAC_MAN_DIAMETER_PIXELS, Color.GRAY);
        }

        pacMan.render(canvas);
        blinky.render(canvas);
        inky.render(canvas);
    }

    @Override
    public void update() {
        blinky.move(null);
        inky.move(null);
        collisionSystem.detect();
    }
}
