package com.ahmedaraby.game.pacman.scene;

import com.ahmedaraby.game.pacman.collision.CollisionSystem;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.ghost.Blinky;
import com.ahmedaraby.game.pacman.sprite.playground.GhostHouseS;
import com.ahmedaraby.game.pacman.sprite.playground.Maze;
import com.ahmedaraby.game.pacman.sprite.playground.Sugar;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import com.ahmedaraby.game.pacman.config.GameConfig;
import com.ahmedaraby.game.pacman.constant.ColorC;
import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.event.manager.EventManager;
import com.ahmedaraby.game.pacman.event.EventType;
import com.ahmedaraby.game.pacman.event.manager.SyncEventManager;
import com.ahmedaraby.game.pacman.input.JavaFXInputHandler;
import com.ahmedaraby.game.pacman.input.JavaFXUserInputHandler;
import com.ahmedaraby.game.pacman.playground.Playground;
import com.ahmedaraby.game.pacman.sound.SoundPlayer;
import com.ahmedaraby.game.pacman.sprite.PacMan;
import com.ahmedaraby.game.pacman.sprite.ghost.Inky;
import com.ahmedaraby.game.pacman.sprite.playground.SuperSugar;
import com.ahmedaraby.game.pacman.util.debug.DebugUtil;


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
        gameState.setMaze(maze);
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
