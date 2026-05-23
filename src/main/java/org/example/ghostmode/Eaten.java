package org.example.ghostmode;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import org.example.animation.Animator;
import org.example.animation.DistanceBasedAnimator;
import org.example.config.Configs;
import org.example.constant.DimensionsC;
import org.example.constant.DirectionsE;
import org.example.constant.GhostEatenSpritesFileNameC;
import org.example.entity.CanvasCoordinate;
import org.example.entity.Vector;
import org.example.ghostmode.navigation.GhostNavigator;
import org.example.ghostmode.navigation.ShortestPathNavigator;
import org.example.model.GameState;
import org.example.sprite.ghost.Ghost;
import org.example.util.ghost.GhostUtil;

import java.util.AbstractMap;
import java.util.Map;

public class Eaten extends GhostMode {

    private final GhostNavigator navigator;
    private final Animator animator;
    private final GameState gameState;

    private CanvasCoordinate ghostHouseEmptyLoc;

    public Eaten(Ghost ghost, GameState gameState) {
        super(ghost);
        this.gameState = gameState;
        this.navigator = new ShortestPathNavigator();

        final Map<Vector, Image[]> sprites = loadSprites();
        this.animator = new DistanceBasedAnimator(
                new double[]{DimensionsC.BLINKY_STRIDE_PIXELS},
                sprites.get(Vector.UP)
        );
    }

    @Override
    public void init() {
        final double ghostHERow = gameState.getGhostHouseS().getERow();;
        final double ghostHSCol = gameState.getGhostHouseS().getCol();
        ghostHouseEmptyLoc = new CanvasCoordinate(ghostHERow - DimensionsC.MAZE_CELL_SIZE_PIXELS, ghostHSCol + DimensionsC.MAZE_CELL_SIZE_PIXELS);
    }

    @Override
    public void enter() {
        System.out.println("Eaten.enter() method need to do nothing");
    }


    @Override
    public boolean ended() {
        return ghost.getTopLeftCorner().equals(ghostHouseEmptyLoc);
    }

    @Override
    public void render(Canvas canvas) {
        canvas.getGraphicsContext2D().drawImage(animator.getFrame(), ghost.getCol(), ghost.getRow());
    }

    @Override
    public void move() {
        final CanvasCoordinate currCord = new CanvasCoordinate(ghost.getRow(), ghost.getCol());
        final DirectionsE newDir = this.navigator.nextMoveDirection(currCord, ghostHouseEmptyLoc);
        final CanvasCoordinate newCord = GhostUtil.move(currCord, newDir);

        ghost.setDir(newDir);
        ghost.setRow(newCord.getRow());
        ghost.setCol(newCord.getCol());

        if (newDir != DirectionsE.STILL) {
            this.animator.stride(DimensionsC.BLINKY_STRIDE_PIXELS / Configs.FRAMES_PER_SEC_FOR_GHOST_BLINKY_STRIDE);
        }
    }

    private Map<Vector, Image[]> loadSprites() {
        // [TODO] move sprites loading into a class that provide caching
        final String GHOST_EATEN_UP_FRAME_FILE_RESOURCE_ABSOLUTE_PATH = getClass().getResource(GhostEatenSpritesFileNameC.GHOST_EATEN_UP_FRAME_FILE_RESOURCE_RELATIVE_PATH).toString();
        final String GHOST_EATEN_RIGHT_FRAME_FILE_RESOURCE_ABSOLUTE_PATH = getClass().getResource(GhostEatenSpritesFileNameC.GHOST_EATEN_RIGHT_FRAME_FILE_RESOURCE_RELATIVE_PATH).toString();
        final String GHOST_EATEN_DOWN_FRAME_FILE_RESOURCE_ABSOLUTE_PATH = getClass().getResource(GhostEatenSpritesFileNameC.GHOST_EATEN_DOWN_FRAME_FILE_RESOURCE_RELATIVE_PATH).toString();
        final String GHOST_EATEN_LEFT_FRAME_FILE_RESOURCE_ABSOLUTE_PATH = getClass().getResource(GhostEatenSpritesFileNameC.GHOST_EATEN_LEFT_FRAME_FILE_RESOURCE_RELATIVE_PATH).toString();

        final Image up = new Image(GHOST_EATEN_UP_FRAME_FILE_RESOURCE_ABSOLUTE_PATH);
        final Image right = new Image(GHOST_EATEN_RIGHT_FRAME_FILE_RESOURCE_ABSOLUTE_PATH);
        final Image down = new Image(GHOST_EATEN_DOWN_FRAME_FILE_RESOURCE_ABSOLUTE_PATH);
        final Image left = new Image(GHOST_EATEN_LEFT_FRAME_FILE_RESOURCE_ABSOLUTE_PATH);

        return Map.ofEntries(
                new AbstractMap.SimpleEntry<>(Vector.UP, new Image[]{up}),
                new AbstractMap.SimpleEntry<>(Vector.RIGHT, new Image[]{right}),
                new AbstractMap.SimpleEntry<>(Vector.DOWN, new Image[]{down}),
                new AbstractMap.SimpleEntry<>(Vector.LEFT, new Image[]{left})
                );
    }
}
