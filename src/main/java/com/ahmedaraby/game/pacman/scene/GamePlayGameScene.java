package com.ahmedaraby.game.pacman.scene;

import com.ahmedaraby.game.pacman.collision.CollisionSystem;
import com.ahmedaraby.game.pacman.config.ConfigsLoader;
import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.event.Event;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sprite.ghost.Blinky;
import com.ahmedaraby.game.pacman.sprite.ghost.Clyde;
import com.ahmedaraby.game.pacman.sprite.ghost.Pinky;
import com.ahmedaraby.game.pacman.sprite.playground.GhostHouseS;
import com.ahmedaraby.game.pacman.sprite.playground.Maze;
import com.ahmedaraby.game.pacman.sprite.playground.Sugar;
import com.ahmedaraby.game.pacman.util.FxSpriteRegistry;
import com.ahmedaraby.jengine.sprite.SpriteRegistry;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import com.ahmedaraby.game.pacman.config.GameConfig;
import com.ahmedaraby.game.pacman.constant.ColorC;
import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.event.EventType;
import com.ahmedaraby.jengine.event.SyncEventManager;
import com.ahmedaraby.game.pacman.input.JavaFXInputHandler;
import com.ahmedaraby.game.pacman.input.JavaFXUserInputHandler;
import com.ahmedaraby.game.pacman.playground.Playground;
import com.ahmedaraby.game.pacman.sound.SoundPlayer;
import com.ahmedaraby.game.pacman.sprite.PacMan;
import com.ahmedaraby.game.pacman.sprite.ghost.Inky;
import com.ahmedaraby.game.pacman.sprite.playground.SuperSugar;
import com.ahmedaraby.game.pacman.util.debug.DebugUtil;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;


public class GamePlayGameScene implements GameScene {
    private final ConfigsEx configs;
    private final GameState gameState = new GameState();
    private final SpriteRegistry<String, Image> spriteRegistry = new FxSpriteRegistry();

    // sprites
    GhostHouseS ghostHouseS;
    Maze maze;
    Sugar sugar;
    SuperSugar superSugar;
    PacMan pacMan;

    // ghosts
    private Blinky blinky;
    private Inky inky;
    private Pinky pinky;
    private Clyde clyde;

    // javaFX
    final Pane pane;
    final Canvas canvas;
    final Scene scene;

    // game engine
    final SyncEventManager<EventType, Event<EventType>> syncEventManager;

    final SoundPlayer soundPlayer;
    final JavaFXInputHandler javaFXInputHandler;

    // collision detection
    private final CollisionSystem collisionSystem;

    public GamePlayGameScene() throws FileNotFoundException, URISyntaxException {
        configs = new ConfigsLoader().load();

        // init
        Playground.init();

        // game engine
        syncEventManager = new SyncEventManager();
        soundPlayer = new SoundPlayer();
        javaFXInputHandler = new JavaFXUserInputHandler(syncEventManager);
        collisionSystem = new CollisionSystem(gameState, syncEventManager);

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
        ghostHouseS = new GhostHouseS(gameState, configs);
        maze = new Maze(gameState, configs);
        pacMan = new PacMan(gameState, configs);
        // sugar Sprite has to be instantiated before SuperSugar Sprite
        sugar = new Sugar(gameState, configs);
        superSugar = new SuperSugar(gameState, configs);
    }

    private void createGhostsSprites() {
        blinky = new Blinky(gameState, configs, spriteRegistry);
        inky = new Inky(gameState, configs, spriteRegistry);
        pinky = new Pinky(gameState, configs, spriteRegistry);
        clyde = new Clyde(gameState, configs, spriteRegistry);
    }

    private void setGameState() {
        gameState.setMaze(maze);
        gameState.setPacMan(pacMan);
        gameState.setGhostHouseS(ghostHouseS);

        // track ghosts
        gameState.addGhost(blinky);
        gameState.addGhost(inky);
        gameState.addGhost(pinky);
        gameState.addGhost(clyde);

    }

    private void registerEventSubscribers() {
        if (soundPlayer == null || sugar == null || pacMan == null) {
            throw new IllegalStateException("can't register null sprite for event subscription, SoundPlayer, Sugar and PacMan sprites must be defined");
        }

        syncEventManager.subscribe(EventType.PAC_MAN_SUGAR_COLLISION, soundPlayer);
        syncEventManager.subscribe(EventType.PAC_MAN_SUGAR_COLLISION, sugar);

        syncEventManager.subscribe(EventType.PAC_MAN_SUPER_SUGAR_COLLISION, soundPlayer);
        syncEventManager.subscribe(EventType.PAC_MAN_SUPER_SUGAR_COLLISION, superSugar);

        syncEventManager.subscribe(EventType.PAC_MAN_SUPER_SUGAR_COLLISION, blinky);
        syncEventManager.subscribe(EventType.PAC_MAN_SUPER_SUGAR_COLLISION, inky);
        syncEventManager.subscribe(EventType.PAC_MAN_SUPER_SUGAR_COLLISION, pinky);
        syncEventManager.subscribe(EventType.PAC_MAN_SUPER_SUGAR_COLLISION, clyde);

        syncEventManager.subscribe(EventType.PAC_MAN_GHOST_COLLISION, blinky);
        syncEventManager.subscribe(EventType.PAC_MAN_GHOST_COLLISION, inky);
        syncEventManager.subscribe(EventType.PAC_MAN_GHOST_COLLISION, pinky);
        syncEventManager.subscribe(EventType.PAC_MAN_GHOST_COLLISION, clyde);
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
        pinky.init();
        clyde.init();
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
        pinky.render(canvas);
        clyde.render(canvas);
    }

    @Override
    public void update() {
        pacMan.move(null);
        blinky.move(null);
        inky.move(null);
        pinky.move(null);
        clyde.move(null);

        collisionSystem.detect();
    }
}
