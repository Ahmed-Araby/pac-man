package com.ahmedaraby.game.pacman.scene;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.constant.ColorC;
import com.ahmedaraby.game.pacman.model.GameState;
import com.ahmedaraby.game.pacman.sound.SoundPlayer;
import com.ahmedaraby.game.pacman.sprite.ghost.*;
import com.ahmedaraby.game.pacman.sprite.pacman.PacMan;
import com.ahmedaraby.game.pacman.sprite.playground.GhostHouseS;
import com.ahmedaraby.game.pacman.sprite.playground.Maze;
import com.ahmedaraby.game.pacman.sprite.playground.Sugar;
import com.ahmedaraby.game.pacman.sprite.playground.SuperSugar;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class DemolishingScene extends GameScene {
    private final GameState gameState;
    private final ConfigsEx configs;
    private final SoundPlayer soundPlayer;

    // sprites
    private final GhostHouseS ghostHouseS;
    private final Maze maze;
    private final Sugar sugar;
    private final SuperSugar superSugar;
    private final PacMan pacMan;

    // ghosts
    private Blinky blinky;
    private Inky inky;
    private Pinky pinky;
    private Clyde clyde;


    public DemolishingScene(Canvas canvas, Pane pane, Scene scene,
                            GameState gameState, ConfigsEx configs) {
        super(canvas, pane, scene);
        this.gameState = gameState;
        this.configs = configs;
        soundPlayer = new SoundPlayer(configs);

        // initiate sprites
        ghostHouseS = gameState.getGhostHouseS();
        maze = gameState.getMaze();
        pacMan = gameState.getPacMan();
        setGhosts();
        sugar = gameState.getSugar();
        superSugar = gameState.getSuperSugar();
        gameState.setPrevFrameEndedAt(System.nanoTime());
    }

    private void setGhosts() {
        for (Ghost ghost : gameState.getGhosts()) {
            if (ghost instanceof Blinky blinky) {
                this.blinky = blinky;
            } else if (ghost instanceof Inky inky) {
                this.inky = inky;
            } else if (ghost instanceof Pinky pinky) {
                this.pinky = pinky;
            } else if (ghost instanceof Clyde clyde) {
                this.clyde = clyde;
            } else {
                throw new IllegalStateException("unidentified ghost");
            }
        }
    }

    @Override
    public void render() {
        // erase canvas
        final GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(ColorC.CANVAS_COLOR);
        context.fillRect(0, 0, configs.CANVAS_WIDTH(), configs.CANVAS_HEIGHT());


        // playground
        maze.render(canvas);
        ghostHouseS.render(canvas);
        sugar.render(canvas);
        superSugar.render(canvas);

        pacMan.render(canvas);

        // ghosts
        blinky.render(canvas);
        inky.render(canvas);
        pinky.render(canvas);
        clyde.render(canvas);
        gameState.setPrevFrameEndedAt(System.nanoTime());
    }

    @Override
    public void update() {
        gameState.setCurrFrameStartedAt(System.nanoTime());
        pacMan.move(null);
    }
}
