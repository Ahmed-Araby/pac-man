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
    private MovingSpriteConfig movingSprite;
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

    public double CORD_EQ_THRESHOLD() {
        return canvas.getCoordinateEqualityThreshold();
    }

    public double PLAYGROUND_CELL_SIZE() {
        return playground.getCellSize();
    }

    public int PLAYGROUND_WIDTH() {
        return (int) (CANVAS_WIDTH() / PLAYGROUND_CELL_SIZE());
    }

    public int PLAYGROUND_HEIGHT() {
        return (int) (CANVAS_HEIGHT() / PLAYGROUND_CELL_SIZE());
    }

    public Color PLAYGROUND_WALL_COLOR() {
        return playground.getWallColor();
    }

    public Color PLAYGROUND_BACKGROUND_COLOR() {
        return playground.getBackgroundColor();
    }

    public double MIN_ALLOWED_STRIDE() {
        return movingSprite.getMinAllowedStride();
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

    // equals to the length of the dying music
    public int PACMAN_DEATH_PERIOD_SEC() {
        return pacman.getDeathPeriodSec();
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

    public int PACMAN_MOUTH_OPEN_ARC_EXTENT_DEG() {
        return pacman.getMouth().getOpen().getArcExtentDeg();
    }

    public int PACMAN_MOUTH_OPEN_RIGHT_START_ANGLE() {
        return pacman.getMouth().getOpen().getRightStartAngle();
    }

    public int PACMAN_MOUTH_OPEN_UP_START_ANGLE() {
        return pacman.getMouth().getOpen().getUpStartAngle();
    }

    public int PACMAN_MOUTH_OPEN_LEFT_START_ANGLE() {
        return pacman.getMouth().getOpen().getLeftStartAngle();
    }

    public int PACMAN_MOUTH_OPEN_DOWN_START_ANGLE() {
        return pacman.getMouth().getOpen().getDownStartAngle();
    }

    public int PACMAN_MOUTH_CLOSED_ARC_EXTENT_DEG() {
        return pacman.getMouth().getClosed().getArcExtentDeg();
    }

    public int PACMAN_MOUTH_CLOSED_ARC_START_ANGLE_DEG() {
        return pacman.getMouth().getClosed().getArcStartAngleDeg();
    }

    public int PAC_MAN_BURSTING_ARC_EXTENT_DEG() {
        return pacman.getBurstingArcExtentDeg();
    }

    public int PAC_MAN_BURSTING_ARC_COUNT() {
        return pacman.getBurstingArcCount();
    }

    public double GHOST_WIDTH() {
        return ghost.getWidth();
    }

    public double GHOST_HEIGHT() {
        return ghost.getHeight();
    }

    public double GHOST_SPEED() {
        return ghost.getSpeed();
    }

    public double GHOST_ANIMATION_COMPLETE_DIST() {
        return ghost.getAnimation().getCompleteDist();
    }

    public List<Double> GHOST_ANIMATION_PERCENTAGES() {
        return ghost.getAnimation().getPercentages();
    }

    public double GHOST_FIRST_FRAME_DISTANCE() {
        return GHOST_ANIMATION_COMPLETE_DIST() * GHOST_ANIMATION_PERCENTAGES().get(0);
    }

    public double GHOST_SECOND_FRAME_DISTANCE() {
        return GHOST_ANIMATION_COMPLETE_DIST() * GHOST_ANIMATION_PERCENTAGES().get(1);
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

    public double GHOST_BLINK_FIRST_FRAME_DISTANCE() {
        return GHOST_BLINKY_ANIMATION_COMPLETE_DIST() * GHOST_BLINKY_ANIMATION_PERCENTAGES().get(0);
    }

    public double GHOST_BLINK_SECOND_FRAME_DISTANCE() {
        return GHOST_BLINKY_ANIMATION_COMPLETE_DIST() * GHOST_BLINKY_ANIMATION_PERCENTAGES().get(1);
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

    public double GHOST_INKY_FIRST_FRAME_DISTANCE() {
        return GHOST_INKY_ANIMATION_COMPLETE_DIST() * GHOST_INKY_ANIMATION_PERCENTAGES().get(0);
    }

    public double GHOST_INKY_SECOND_FRAME_DISTANCE() {
        return GHOST_INKY_ANIMATION_COMPLETE_DIST() * GHOST_INKY_ANIMATION_PERCENTAGES().get(1);
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

    public double GHOST_PINKY_FIRST_FRAME_DISTANCE() {
        return GHOST_PINKY_ANIMATION_COMPLETE_DIST() * GHOST_PINKY_ANIMATION_PERCENTAGES().get(0);
    }
    public double GHOST_PINKY_SECOND_FRAME_DISTANCE() {
        return GHOST_PINKY_ANIMATION_COMPLETE_DIST() * GHOST_PINKY_ANIMATION_PERCENTAGES().get(1);
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

    public double GHOST_CLYDE_FIRST_FRAME_DISTANCE() {
        return GHOST_CLYDE_ANIMATION_COMPLETE_DIST() * GHOST_CLYDE_ANIMATION_PERCENTAGES().get(0);
    }

    public double GHOST_CLYDE_SECOND_FRAME_DISTANCE() {
        return GHOST_CLYDE_ANIMATION_COMPLETE_DIST() * GHOST_CLYDE_ANIMATION_PERCENTAGES().get(1);
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

    public String PAC_MAN_EAT_SUGAR_CLIP_PATH() {
        return sound.getPacmanEatSugarPath();
    }

    public String PAC_MAN_DYING_CLIP_PATH() {
        return sound.getPacManDyingPath();
    }

    public String AFTER_PAC_MAN_DEATH_CLIP_PATH() {
        return sound.getAfterPacManDeathPath();
    }
}
