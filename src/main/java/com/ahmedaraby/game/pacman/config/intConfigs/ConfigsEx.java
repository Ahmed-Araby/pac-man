package com.ahmedaraby.game.pacman.config.intConfigs;

import javafx.scene.paint.Color;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
public class ConfigsEx {
    private CanvasConfig canvas;
    private PlaygroundConfig playground;
    private PacManConfig pacman;
    private GhostConfig ghost;
    private GhostHouseConfig ghostHouse;
    private SugarConfig sugar;
    private SuperSugarConfig superSugar;
    private SoundConfig sound;


    public double CANVAS_WIDTH() {
        return canvas.getWidth();
    }

    public double CANVAS_HEIGHT() {
        return canvas.getHeight();
    }

    public double PLAYGROUND_CELL_SIZE() {
        return playground.getCellSize();
    }

    public Color PLAYGROUND_WALL_COLOR() {
        return playground.getWallColor();
    }

    public Color PLAYGROUND_BACKGROUND_COLOR() {
        return playground.getBackgroundColor();
    }

    public double PACMAN_SPEED() {
        return pacman.getSpeed();
    }

    public Color PACMAN_COLOR() {
        return pacman.getColor();
    }

    public double PACMAN_DIAMETER() {
        return pacman.getDiameter();
    }

    public double PACMAN_MOUTH_ANIMATION_COMPLETE_DIST() {
        return pacman.getMouth().getAnimation().getCompleteDist();
    }

    public List<Double> PACMAN_MOUTH_ANIMATION_PERCENTAGES() {
        return pacman.getMouth().getAnimation().getPercentages();
    }

    public double PACMAN_MOUTH_OPEN_DISTANCE()  {
        return PACMAN_MOUTH_ANIMATION_COMPLETE_DIST() * PACMAN_MOUTH_ANIMATION_PERCENTAGES().get(0);
    }

    public double PACMAN_MOUTH_CLOSED_DISTANCE()  {
        return PACMAN_MOUTH_ANIMATION_COMPLETE_DIST() * PACMAN_MOUTH_ANIMATION_PERCENTAGES().get(1);
    }

    public double PACMAN_MOUTH_OPEN_ARC_EXTENT_DEG() {
        return pacman.getMouth().getOpen().getArcExtentDeg();
    }

    public double PACMAN_MOUTH_OPEN_RIGHT_START_ANGLE() {
        return pacman.getMouth().getOpen().getRightStartAngle();
    }

    public double PACMAN_MOUTH_OPEN_UP_START_ANGLE() {
        return pacman.getMouth().getOpen().getUpStartAngle();
    }

    public double PACMAN_MOUTH_OPEN_LEFT_START_ANGLE() {
        return pacman.getMouth().getOpen().getLeftStartAngle();
    }

    public double PACMAN_MOUTH_OPEN_DOWN_START_ANGLE() {
        return pacman.getMouth().getOpen().getDownStartAngle();
    }

    public double PACMAN_MOUTH_CLOSED_ARC_EXTENT_DEG() {
        return pacman.getMouth().getClosed().getArcExtentDeg();
    }

    public double PACMAN_MOUTH_CLOSED_RIGHT_START_ANGLE() {
        return pacman.getMouth().getClosed().getRightStartAngle();
    }

    public double PACMAN_MOUTH_CLOSED_UP_START_ANGLE() {
        return pacman.getMouth().getClosed().getUpStartAngle();
    }

    public double PACMAN_MOUTH_CLOSED_LEFT_START_ANGLE() {
        return pacman.getMouth().getClosed().getLeftStartAngle();
    }

    public double PACMAN_MOUTH_CLOSED_DOWN_START_ANGLE() {
        return pacman.getMouth().getClosed().getDownStartAngle();
    }

    public double GHOST_WIDTH() {
        return ghost.getWidth();
    }

    public double GHOST_HEIGHT() {
        return ghost.getHeight();
    }

    public double GHOST_BLINKY_SPEED() {
        return ghost.getBlinky().getSpeed();
    }

    public double GHOST_BLINKY_ANIMATION_COMPLETE_DIST() {
        return ghost.getBlinky().getAnimation().getCompleteDist();
    }

    public List<Double> GHOST_BLINKY_ANIMATION_PERCENTAGES() {
        return ghost.getBlinky().getAnimation().getPercentages();
    }

    public double GHOST_INKY_SPEED() {
        return ghost.getInky().getSpeed();
    }

    public double GHOST_INKY_ANIMATION_COMPLETE_DIST() {
        return ghost.getInky().getAnimation().getCompleteDist();
    }

    public List<Double> GHOST_INKY_ANIMATION_PERCENTAGES() {
        return ghost.getInky().getAnimation().getPercentages();
    }

    public double GHOST_PINKY_SPEED() {
        return ghost.getPinky().getSpeed();
    }

    public double GHOST_PINKY_ANIMATION_COMPLETE_DIST() {
        return ghost.getPinky().getAnimation().getCompleteDist();
    }

    public List<Double> GHOST_PINKY_ANIMATION_PERCENTAGES() {
        return ghost.getPinky().getAnimation().getPercentages();
    }

    public double GHOST_CLYDE_SPEED() {
        return ghost.getClyde().getSpeed();
    }

    public double GHOST_CLYDE_ANIMATION_COMPLETE_DIST() {
        return ghost.getClyde().getAnimation().getCompleteDist();
    }

    public List<Double> GHOST_CLYDE_ANIMATION_PERCENTAGES() {
        return ghost.getClyde().getAnimation().getPercentages();
    }

    public Color GHOST_HOUSE_WALL_COLOR() {
        return ghostHouse.getWallColor();
    }

    public Color SUGAR_COLOR() {
        return sugar.getColor();
    }

    public double SUGAR_DIAMETER() {
        return sugar.getDiameter();
    }

    public Color SUPER_SUGAR_COLOR() {
        return superSugar.getColor();
    }

    public double SUPER_SUGAR_DIAMETER() {
        return superSugar.getDiameter();
    }

    public double SUPER_SUGAR_PERCENTAGE() {
        return superSugar.getPercentage();
    }
}
