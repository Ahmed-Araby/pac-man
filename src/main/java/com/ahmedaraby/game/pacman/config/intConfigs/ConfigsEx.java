package com.ahmedaraby.game.pacman.config.intConfigs;

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


    public double getCanvasWidth() {
        return canvas.getWidth();
    }

    public double getCanvasHeight() {
        return canvas.getHeight();
    }

    public double getPlaygroundCellSize() {
        return playground.getCellSize();
    }

    public double getPacmanSpeed() {
        return pacman.getSpeed();
    }

    public double getPacmanDiameter() {
        return pacman.getDiameter();
    }

    public double getPacmanMouthAnimationCompleteDist() {
        return pacman.getMouth().getAnimation().getCompleteDist();
    }

    public List<Double> getPacmanMouthAnimationPercentages() {
        return pacman.getMouth().getAnimation().getPercentages();
    }

    public double getPacmanMouthOpenArcExtentDeg() {
        return pacman.getMouth().getOpen().getArcExtentDeg();
    }

    public double getPacmanMouthOpenRightStartAngle() {
        return pacman.getMouth().getOpen().getRightStartAngle();
    }

    public double getPacmanMouthOpenUpStartAngle() {
        return pacman.getMouth().getOpen().getUpStartAngle();
    }

    public double getPacmanMouthOpenLeftStartAngle() {
        return pacman.getMouth().getOpen().getLeftStartAngle();
    }

    public double getPacmanMouthOpenDownStartAngle() {
        return pacman.getMouth().getOpen().getDownStartAngle();
    }

    public double getPacmanMouthClosedArcExtentDeg() {
        return pacman.getMouth().getClosed().getArcExtentDeg();
    }

    public double getPacmanMouthClosedRightStartAngle() {
        return pacman.getMouth().getClosed().getRightStartAngle();
    }

    public double getPacmanMouthClosedUpStartAngle() {
        return pacman.getMouth().getClosed().getUpStartAngle();
    }

    public double getPacmanMouthClosedLeftStartAngle() {
        return pacman.getMouth().getClosed().getLeftStartAngle();
    }

    public double getPacmanMouthClosedDownStartAngle() {
        return pacman.getMouth().getClosed().getDownStartAngle();
    }

    public double getGhostWidth() {
        return ghost.getWidth();
    }

    public double getGhostHeight() {
        return ghost.getHeight();
    }

    public double getGhostBlinkySpeed() {
        return ghost.getBlinky().getSpeed();
    }

    public double getGhostBlinkyAnimationCompleteDist() {
        return ghost.getBlinky().getAnimation().getCompleteDist();
    }

    public List<Double> getGhostBlinkyAnimationPercentages() {
        return ghost.getBlinky().getAnimation().getPercentages();
    }

    public double getGhostInkySpeed() {
        return ghost.getInky().getSpeed();
    }

    public double getGhostInkyAnimationCompleteDist() {
        return ghost.getInky().getAnimation().getCompleteDist();
    }

    public List<Double> getGhostInkyAnimationPercentages() {
        return ghost.getInky().getAnimation().getPercentages();
    }

    public double getGhostPinkySpeed() {
        return ghost.getPinky().getSpeed();
    }

    public double getGhostPinkyAnimationCompleteDist() {
        return ghost.getPinky().getAnimation().getCompleteDist();
    }

    public List<Double> getGhostPinkyAnimationPercentages() {
        return ghost.getPinky().getAnimation().getPercentages();
    }

    public double getGhostClydeSpeed() {
        return ghost.getClyde().getSpeed();
    }

    public double getGhostClydeAnimationCompleteDist() {
        return ghost.getClyde().getAnimation().getCompleteDist();
    }

    public List<Double> getGhostClydeAnimationPercentages() {
        return ghost.getClyde().getAnimation().getPercentages();
    }

    public double getSugarDiameter() {
        return sugar.getDiameter();
    }

    public double getSuperSugarDiameter() {
        return superSugar.getDiameter();
    }

    public double getSuperSugarPercentage() {
        return superSugar.getPercentage();
    }
}
