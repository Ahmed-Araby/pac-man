package com.ahmedaraby.game.pacman.sprite.pacman;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.constant.ColorC;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.game.pacman.model.event.Event;
import com.ahmedaraby.game.pacman.sprite.MovingSprite;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;

public class DyingPacManS extends MovingSprite {
    // pacman mouse geometric information
    private double arcStartAngle;
    private double arcExtent;

    public DyingPacManS(PacMan pacMan, ConfigsEx configs) {
        super(null, configs, null, SpriteE.DEMOLISHING_PAC_MAN,
                pacMan.getTopLeftCorner(), pacMan.getWidth(), pacMan.getHeight(), pacMan.getDirV());
        arcStartAngle = pacMan.getArcStartAngle();
        arcExtent = pacMan.getArcExtent();
    }

    @Override
    public void render(Canvas canvas) {
        final GraphicsContext con = canvas.getGraphicsContext2D();
        con.setFill(ColorC.PAC_MAN_COLOR);
        con.fillArc(getCol(), getRow(), configs.PACMAN_DIAMETER(), configs.PACMAN_DIAMETER(),
                arcStartAngle, arcExtent, ArcType.ROUND);
    }

    @Override
    public void move(Event event) {
        // [TODO] collapse pacman
    }
}
