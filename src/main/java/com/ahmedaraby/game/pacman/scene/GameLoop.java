package com.ahmedaraby.game.pacman.scene;

import com.ahmedaraby.game.pacman.model.exception.GameOverException;
import com.ahmedaraby.game.pacman.model.exception.SceneTransitionException;
import javafx.animation.AnimationTimer;
import javafx.stage.Stage;
import lombok.SneakyThrows;


public class GameLoop extends AnimationTimer {
    private Stage stage;
    private GameScene activeScene;

    @SneakyThrows
    public GameLoop(Stage stage) {
        this.stage = stage;
        this.stage.setTitle("Pac Man");

        activeScene = new GamePlayGameScene();
        this.stage.setScene(activeScene.getScene());
        this.stage.show();
    }

    @Override
    public void handle(long l) {
        try {
            activeScene.update();
            activeScene.render();
        } catch (SceneTransitionException exc) {
            System.out.println("scene transition, " + exc);
            transitionScene(exc);
        }
    }

    private void transitionScene(SceneTransitionException exc) {
        if (activeScene instanceof GamePlayGameScene) {
            transitionFromGamePlayScene((GameOverException) exc);
        } else if (activeScene instanceof DemolishingScene) {
            transitionFromDemolishingScene();
        }
    }

    private void transitionFromGamePlayScene(GameOverException exc) {
        activeScene = new DemolishingScene(activeScene.getCanvas(), activeScene.getPane(), activeScene.getScene(),
                exc.getGameState(), exc.getConfigs());
        stage.setScene(activeScene.getScene());
        stage.show();
    }

    @SneakyThrows
    private void transitionFromDemolishingScene() {
        activeScene = new GamePlayGameScene();
        stage.setScene(activeScene.getScene());
        stage.show();
    }

}
